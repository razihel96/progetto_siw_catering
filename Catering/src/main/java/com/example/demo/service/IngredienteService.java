package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Piatto;
import com.example.demo.model.Ingrediente;
import com.example.demo.repository.IngredienteRepository;


@Service
public class IngredienteService {

	@Autowired
	private IngredienteRepository ir;
	
	
	//ci pensa spring boot ad aprire e chiudere la transazione (INTERROGAZIONE TRANSAZIONALE)
	@Transactional
	public void save(Ingrediente ingrediente)  {
		ir.save(ingrediente);
	}
	
	@Transactional
	public void delete(Ingrediente ingrediente) {
		ir.delete(ingrediente);
	}
	

	//cerco un ingrediente dandogli un id
	//INTERROGAZIONE NON TRANSAZIONALE
	public Ingrediente findById(Long id) {
		return ir.findById(id).get(); //con .get() mi ritorna l'oggetto che ha preso
	}
	
	

	
	/*
	 * mi ritorna la lista degli ingredienti corrispondenti ad un piatto
	 */
	public List<Ingrediente> getByPiatto(Piatto piatto) {
		List<Ingrediente> ingredientiPerPiatto = new ArrayList<>();
		Iterable<Ingrediente> i = ir.findByPiatti(piatto);
		for(Ingrediente ing : i) {
			ingredientiPerPiatto.add(ing);
		}
		return ingredientiPerPiatto;
	}
	

	//mi ritorna la lista degli ingredienti
	/*abbiamo fatto un ciclo perché findAll() è un iteratore e non una lista*/
	public List<Ingrediente> findAll() {
		List<Ingrediente> elencoIngredienti = new ArrayList<>();
		for(Ingrediente i : ir.findAll()) {
			elencoIngredienti.add(i);
		}
		return elencoIngredienti;
	}
	
	
	
	//cancella ingrediente da id
	public void deleteById(Long id) {
		ir.deleteById(id);
	}

	public boolean alreadyExists(Ingrediente ingrediente) {
		return ir.existsByNome(ingrediente.getNome());
	}

}
