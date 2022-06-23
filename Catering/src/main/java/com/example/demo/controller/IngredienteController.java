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
	
	

	@PostMapping("/admin/ingrediente")
	public String addIngrediente(@RequestParam ("idPiatto") String idPiatto, @Valid @ModelAttribute ("ingrediente") Ingrediente ingrediente, 
			BindingResult bindingResult, Model model) {
		
		
		Long id = Long.valueOf(idPiatto);
		Piatto piatto = piattoService.findById(id);
		ingrediente.setPiatto(piatto);
		
		
//		ingredienteValidator.validate(ingrediente, bindingResult);


		if(!bindingResult.hasErrors()) {
			
			ingredienteService.save(ingrediente);
			
			model.addAttribute("ingrediente", ingrediente);
			

			return "admin/ingrediente.html";
		}
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
//	@GetMapping("/ingrediente/{id}")
//	public String getIngrediente(@PathVariable ("id") Long id, Model model) {
//		Ingrediente ingrediente = ingredienteService.findById(id);
//		model.addAttribute("ingrediente", ingrediente);
//		
//		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
//    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
//            return "admin/ingrediente.html";
//        }
//        return "ingrediente_default.html";
//    }

//	@GetMapping("/ingrediente/{id}")
//	public String getIngrediente2(@PathVariable ("id") Long id, Model model) {
//		
//		Ingrediente ingrediente = ingredienteService.findById(id);
//		model.addAttribute("ingrediente", ingrediente);
//		
//		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
//    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
//            return "admin/ingrediente.html";
//        }
//        return "ingrediente_default.html";
//    }
//	
	

	//mi ritorna la form...
	//...MA prima mette nel modello un oggetto Piatto appena creato
	/*
	 * prendo il piatto per il suo id e ci aggiungo un ingrediente
	 */
	@GetMapping("/piatto/{id}/ingredienteForm") 
	public String creaIngrediente(@PathVariable ("id") Long id, Model model) {
		Piatto piatto = piattoService.findById(id);
		Ingrediente ingrediente = new Ingrediente();
		ingrediente.setPiatto(piatto);
		//non riesco a settare il piatto
		model.addAttribute("ingrediente", ingrediente);
		
		return "admin/ingredienteForm.html";
	}
}















