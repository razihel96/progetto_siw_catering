package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Piatto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //chiave primaria nel mapping
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@Column(nullable = false)
	private String descrizione;
	
	
	/*
	 * JPA
	 */
	@ManyToOne
	private Buffet buffet; //un piatto si trova in un solo buffet
	
	@ManyToMany 
	@Column (name="ingredienti")
	private List<Ingrediente> ingredientiPiatto; //un piatto può avere più ingredienti
	
	

	
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

	public Buffet getBuffet() {
		return buffet;
	}

	public void setBuffet(Buffet buffet) {
		this.buffet = buffet;
	}

	public List<Ingrediente> getIngredientiPiatto() {
		return ingredientiPiatto;
	}

	public void setIngredientiPiatto(List<Ingrediente> ingredientiPiatto) {
		this.ingredientiPiatto = ingredientiPiatto;
	}
	
	
	public void addIngrediente(Ingrediente ingrediente) {
		this.ingredientiPiatto.add(ingrediente);
	}
	
	
	

}