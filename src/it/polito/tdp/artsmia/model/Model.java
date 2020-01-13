package it.polito.tdp.artsmia.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private Graph<ArtObject, DefaultWeightedEdge> graph;
	private Map<Integer, ArtObject> idMap;
	
	public Model() {
		idMap = new HashMap<Integer, ArtObject>();
		graph = new SimpleWeightedGraph<ArtObject, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	
	public void creaGrafo() {
				
		ArtsmiaDAO dao = new ArtsmiaDAO();		
		dao.listObjects(idMap);
		
		//aggiungo i vertici
		Graphs.addAllVertices(graph, idMap.values());
		
		//Aggiungo gli archi
		List<Adiacenza> adj = dao.listAdiacenze();
		
		for (Adiacenza a: adj) {
			ArtObject source = idMap.get(a.getO1()); //Perche abbiamo salvato l'id nell'Adiacenza, non l'oggetto
			ArtObject dest = idMap.get(a.getO2());
			Graphs.addEdge(graph, source, dest, a.getPeso());
		}
		
		System.out.println("Grafo creato: "+graph.vertexSet().size()+" vertici e "+graph.edgeSet().size()+" archi");
	}

	public Integer getVertexSize() {
		return graph.vertexSet().size();
	}

	public Integer getEdgesSize() {
		// TODO Auto-generated method stub
		return graph.edgeSet().size();
	}
	
	
}
