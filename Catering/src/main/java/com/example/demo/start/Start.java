package com.example.demo.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.example.demo.model.Credentials;
import com.example.demo.model.Ingrediente;
import com.example.demo.model.Piatto;
import com.example.demo.service.ChefService;
import com.example.demo.service.CredentialsService;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.PiattoService;

@Component
public class Start implements ApplicationListener<ContextRefreshedEvent>{


	
	@Autowired
	private PiattoService ps;
	
	@Autowired
	private CredentialsService cs;
	
	@Autowired 
	private IngredienteService is;
	
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		
//		Piatto p1 = new Piatto();
//		
//		Ingrediente i1 = new Ingrediente("nome1", "descrizione1", "origine1");
//		Ingrediente i2 = new Ingrediente("nome2", "descrizione2", "origine2");
//
//		
//		p1.addIngrediente(i2);
//		
//		ps.save(p1); 
		
//		Credentials c1 = new Credentials("aa", "bb");
//		
//		cs.saveCredentials(c1);
//		
//		Ingrediente i1 = new Ingrediente("nome1", "descrizione1", "origine1");
//		is.save(i1);
		
		
		
	}

	
}
