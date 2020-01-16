package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

// 1. Creo la classe model
public class Model {

	//1.1 Definisco il grafo e la idMap
	private Graph<ArtObject, DefaultWeightedEdge> graph;
	private Map<Integer, ArtObject> idMap;
	
	//1.2 Li costruisco
	public Model() {
		idMap = new HashMap<Integer, ArtObject>();
		graph = new SimpleWeightedGraph<ArtObject, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	
	//2. Creo la funzione del grafo
	public void creaGrafo() {
		
		ArtsmiaDAO dao = new ArtsmiaDAO();
		dao.listObjects(idMap);
		
		//3. Vado nel dai e aggiungo la idMap alla funzione listObjects
		
		//4. aggiungo i vertici
		Graphs.addAllVertices(graph, idMap.values());
		
		
		//5. Aggiungo gli archi -> Vado nel DAO
		//7. Mi ritorno la lista del dao appena creata
		List<Adiacenza> adj = dao.listAdiacenze();
		//8. Scorro la lista
		for (Adiacenza a: adj) {
			
			//qui mi serve la idMap perche ho salvato l'id in Adiacenza, ma ho bisogno dell'ArtObject
			ArtObject source = idMap.get(a.getO1()); //Perche abbiamo salvato l'id nell'Adiacenza, non l'oggetto
			ArtObject dest = idMap.get(a.getO2());
			//Non ho bisogno di controlli perche sono sicura che ci sia l'oggetto dentro la idMap
			
			//9.Aggiungo l'arco
			Graphs.addEdge(graph, source, dest, a.getPeso());
		}
		//10. Faccio la stampa
		System.out.println("Grafo creato: "+graph.vertexSet().size()+" vertici e "+graph.edgeSet().size()+" archi");
		//11. vado in TestModel
	}

	public Integer getVertexSize() {
		return graph.vertexSet().size();
	}

	public Integer getEdgesSize() {
		// TODO Auto-generated method stub
		return graph.edgeSet().size();
	}
	
	
}
