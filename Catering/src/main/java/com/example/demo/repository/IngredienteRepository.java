package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;

public interface IngredienteRepository extends CrudRepository<Ingrediente, Long>{

	//mi trova la lista degli ingredienti passatogli un piatto
	public List<Ingrediente> findByPiatti(Piatto piatto);

	public boolean existsByNome(String nome);
}
