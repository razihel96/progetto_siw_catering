package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Ingrediente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //chiave primaria nel mapping
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@Column(nullable = false)
	private String descrizione;
	
	@NotBlank
	@Column(nullable = false)
	private String origine;
	
	
	
	
	/*
	 * JPA
	 */
	@ManyToMany (mappedBy="ingredientiPiatto")
	@Column (name="piatti")
	private List<Piatto> ingredienteInPiuPiatti; //gli ingredienti possono trovarsi in più piatti
	

	//GETTERS&SETTERS
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getOrigine() {
		return origine;
	}

	public void setOrigine(String origine) {
		this.origine = origine;
	}

	public List<Piatto> getIngredienteInPiuPiatti() {
		return ingredienteInPiuPiatti;
	}

	public void setIngredienteInPiuPiatti(List<Piatto> ingredienteInPiuPiatti) {
		this.ingredienteInPiuPiatti = ingredienteInPiuPiatti;
	}

	public void addPiatto(Piatto piatto) {
		this.ingredienteInPiuPiatti.add(piatto);
	}
	
	

}
