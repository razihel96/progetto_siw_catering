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

import com.example.demo.controller.validator.ChefValidator;
import com.example.demo.model.Chef;
import com.example.demo.model.Credentials;
import com.example.demo.service.ChefService;
import com.example.demo.service.CredentialsService;
import com.example.demo.upload.FileUploadUtil;



@Controller
public class ChefController {

	@Autowired
	private ChefService chefService;
	
	@Autowired
	private ChefValidator chefValidator;
	
	@Autowired
	private CredentialsService credentialsService;
	
	
	
	@PostMapping("/admin/chef")
	public String addChef(@Valid @ModelAttribute ("chef") Chef chef, @RequestParam("image") MultipartFile multipartFile, BindingResult bindingResult, Model model) throws IOException {
		
		//controlla se non ci sono doppioni
		chefValidator.validate(chef, bindingResult);

		//se non ci sono errori
		if(!bindingResult.hasErrors()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			chef.setPhotos(fileName);
			
			Chef salvaChef = this.chefService.inserisci(chef);
			
			String uploadDir = "chef-photos/" + salvaChef.getId();
			
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
			
			//lo aggiungo al modello
			model.addAttribute("chef", chef);
			
			//se è andato tutto a buon fine
			return "admin/chef.html";
		}
		//se qualcosa è andato storto, torno alla form
		return "admin/chefForm.html";
		
	}
	
	
	//prendo l'elenco degli chef
	@GetMapping("/elencoChef")
	public String getElencoChef(Model model) {
		List<Chef> elencoChef = chefService.findAll();
		model.addAttribute("elencoChef", elencoChef);
		
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/elencoChef.html";
        }
        return "elencoChef_default.html";
    }
		
	
	//prendo lo chef per il suo id
	@GetMapping("/chef/{id}")
	public String getChef(@PathVariable ("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("chef", chef);
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/chef.html";
        }
        return "chef_default.html";
	}

	
	//mi ritorna la form...
	//...MA prima mette nel modello un oggetto Chef appena creato
	@GetMapping("/chefForm") 
	public String getChefForm(Model model) {
		model.addAttribute("chef", new Chef());
		
		return "admin/chefForm.html";
	}
	
	
	//se clicco su cancella mi porta alla pagina di conferma
	@GetMapping("/toDeleteChef/{id}")
	public String toDeleteChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", chefService.findById(id));
		
		return "admin/toDeleteChef.html";
	}
	
	//confermo la cancellazione
	@GetMapping("/deleteChef/{id}") 
	public String deleteChef(@PathVariable("id") Long id, Model model) {
		chefService.deleteById(id);
		model.addAttribute("elencoBuffet", chefService.findAll());
		
		return "admin/elencoChef.html";
	}
	

	
}
