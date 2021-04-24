package it.polito.tdp.nobel.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	EsameDAO dao= new EsameDAO();
	Set <Esame> parziale= new HashSet <Esame>();
	List <Esame> partenza= new ArrayList <Esame>(this.dao.getTuttiEsami());
	Set <Esame> migliore;
	double mediaMigliore;
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		this.mediaMigliore=0.0;
		this.migliore= new HashSet <Esame>();
		Set <Esame> parziale= new HashSet<Esame>();
		cerca2(parziale, 0, numeroCrediti);
		return this.migliore;	
	}

	
	private void cerca(Set<Esame> parziale, int L , int numeroCrediti) {
		int crediti= this.sommaCrediti(parziale);
		if(crediti>numeroCrediti) {
			return;
			
		}
		if(crediti==numeroCrediti) {
			double media= this.calcolaMedia(parziale);
			if(media>this.mediaMigliore) {
				this.mediaMigliore=media;
				this.migliore=new HashSet <Esame>(parziale);
				return;
			}
		}
		if(L==partenza.size()) {
			return;
		}
		
		for(Esame e: partenza) {
			if(!parziale.contains(e)) {
				parziale.add(e);
				cerca(parziale, L+1, numeroCrediti);
				parziale.remove(e);
			}
		}
		
	}
	
	private void cerca2(Set<Esame> parziale, int L , int numeroCrediti) {
		int crediti= this.sommaCrediti(parziale);
		if(crediti>numeroCrediti) {
			return;
			
		}
		if(crediti==numeroCrediti) {
			double media= this.calcolaMedia(parziale);
			if(media>this.mediaMigliore) {
				this.mediaMigliore=media;
				this.migliore=new HashSet <Esame>(parziale);
				return;
			}
		}
		if(L==partenza.size()) {
			return;
		}
		
		parziale.add(partenza.get(L));
		cerca2(parziale, L+1, numeroCrediti);
		parziale.remove(partenza.get(L));
		cerca2(parziale, L+1, numeroCrediti);
		
	}


	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
