package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Chef;

public interface ChefRepository extends CrudRepository<Chef, Long> {

	public boolean existsByNomeAndCognome(String nome, String cognome);

	public Chef save(Chef chef);

	public Optional<Chef> findById(Long fileId);
	
	

}
