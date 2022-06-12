package com.example.demo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Ingrediente;
import com.example.demo.service.IngredienteService;

@Component
public class IngredienteValidator implements Validator {
	
	@Autowired 
	private IngredienteService ingredienteService;

	@Override
	public boolean supports(Class<?> pClass) {
		return Ingrediente.class.equals(pClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(this.ingredienteService.alreadyExists((Ingrediente) target)) {
			errors.reject("chef.duplicato");
		}		
	}

}
