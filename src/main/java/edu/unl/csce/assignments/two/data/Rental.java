package edu.unl.csce.assignments.two.data;

public class Rental extends Product {
	private double dailyCost;
	private double deposit;
	private double cleaningFee;
	


	public Rental(String code, char type, String label,double dailyCost,double deposit,double cleaningFee)
	{
		super(code,type,label);
		this.dailyCost = dailyCost;
		this.deposit = deposit;
		this.cleaningFee = cleaningFee;
	}

	public double getDailyCost() {
		return dailyCost;
	}

	public double getDeposit() {
		return deposit;
	}

	public double getCleaningFee() {
		return cleaningFee;
	}
}
