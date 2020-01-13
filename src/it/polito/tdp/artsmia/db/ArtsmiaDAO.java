package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects(Map<Integer, ArtObject> idMap) {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(idMap.get(res.getInt("object_id")) == null) {
					
				
					ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
							res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
							res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
							res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
					
					idMap.put(artObj.getId(), artObj);
					result.add(artObj);
				} else {
					result.add(idMap.get(res.getInt("object_id"))); //Se l'oggetto è gia stato creato non lo creo più
				}
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public List<Adiacenza> listAdiacenze()  {
		
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
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
