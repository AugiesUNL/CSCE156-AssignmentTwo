package edu.unl.csce.assignments.two.data;

public class Concession extends Product {
	private double unitCost;
	
	

	public Concession(String code, char type, String label,double unitCost)
	{
		super(code,type,label);
		this.unitCost = unitCost;
	}
	
	public double getUnitCost() {
		return unitCost;
	}
}
