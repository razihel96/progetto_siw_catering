package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.controller.validator.BuffetValidator;
import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.model.Credentials;
import com.example.demo.service.BuffetService;
import com.example.demo.service.ChefService;
import com.example.demo.service.CredentialsService;
import com.example.demo.upload.FileUploadUtil;

@Controller
public class BuffetController {
	
	@Autowired
	private BuffetService buffetService;
	
	@Autowired
	private BuffetValidator buffetValidator;
	
	@Autowired
	private ChefService chefService;
	
	@Autowired
	private CredentialsService credentialsService;

	
	
	@PostMapping("/buffet")
	public String addBuffet(@Valid @ModelAttribute ("buffet") Buffet buffet, @RequestParam("idChef") String idChef,
			@RequestParam("image") MultipartFile multipartFile, BindingResult bindingResult, Model model) throws IOException {
		
		/*
		 * codice ridondante ma che serve per il passaggio dei parametri
		 * perché gli devo passare l'id dello chef per aggiungere il buffet 
		 * a questo specifico chef
		 */
		Long id = Long.valueOf(idChef);
		Chef chef = chefService.findById(id);
		buffet.setChef(chef);
		
		//intanto verifichiamo che non ci siano doppioni
		buffetValidator.validate(buffet, bindingResult);
		
		//poi verifichiamo che non ci siano stati errori nella validazione

		//se non ci sono errori
		if(!bindingResult.hasErrors()) {

			//salvo l'oggetto buffet
			buffetService.save(buffet);
		
			//upload delle immagini
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			buffet.setPhotos(fileName);
			Buffet salvaBuffet = this.buffetService.inserisci(buffet);
			String uploadDir = "buffet-photos/" + salvaBuffet.getId();
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
			//lo aggiungo al modello
			model.addAttribute("buffet", buffet);
						
			//se è andato tutto a buon fine
			return "admin/buffet.html";
		}
		//se qualcosa è andato storto, torno alla form
		return "admin/buffetForm.html";
		
	}
	
	
	
	//prendo l'elenco dei buffet tramite l'id dello chef
	@GetMapping("/chef/{id}/elencoBuffet")
	public String getElencoBuffet(@PathVariable ("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		List<Buffet> elencoBuffet = buffetService.getByChef(chef);
		model.addAttribute("elencoBuffet", elencoBuffet);
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/elencoBuffet.html";
        }
        return "elencoBuffet_default.html";
    }
	
	
	
	
	//prendo il buffet per il suo id
	@GetMapping("/buffet/{id}")
	public String getBuffet(@PathVariable ("id") Long id, Model model) {
		Buffet buffet = buffetService.findById(id);
		model.addAttribute("buffet", buffet);
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/buffet.html";
        }
        return "buffet_default.html";
    }


	
	//mi ritorna la form...
	//...MA prima mette nel modello un oggetto Buffet appena creato
	/*
	 * prendo l'id di uno chef e ci aggiungo il suo buffet
	 */
	@GetMapping("/chef/{id}/buffetForm") 
	public String creaBuffet(@PathVariable ("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		Buffet buffet = new Buffet();
		buffet.setChef(chef);
		model.addAttribute("buffet", buffet);
		return "admin/buffetForm.html";
	}
	
	
	//se clicco su cancella mi porta alla pagina di conferma
	@GetMapping("/toDeleteBuffet/{id}")
	public String toDeleteBuffet(@PathVariable("id") Long id, Model model) {
		model.addAttribute("buffet", buffetService.findById(id));
		
		return "admin/toDeleteBuffet.html";
	}
	
	//confermo la cancellazione
	@GetMapping("/deleteBuffet/{id}") 
	public String deleteBuffet(@PathVariable("id") Long id, Model model) {
		buffetService.deleteById(id);
		model.addAttribute("elencoBuffet", buffetService.findAll());
		
		return "admin/elencoBuffet.html";
	}

}
