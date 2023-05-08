package it.polito.tdp.metroparis;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class ProvaGrafo {
	// Creazione del grafo slide 28 
	public static void main(String[] args) {
		// creazione grafo vuoto
		Graph<String, DefaultEdge> grafo = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class); 
		
		// aggiunta elementi
		grafo.addVertex("r");
		grafo.addVertex("s");
		grafo.addVertex("t");
		grafo.addVertex("u");
		grafo.addVertex("v");
		grafo.addVertex("w");
		grafo.addVertex("x");
//		grafo.addVertex("y");
//		grafo.addVertex("z");
		
		grafo.addEdge("r", "s"); // addEdge(vertice di partenza , vertice di arrivo)
		grafo.addEdge("r", "v");
		grafo.addEdge("s", "w");
		grafo.addEdge("t", "x");
		grafo.addEdge("t", "w");
		grafo.addEdge("w", "x");
		//...

		System.out.println(grafo.toString()); 
		// ([r, s, t], [{r,s}]) 
		// ([elenco dei veritci], [{elenco degli archi}]) con le graffe perchè non orientato
		// se fosse orientato ci sarebbero le parentesi tonde
		
		// Quanti vertici e archi ci sono?
		System.out.println("Vertici: "+grafo.vertexSet().size());
		System.out.println("Archi: "+grafo.edgeSet().size());
		
		// Qual è il grado del vertice ? 
		for(String v: grafo.vertexSet()) {
			System.out.println("Vertice " + v + " ha grado "+grafo.degreeOf(v));
//		}
//		
//		// Quali sono gli archi incidenti sul vertice=
//		for(String v: grafo.vertexSet()) {
//			System.out.println("Vertice " + v + " ha archi "+grafo.edgesOf(v));
			
			// Quali sono i vertici collegati a un vertice?
			for(DefaultEdge arco : grafo.edgesOf(v)) {
				System.out.println(arco);
				
				if(v.equals(grafo.getEdgeSource(arco))) {
					String arrivo = grafo.getEdgeTarget(arco); // restituisce il vertice di arrivo dato quello di partenza
					System.out.println("\té connesso a "+arrivo);
				}else {
					String arrivo = grafo.getEdgeSource(arco); // restituisce il vertice di partenza dato quello di arrivo
					System.out.println("\té connesso a "+arrivo);
				}
				
			String arrivo = Graphs.getOppositeVertex(grafo, arco, v); // solge la stessa funzione dell'if
			System.out.println("\t*è connesso a "+arrivo);
				
			}
			
			List<String> vicini = Graphs.neighborListOf(grafo, v); // restituisce i vicini
			System.out.println("\tI vicini sono: "+vicini);
		}
		
		
	}
	

}
