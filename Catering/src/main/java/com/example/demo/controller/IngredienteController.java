package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.controller.validator.IngredienteValidator;
import com.example.demo.model.Credentials;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.service.CredentialsService;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.PiattoService;


@Controller
public class IngredienteController {
	
	@Autowired
	private IngredienteService ingredienteService;
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private CredentialsService credentialsService;
	
	@Autowired
	private IngredienteValidator ingredienteValidator;
	

	@PostMapping("/admin/ingrediente")
	public String addIngrediente(@Valid @ModelAttribute ("ingrediente") Ingrediente ingrediente,
			BindingResult bindingResult, Model model) {
		
		//i doppioni dovrebbero andare bene
		ingredienteValidator.validate(ingrediente, bindingResult);


		//poi verifichiamo che non ci siano stati errori nella validazione

		//se non ci sono errori
		if(!bindingResult.hasErrors()) {
			//salvo gli oggetti ingrediente e piatto
			ingredienteService.save(ingrediente);

			//li aggiungo al modello
			model.addAttribute("ingrediente", ingrediente);

			//se è andato tutto a buon fine
			return "admin/ingrediente.html";
		}
		//se qualcosa è andato storto, torno alla form
		return "admin/ingredienteForm.html";
	}
	
	
	//prendo l'elenco degli ingredienti tramite l'id del piatto
	@GetMapping("/piatto/{id}/elencoIngredienti")
	public String getElencoIngredienti(@PathVariable ("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		List<Ingrediente> elencoIngredienti = ingredienteService.getByPiatto(piatto);
		model.addAttribute("elencoIngredienti", elencoIngredienti);
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/elencoIngredienti.html";
        }
        return "elencoIngredienti_default.html";
    }
	
	
	
	//prendo l'ingrediente per il suo id
	@GetMapping("/ingrediente/{id}")
	public String getIngrediente(@PathVariable ("id") Long id, Model model) {
		Ingrediente ingrediente = ingredienteService.findById(id);
		model.addAttribute("ingrediente", ingrediente);
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/ingrediente.html";
        }
        return "ingrediente_default.html";
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
		return "admin/ingredienteForm.html";
	}
}
