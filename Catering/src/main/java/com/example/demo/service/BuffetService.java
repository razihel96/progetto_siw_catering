package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.repository.BuffetRepository;



@Service
public class BuffetService {

	@Autowired //carica da solo un'istanza di BuffetRepository
	private BuffetRepository br;

	
	
	
	//ci pensa spring boot ad aprire e chiudere la transazione (INTERROGAZIONE TRANSAZIONALE)
	@Transactional
	public void save(Buffet buffet)  {
		br.save(buffet);
	}
	
	@Transactional
	public void delete(Buffet buffet) {
		br.delete(buffet);
	}

	//cerco un buffet dandogli un id
	//INTERROGAZIONE NON TRANSAZIONALE
	public Buffet findById(Long id) {
		return br.findById(id).get(); //con .get() mi ritorna l'oggetto che ha preso
	}

	//mi ritorna la lista dei buffet
	/*abbiamo fatto un ciclo perché findAll() è un iteratore e non una lista*/
	public List<Buffet> findAll() {
		List<Buffet> elencoBuffet = new ArrayList<>();
		for(Buffet b : br.findAll()) {
			elencoBuffet.add(b);
		}
		return elencoBuffet;
	}
	
	/*
	 * mi ritorna la lista dei buffet corrispondenti ad uno chef
	 */
	public List<Buffet> getByChef(Chef chef) {
		List<Buffet> buffetPerChef = new ArrayList<>();
		Iterable<Buffet> i = br.findByChef(chef);
		for(Buffet b : i) {
			buffetPerChef.add(b);
		}
		return buffetPerChef;
		
	}
	
	
	public boolean alreadyExists(Buffet buffet) {
		return br.existsByNome(buffet.getNome());
	}
	
	public void deleteById(Long id) {
		br.deleteById(id);
	}
	
}
