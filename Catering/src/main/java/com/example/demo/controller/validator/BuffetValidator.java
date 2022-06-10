package com.example.demo.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.model.Buffet;
import com.example.demo.service.BuffetService;


@Component
public class BuffetValidator implements Validator {


	@Autowired 
	private BuffetService buffetService;

	@Override
	public boolean supports(Class<?> pClass) {
		return Buffet.class.equals(pClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//downcast di object
		if(this.buffetService.alreadyExists((Buffet) target)) {
			errors.reject("buffet.duplicato");
		}

	}
}		




