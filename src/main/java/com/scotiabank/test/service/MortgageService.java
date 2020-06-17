package com.scotiabank.test.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.scotiabank.test.model.History;
import com.scotiabank.test.model.Mortgage;

@Service
public class MortgageService {
	
	
	
	//private SecureRandom random = new SecureRandom();
	public Mortgage addCalculation(Mortgage newMortgage) {
		
		Mortgage mortgage = new Mortgage(newMortgage.getDownpayment(), newMortgage.getPropertyPrice(),
				newMortgage.getInterestRate(), newMortgage.getYears());
		
		double AmountToBorrow = newMortgage.getPropertyPrice() - newMortgage.getDownpayment();
		mortgage.setMonthlyFee(mortgage.calculatePayment(newMortgage.getYears(), 
				newMortgage.getInterestRate(), AmountToBorrow));

		int id = History.getCalculations().size() + 1;
		mortgage.setId(id);

		History.addCalculations(mortgage);

		return mortgage;
	}
	
	public List<Mortgage> retrieveCalculations() {
		
		return History.getCalculations();
	}

}
