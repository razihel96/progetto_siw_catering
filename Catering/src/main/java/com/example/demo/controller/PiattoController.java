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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.controller.validator.PiattoValidator;
import com.example.demo.model.Buffet;
import com.example.demo.model.Credentials;
import com.example.demo.model.Piatto;
import com.example.demo.service.BuffetService;
import com.example.demo.service.CredentialsService;
import com.example.demo.service.PiattoService;

@Controller
public class PiattoController {

	@Autowired
	private PiattoService piattoService;

	@Autowired
	private PiattoValidator piattoValidator;
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired 
	private CredentialsService credentialsService;
	
	

	@PostMapping("/admin/piatto")
	public String addPiatto(@RequestParam ("idBuffet") String idBuffet, @Valid @ModelAttribute ("piatto") Piatto piatto, BindingResult bindingResult, Model model) {

		Long id = Long.valueOf(idBuffet);
		Buffet buffet = buffetService.findById(id);
		piatto.setBuffet(buffet);

		//intanto verifichiamo che non ci siano doppioni
		piattoValidator.validate(piatto, bindingResult);

		//poi verifichiamo che non ci siano stati errori nella validazione

		//se non ci sono errori
		if(!bindingResult.hasErrors()) {
			//salvo l'oggetto piatto
			piattoService.save(piatto);

			//lo aggiungo al modello
			model.addAttribute("piatto", piatto);

			//se è andato tutto a buon fine
			return "admin/piatto.html";
		}
		//se qualcosa è andato storto, torno alla form
		return "admin/piattoForm.html";
	}


	//prendo l'elenco dei piatti tramite l'id del buffet
	@GetMapping("/buffet/{id}/elencoPiatti")
	public String getElencoPiatti(@PathVariable ("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		List<Piatto> elencoPiatti = piattoService.getByBuffet(buffet);
		model.addAttribute("elencoPiatti", elencoPiatti);
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/elencoPiatti.html";
        }
        return "elencoPiatti_default.html";
    }

	
	
	//prendo il piatto per il suo id
	@GetMapping("/piatto/{id}")
	public String getPiatto(@PathVariable ("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		model.addAttribute("piatto", piatto);
		return "admin/piatto.html";
	}

	
	

	//mi ritorna la form...
	//...MA prima mette nel modello un oggetto Piatto appena creato
	/*
	 * prendo il buffet per il suo id e ci aggiungo un piatto
	 */
	@GetMapping("/buffet/{id}/piattoForm") 
	public String creaPiatto(@PathVariable ("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		Piatto piatto = new Piatto();
		piatto.setBuffet(buffet);
		model.addAttribute("piatto", piatto);
		return "admin/piattoForm.html";
	}
	
	
	
	
	//se clicco su cancella mi porta alla pagina di conferma
	@GetMapping("/toDeletePiatto/{id}")
	public String toDeletePiatto(@PathVariable("id") Long id, Model model) {
		model.addAttribute("piatto", piattoService.findById(id));
		
		return "admin/toDeletePiatto.html";
	}
	
	//confermo la cancellazione
	@GetMapping("/deletePiatto/{id}") 
	public String deletePiatto(@PathVariable("id") Long id, Model model) {
		piattoService.deleteById(id);
		model.addAttribute("elencoPiatti", piattoService.findAll());
		
		return "admin/elencoPiatti.html";
	}
	
}
