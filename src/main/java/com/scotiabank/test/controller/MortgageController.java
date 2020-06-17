package com.scotiabank.test.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.scotiabank.test.model.Mortgage;
import com.scotiabank.test.service.MortgageService;

@RestController
@RequestMapping("/api")
public class MortgageController {

	@Autowired
	private MortgageService mortgageService;

	@Autowired
	ObjectMapper mapper;

	@GetMapping("/mortgage/m-history")
	public List<Mortgage> retrieveCalculationsFromHistory() {
		return mortgageService.retrieveCalculations();
	}

	@PostMapping("/happy-path")
	ResponseEntity<Object> age(@Valid @RequestBody Mortgage newMortgage) {

//		String double_pattern = "[0-9]+(\\.){0,1}[0-9]*";
//		String integer_pattern = "\\d+";

		if ((newMortgage.getDownpayment()) > 0.1 * newMortgage.getPropertyPrice()) {

			List<ObjectNode> errors = new ArrayList<ObjectNode>();
			ObjectNode objectNode = mapper.createObjectNode();

			objectNode.put("code", HttpStatus.BAD_REQUEST.value());
			objectNode.put("message", "The downpayment can only cover up to 10% of the property price");
			errors.add(objectNode);
			return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
//		}else if(Pattern.matches(double_pattern, newMortgage.getPropertyPrice())) {
//			
		}

		else {

			Mortgage mortgage = mortgageService.addCalculation(newMortgage);
			return new ResponseEntity<Object>(mortgage, HttpStatus.OK);
		}
	}
}
