package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Piatto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id; //chiave primaria nel mapping
	
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
	
//	@ManyToMany (cascade = CascadeType.PERSIST)
//	@JoinTable(
//			name = "piatto_ingrediente",
//			joinColumns = @JoinColumn(name = "piatto_id"),
//			inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
//			)
	
	@OneToMany (mappedBy = "piatto", cascade = CascadeType.ALL)
	private List<Ingrediente> ingredienti; //un piatto può avere più ingredienti
	
	

	
	public Piatto() {
	}
	
	
	public Piatto(@NotBlank String nome,
			List<Ingrediente> ingredienti) {
		this.nome = nome;
		this.ingredienti = ingredienti;
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

	public Buffet getBuffet() {
		return buffet;
	}

	public void setBuffet(Buffet buffet) {
		this.buffet = buffet;
	}

	public List<Ingrediente> getIngredientiPiatto() {
		return ingredienti;
	}

	public void setIngredientiPiatto(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}
	
	public void addIngrediente(Ingrediente ingrediente) {
		this.ingredienti.add(ingrediente);
	}
	
	
	

}
