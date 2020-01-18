package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {

	//3. Aggiungo la idMap ai parametri
	public List<ArtObject> listObjects(Map<Integer, ArtObject> idMap) {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				//3.1 Se non esiste nella mappa lo aggiungo e alla funz result
				if(idMap.get(res.getInt("object_id")) == null) {
					
				
					ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
							res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
							res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
							res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
					
					idMap.put(artObj.getId(), artObj);
					result.add(artObj);
					
				//3.2 Se esiste lo aggiungo direttamente
				} else {
					result.add(idMap.get(res.getInt("object_id"))); //Se l'oggetto è gia stato creato non lo creo più
				}
				
				//4. Torno nel Model
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	//5. Creo la query in sql 
	//5.1 Creo una nuova classe con gli estessi valori degli archi -> Adiacenza
	//6. Creo la funzione
	public List<Adiacenza> listAdiacenze()  {
		//qui non mi serve passargli la idMap perche la classe adiacenza è gia con l'id
		String sql = "SELECT e1.object_id AS o1, e2.object_id AS o2, COUNT(*) AS cnt " + 
				"FROM exhibition_objects e1, exhibition_objects e2 " + 
				"WHERE e1.exhibition_id = e2.exhibition_id " + 
				"AND e2.object_id > e1.object_id " + 
				"GROUP BY e1.object_id, e2.object_id";
		
		Connection conn = DBConnect.getConnection();
		List<Adiacenza> adj = new LinkedList<>();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

								
				Adiacenza a = new Adiacenza(res.getInt("o1"), res.getInt("o2"), res.getInt("cnt"));
				
				adj.add(a);
			}
			conn.close();
			return adj;
		//7. Torno nel Model	
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
