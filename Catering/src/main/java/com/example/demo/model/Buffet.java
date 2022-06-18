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
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;


@Entity
public class Buffet {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id; //chiave primaria nel mapping

	@NotBlank
	@Column(nullable = false)
	private String nome;

	@NotBlank
	@Column(nullable = false)
	private String descrizione;

	@Column(nullable = true, length = 64)
	private String photos;


	/*
	 * JPA
	 */
	@ManyToOne
	private Chef chef; //i buffet vengono proposti da un solo chef

	@OneToMany(mappedBy= "buffet", cascade = CascadeType.ALL)
	private List<Piatto> piattiBuffet; //ogni buffet contiene più piatti



	//IMMAGINI
	@Transient
	public String getPhotosImagePath() {
		if (this.getPhotos() == null || this.getId() == null) return null;

		return "/buffet-photos/" + id + "/" + photos;
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

	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public List<Piatto> getPiattiBuffet() {
		return piattiBuffet;
	}

	public void setPiattiBuffet(List<Piatto> piattiBuffet) {
		this.piattiBuffet = piattiBuffet;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	private String getPhotos() {
		return this.photos;
	}

}
