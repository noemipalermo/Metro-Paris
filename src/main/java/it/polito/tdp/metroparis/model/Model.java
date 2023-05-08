package it.polito.tdp.metroparis.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.metroparis.db.MetroDAO;

public class Model {
	
	private Graph<Fermata, DefaultEdge> grafo;
	private List<Fermata> fermate ;
	private Map<Integer, Fermata> fermateIdMap; // uso IdMap per collegare le id_fermata e Fermata
	
	public void creaGrafo() {
		
		// crea l'oggetto grafo
		this.grafo = new SimpleGraph<Fermata, DefaultEdge>(DefaultEdge.class);
		
		
		// aggiungi vertici
		MetroDAO dao = new MetroDAO();
		
		fermateIdMap = new HashMap<>();
		
		this.fermate = dao.readFermate();
		
		for(Fermata f: this.fermate) {
			this.fermateIdMap.put(f.getIdFermata(), f);
		}
		
		Graphs.addAllVertices(this.grafo, this.fermate);
		
		
		// aggiungi archi
		// Quali sono gli archi? 3 modi:
		
		
	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		// 1. CONSIDERO TUTTI I POTENZIALI ARCHI: Prendo una qualunque coppia di vertici e mi chieso se sono connessi(aggiungo arco) o no(non aggiungo l'arco)
//		long tic = System.currentTimeMillis();
//		for(Fermata partenza : this.grafo.vertexSet()) { // potrei usare anche this.fermate, ma this.grafo è più sicuro che abbia tutte le fermate 
//			for(Fermata arrivo : this.grafo.vertexSet()) {
//				if(dao.isConnected(partenza,arrivo)) {
//					this.grafo.addEdge(partenza, arrivo); // se ho già inserito l'arco A-B e voglio inerire B-A, se l'arco è orientato crea il nuovo arco, ma se l'arco non è orientato lo ignora e ritorna null 
//				}
//			}
//		}
//		long toc = System.currentTimeMillis();
//		System.out.println("Tempo di esecuzione metodo 1: " +(toc-tic));
//		
		


	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		// 2. DATA UNA FERMATA, TROVA LA LISTA DELLE ADIACENTI: Prendo una stazione per volta e per ciascuna vedo le fermate vicine
//		tic = System.currentTimeMillis();
//		for(Fermata partenza : this.grafo.vertexSet()) {
//			List<Fermata> collegate = dao.trovaCollegate(partenza, fermateIdMap);
//			
//			for(Fermata arrivo: collegate) {
//				this.grafo.addEdge(arrivo, partenza);
//			}
//		}
//		toc = System.currentTimeMillis();
//		System.out.println("Tempo di esecuzione metodo 2: " +(toc-tic));
//		
//		
		
		

	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		// 3. CHIEDO DIRETTAMENTE AL DAO DI TROVARE LE COPPIE: Faccio una query per prendere tutti gli archi con un'unica query
//		tic = System.currentTimeMillis();
		List<CoppieF> coppie = dao.trovaCoppie(fermateIdMap);
		
		for(CoppieF c : coppie) {
			this.grafo.addEdge(c.getFermataP(), c.getFermataA());
			
		}
//		toc = System.currentTimeMillis();
//		System.out.println("Tempo di esecuzione metodo 3: " +(toc-tic));
		
		System.out.println("Grafo creato con "+this.grafo.vertexSet().size()+ " vertici e "+this.grafo.edgeSet().size()+" archi.");
		System.out.println(this.grafo);
	}

	public List<Fermata> getAllFermate() {
		MetroDAO dao = new MetroDAO();
		
		return dao.readFermate();
	}
	
	
	// controllo se il grafo è stato creato
	public boolean isGrafoLoaded() {
		return this.grafo.vertexSet().size()>0;
	}
	
	
	//Determina il percorso minimo tra due fermate
	public List<Fermata> Percorso(Fermata partenza, Fermata arrivo) {
		
		// Visita del grafo per costruire l'albero di visita, partendo da 'partenza'
		// se non do un vertice di partenza ne sceglie uno a caso
		BreadthFirstIterator<Fermata, DefaultEdge> visita = 
				new BreadthFirstIterator<Fermata, DefaultEdge>(this.grafo,partenza);
		
		List<Fermata> raggiungibili = new ArrayList<Fermata>();
		
		// Posso aggiungere quello che trovo in visita solo con un while e non con un for
		while(visita.hasNext()) {
			Fermata f = visita.next();
//			raggiungibili.add(f);
		}
		
//		System.out.println(raggiungibili);
		
		// Trova il percorso sull'albero di visita
		
		List<Fermata> percorso = new ArrayList<Fermata>();
		Fermata corrente = arrivo;
		percorso.add(arrivo);
		//Qual è la fermata IMMEDIATAMENTE precedente?
		DefaultEdge e = visita.getSpanningTreeEdge(corrente);
		
		
		while(e!=null) {
			Fermata precedente = Graphs.getOppositeVertex(this.grafo, e, corrente);
			//aggiungo la stazione all'inizio della lista
			percorso.add(0, precedente);
			corrente = precedente;
			
			e = visita.getSpanningTreeEdge(corrente);
		}
	
		
		return percorso;
	}

}
