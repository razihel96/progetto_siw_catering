package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.PiattoService;


@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private PiattoService piattoService;
	

	@PostMapping("/admin/ingrediente")
	public String addIngrediente(@Valid @ModelAttribute ("ingrediente") Ingrediente ingrediente, @Valid @ModelAttribute ("piatto") Piatto piatto,
			BindingResult bindingResult, Model model) {

//		Long id = Long.valueOf(idPiatto);
//		List<Piatto> ingredienteInPiuPiatti = piattoService.;
//		ingrediente.setIngredienteInPiuPiatti(ingredienteInPiuPiatti);

		
		//i doppioni dovrebbero andare bene
		

		//poi verifichiamo che non ci siano stati errori nella validazione

		//se non ci sono errori
		if(!bindingResult.hasErrors()) {
			//salvo gli oggetti ingrediente e piatto
			piatto.addIngrediente(ingrediente);
			piattoService.save(piatto);

			//li aggiungo al modello
			model.addAttribute("ingrediente", ingrediente);
			model.addAttribute("piatto", piatto);

			//se è andato tutto a buon fine
			return "ingrediente.html";
		}
		//se qualcosa è andato storto, torno alla form
		return "ingredienteForm.html";
	}
	
	
	//prendo l'elenco degli ingredienti tramite l'id del piatto
//	@GetMapping("/piatto/{id}/elencoIngredienti")
//	public String getElencoIngredienti(@PathVariable ("id") Long id, Model model) {
//		Piatto piatto = piattoService.findById(id);
//		List<Ingrediente> ingrediente = ingredienteService.getByPiatti(piatto);
//		model.addAttribute("elencoIngredienti", ingrediente);
//		return "elencoIngredienti.html";
//	}
	
	
	//prendo l'ingrediente per il suo id
	@GetMapping("/ingrediente/{id}")
	public String getIngrediente(@PathVariable ("id") Long id, Model model) {
		Ingrediente ingrediente = ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		return "ingrediente.html";
	}
	
	

	//mi ritorna la form...
	//...MA prima mette nel modello un oggetto Piatto appena creato
	/*
	 * prendo il piatto per il suo id e ci aggiungo un ingrediente
	 */
	@GetMapping("/piatto/{id}/ingredienteForm") 
	public String creaIngrediente(@PathVariable ("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		Ingrediente ingrediente = new Ingrediente();
		model.addAttribute("ingrediente", ingrediente);
		model.addAttribute("piatto", piatto);
		return "ingredienteForm.html";
	}
}
