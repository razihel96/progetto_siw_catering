package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Chef;
import com.example.demo.repository.ChefRepository;



@Service
public class ChefService {

	@Autowired //carica da solo un'istanza di ChefRepository
	private ChefRepository cr;
	
	@Autowired
	private CredentialsService credentialsService;


	//ci pensa spring boot ad aprire e chiudere la transazione (INTERROGAZIONE TRANSAZIONALE)
	@Transactional
	public void save(Chef chef){
		cr.save(chef);
	}
	
	@Transactional
	public Chef inserisci(Chef chef) {
		return cr.save(chef);
	}

	@Transactional
	public void delete(Chef chef) {
		cr.delete(chef);
	}

	//cerco uno chef dandogli un id
	//INTERROGAZIONE NON TRANSAZIONALE
	public Chef findById(Long id) {
		return cr.findById(id).get(); //con .get() mi ritorna l'oggetto che ha preso
	}

	//mi ritorna la lista degli chef
	/*abbiamo fatto un ciclo perché findAll() è un iteratore e non una lista*/
	public List<Chef> findAll() {
		List<Chef> elencoChef = new ArrayList<>();
		for(Chef c : cr.findAll()) {
			elencoChef.add(c);
		}
		return elencoChef;
	}


	public void deleteById(Long id) {
		cr.deleteById(id);
	}


	public boolean alreadyExists(Chef chef) {
		return cr.existsByNomeAndCognome(chef.getNome(), chef.getCognome());
	}
	
	@Transactional
	public CredentialsService getCredentialsService() {
		return credentialsService;
	}

}
