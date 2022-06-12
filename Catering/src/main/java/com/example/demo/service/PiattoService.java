package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.repository.PiattoRepository;

@Service
public class PiattoService {
	
	@Autowired
	private PiattoRepository pr;
	
	
	//ci pensa spring boot ad aprire e chiudere la transazione (INTERROGAZIONE TRANSAZIONALE)
	@Transactional
	public void save(Piatto piatto)  {
		pr.save(piatto);
	}
	
	@Transactional
	public void delete(Piatto piatto) {
		pr.delete(piatto);
	}
	

	//cerco un piatto dandogli un id
	//INTERROGAZIONE NON TRANSAZIONALE
	public Piatto findById(Long id) {
		return pr.findById(id).get(); //con .get() mi ritorna l'oggetto che ha preso
	}
	
	
	public List<Piatto> getByBuffet(Buffet buffet) {
		List<Piatto> piattiPerBuffet = new ArrayList<>();
		Iterable<Piatto> i = pr.findByBuffet(buffet);
		for(Piatto p : i) {
			piattiPerBuffet.add(p);
		}
		return piattiPerBuffet;
	}
	
	/*
	 * mi ritorna la lista dei piatti corrispondenti ad un ingrediente
	 */
	public List<Piatto> getByIngrediente(Ingrediente ingredienti) {
		List<Piatto> piattoPerIngrediente = new ArrayList<>();
		Iterable<Piatto> i = pr.findByIngredienti(ingredienti);
		for(Piatto p : i) {
			piattoPerIngrediente.add(p);
		}
		return piattoPerIngrediente;
	}
	

	//mi ritorna la lista dei piatti
	/*abbiamo fatto un ciclo perché findAll() è un iteratore e non una lista*/
	public List<Piatto> findAll() {
		List<Piatto> elencoPiatti = new ArrayList<>();
		for(Piatto b : pr.findAll()) {
			elencoPiatti.add(b);
		}
		return elencoPiatti;
	}
	

	//verifica se un piatto esiste già
	public boolean alreadyExists(Piatto piatto) {
		return pr.existsByNome(piatto.getNome());
	}
	
	
	//cancella piatto da id
	public void deleteById(Long id) {
		pr.deleteById(id);
	}



}
