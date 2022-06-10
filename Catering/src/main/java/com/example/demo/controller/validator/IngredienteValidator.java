package com.example.demo.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Ingrediente;

@Component
public class IngredienteValidator implements Validator {

	@Override
	public boolean supports(Class<?> pClass) {
		return Ingrediente.class.equals(pClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		
	}

}
