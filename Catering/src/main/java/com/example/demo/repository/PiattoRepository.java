package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Buffet;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;

public interface PiattoRepository extends CrudRepository<Piatto, Long> {

	//mi costruisco un metodo che mi dice se esiste un piatto con quel nome 
	public boolean existsByNome(String nome);
	
	public List<Piatto> findByBuffet(Buffet buffet);
	
	//gli passo un ingrediente mi restituisce una lista di piatti
	public List<Piatto> findByIngredienti(Ingrediente ingrediente);
}
