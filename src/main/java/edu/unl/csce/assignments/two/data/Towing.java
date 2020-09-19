package edu.unl.csce.assignments.two.data;

public class Towing extends Product {
	private double costPerMile;

	public Towing(String code, char type, String label,double costPerMile)
	{
		super(code,type,label);
		this.costPerMile = costPerMile;
	}
	
	public double getCostPerMile() {
		return costPerMile;
	}
}
