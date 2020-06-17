package com.scotiabank.test.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Mortgage {

	private int id;
	@NotNull(message = "The property price cannot be null")
//	@Digits(integer=10, fraction=1, message="The property must be a number")
	@Min(value = 0, message = "The property price must be a positive number")
	private double propertyPrice;
	@NotNull(message = "The interest rate cannot be null")
//	@Digits(integer=10, fraction=1, message="The interest rate must be a number")	
	@Min(value = 0, message = "The interest rate must be a positive number")
	private double interestRate;
	@NotNull(message = "The years must have a value")
//	@Digits(integer=10, fraction=1, message="The years field must be a number")
	@Min(value = 0, message = "The years to finance must be a positive value")
	@Max(value = 30, message = "The years to finance cannot exceed {value}")
	private int years;
	@NotNull(message = "The downpayment must have a value")
//	@Digits(integer=10, fraction=1, message="The downpayment must be a number")
	private double downpayment;
	private double monthlyFee;
	
	public Mortgage() {}
	
	public Mortgage(@JsonProperty("downpayment")double downpayment, @JsonProperty("propertyPrice")double propertyPrice, 
			@JsonProperty("interestRate")double interestRate, @JsonProperty("years")int years) {
		super();

		this.downpayment = downpayment;
		this.propertyPrice = propertyPrice;
		this.interestRate = interestRate;
		this.years = years;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getDownpayment() {
		return downpayment;
	}

	public void setDownpayment(double downpayment) {
		this.downpayment = downpayment;
	}

	public double getPropertyPrice() {
		return propertyPrice;
	}

	public void setPropertyPrice(double propertyPrice) {
		this.propertyPrice = propertyPrice;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public double getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = roundFee(monthlyFee,4);
	}

	public double calculatePayment(int years, double interestRate, double loan) {

		double months = (double) (years * 12);
		double monthlyInterestRate = (interestRate/100)/12;
		double monthlyPayment = loan * ((monthlyInterestRate * Math.pow(1 + monthlyInterestRate, months))
				/ (Math.pow(1 + monthlyInterestRate, months) - 1));
		return monthlyPayment;
	}
	
	private static double roundFee(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	 
	    BigDecimal bd = new BigDecimal(Double.toString(value));
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}

	@Override
	public String toString() {
		return "mortgage [propertyPrice=" + propertyPrice + ", interestRate=" + interestRate + ", years=" + years
				+ ", downpayment=" + downpayment + "]";
	}

}
