package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ingrediente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //chiave primaria nel mapping
	
	
	@Column(nullable = true)
	private String nome;
	
	@Column(nullable = true)
	private String descrizione;
	

	@Column(nullable = true)
	private String origine;
	
	
	
	
	/*
	 * JPA
	 */
//	@ManyToMany (mappedBy="ingredienti") //private List<Ingrediente> ingredienti; che si trova in Piatto
//	public List<Piatto> piatti; //gli ingredienti possono trovarsi in più piatti
	
	@ManyToOne
	private Piatto piatto; //gli ingredienti si trovano in un solo piatto
	
	
	public Ingrediente() {
	}
	

	public Ingrediente(String nome, String descrizione, String origine) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.origine = origine;
	}

	
	
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


	public Piatto getPiatto() {
		return piatto;
	}


	public void setPiatto(Piatto piatto) {
		this.piatto = piatto;
	}
	
	
	

//	public List<Piatto> getIngredienteInPiuPiatti() {
//		return piatti;
//	}
//
//	public void setIngredienteInPiuPiatti(List<Piatto> piatti) {
//		this.piatti = piatti;
//	}
//
//	public void addPiatto(Piatto piatto) {
//		this.piatti.add(piatto);
//	}
//
//
//	public void addPiatti(List<Piatto> piatti) {
//		this.piatti = piatti;
//	}

	
	

}
