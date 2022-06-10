package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;

public interface BuffetRepository extends CrudRepository<Buffet, Long> { //id è tipo Long

		//mi costruisco un metodo che mi dice se esiste un buffet con quel nome 
		public boolean existsByNome(String nome);

		//mi costruisco un metodo che mi dia un buffet in base al suo chef
		public List<Buffet> findByChef(Chef chef);
	}

