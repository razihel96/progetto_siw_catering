package com.example.demo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

@Entity
public class Chef {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //chiave primaria nel mapping
	
	@NotBlank
	@Column(nullable = false)
	private String nome;
	
	@NotBlank
	@Column(nullable = false)
	private String cognome;

	@NotBlank
	@Column(nullable = false)
	private String nazionalita;
	
	
	@Column(nullable = true, length = 64)
	private String photos;
	
	
	
	//COSTRUTTORE VUOTO
	public Chef() {	}
	
	//COSTRUTTORE
	public Chef(Long id, String nome,String cognome,String nazionalita, @NotBlank String photos) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.nazionalita = nazionalita;
		this.photos = photos;
	}
	
	

	/*
	 * JPA
	 */
	@OneToMany (mappedBy = "chef", cascade = CascadeType.ALL)
	private List<Buffet> buffetChef; //ogni chef propone più buffet 

	
	
	
	//IMMAGINI
	@Transient
    public String getPhotosImagePath() {
        if (this.getPhotos() == null || this.getId() == null) return null;
         
        return "/chef-photos/" + id + "/" + photos;
    }
	
	//GETTERS&SETTERS
	public Long getId() {
		return id;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNazionalita() {
		return nazionalita;
	}

	public void setNazionalita(String nazionalita) {
		this.nazionalita = nazionalita;
	}

	public List<Buffet> getBuffetChef() {
		return buffetChef;
	}

	public void setBuffetChef(List<Buffet> buffetChef) {
		this.buffetChef = buffetChef;
	}
	
	
	
}
