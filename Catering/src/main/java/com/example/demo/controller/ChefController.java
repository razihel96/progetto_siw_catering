package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.example.demo.model.Buffet;
import com.example.demo.model.Chef;
import com.example.demo.model.Piatto;
import com.example.demo.service.BuffetService;
import com.example.demo.service.ChefService;
import com.example.demo.service.PiattoService;
import com.example.demo.upload.FileUploadUtil;



@Controller
public class ChefController {

	@Autowired
	private ChefService chefService;

	@Autowired
	private ChefValidator chefValidator;
	
	@Autowired
	private PiattoService piattoService;
	
	@Autowired
	private BuffetService buffetService; 





	@PostMapping("/chef")
	public String addChef(@Valid @ModelAttribute ("chef") Chef chef, 
			@RequestParam("image") MultipartFile multipartFile, BindingResult bindingResult, Model model) throws IOException {

		//controlla se non ci sono doppioni
		chefValidator.validate(chef, bindingResult);

		//se non ci sono errori
		if(!bindingResult.hasErrors()) {
			
			chefService.save(chef);

			//immagini
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			chef.setPhotos(fileName);
			Chef salvaChef = this.chefService.inserisci(chef);
			String uploadDir = "chef-photos/" + salvaChef.getId();
			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

			//lo aggiungo al modello
			model.addAttribute("chef", chef);
			model.addAttribute("elencoChef", chefService.findAll());
			model.addAttribute("role", chefService.getCredentialsService().getRoleAuthenticated());			


			//se è andato tutto a buon fine
			return "elencoChef.html";
		}
		//se qualcosa è andato storto, torno alla form
		return "chefForm.html";

	}

	
	

	//prendo l'elenco degli chef
	//qui c'era un "/admin/elencoChef"
	@GetMapping("/elencoChef")
	public String getElencoChef(Model model) {
		List<Chef> elencoChef = chefService.findAll();
		model.addAttribute("elencoChef", elencoChef);
		model.addAttribute("role", chefService.getCredentialsService().getRoleAuthenticated());

		return "elencoChef.html";
	}
	
	
	//mi faccio ritornare l'elenco di tutti i piatti
	@GetMapping("/elencoTuttiPiatti") 
	public String getElencoTuttiPiatti(Model model) {
		List<Piatto> elencoTuttiPiatti = piattoService.findAll();	
		
		
				
		model.addAttribute("elencoPiatti", elencoTuttiPiatti);
		model.addAttribute("role", piattoService.getCredentialsService().getRoleAuthenticated());

		return "elencoPiatti.html";
	}
	
	
	//mi faccio ritornare l'elenco di tutti i buffet
	@GetMapping("/elencoTuttiBuffet") 
	public String getElencoTuttiBuffet(Model model) {
		List<Buffet> elencoTuttiBuffet = buffetService.findAll();	

		model.addAttribute("elencoBuffet", elencoTuttiBuffet);
		model.addAttribute("role", buffetService.getCredentialsService().getRoleAuthenticated());
		
		return "elencoBuffet.html";
	}





	//prendo lo chef per il suo id
	@GetMapping("/chef/{id}")
	public String getChef(@PathVariable ("id") Long id, Model model) {
		Chef chef = chefService.findById(id);
		model.addAttribute("chef", chef);

		return "chef.html";
	}


	//mi ritorna la form...
	//...MA prima mette nel modello un oggetto Chef appena creato
	@GetMapping("/admin/chefForm") 
	public String getChefForm(Model model) {
		model.addAttribute("chef", new Chef());
		model.addAttribute("elencoChef", chefService.findAll());


		return "chefForm.html";
	}
	
	


	//se clicco su cancella mi porta alla pagina di conferma
	@GetMapping("/admin/toDeleteChef/{id}")
	public String toDeleteChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", chefService.findById(id));

		return "toDeleteChef.html";
	}


	//confermo la cancellazione
	@GetMapping("/admin/deleteChef/{id}") 
	public String deleteChef(@PathVariable("id") Long id, Model model) {
		chefService.deleteById(id);
		
		model.addAttribute("elencoChef", chefService.findAll());
		model.addAttribute("role", chefService.getCredentialsService().getRoleAuthenticated());

		return "elencoChef.html";
	}
	
	
	
	//cancellazione diretta
	@GetMapping("/admin/cancellaChef/{id}") 
	public String cancellaChef(@PathVariable("id") Long id, Model model) {
		
		Chef chef = chefService.findById(id);
		chefService.delete(chef);
		model.addAttribute("elencoChef", chefService.findAll());
		model.addAttribute("role", chefService.getCredentialsService().getRoleAuthenticated());

		return "elencoChef.html";
	}
	
	
	
	
	
	
	
	
	@GetMapping("/admin/toModificaChef/{id}")
	public String toModificaChef(@PathVariable("id") Long id, Model model) {
		model.addAttribute("chef", chefService.findById(id));

		return "chefFormUpdate.html";
	}
	
	
	
	
	//modifica dati dello chef
	@PostMapping("/admin/modificaChef/{id}") 
	public String modificaChef(@RequestParam("image") MultipartFile multipartFile, @PathVariable("id") Long id,
			Model model, @ModelAttribute("chef") Chef chef ) throws IOException {
		
		Chef chefid = chefService.findById(id);
		
		chefid = chef;
		
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		chefid.setPhotos(fileName);
//		Chef salvaChef = this.chefService.inserisci(chefid);
		String uploadDir = "chef-photos/" + chefid.getId();
		FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		
		chefService.saveChef(chefid);
		
		model.addAttribute("role", chefService.getCredentialsService().getRoleAuthenticated());
		
		return "redirect:/elencoChef";
	}
	
	



}
