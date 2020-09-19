package edu.unl.csce.assignments.two.data;

public class Repair extends Product{
	private double partsCost;
	private double hourlyLaborCost;
	
	public Repair(String code, char type, String label,double partsCost,double hourlyLaborCost)
	{
		super(code,type,label);
		this.partsCost = partsCost;
		this.hourlyLaborCost = hourlyLaborCost;
	}
	
	
	public double getPartsCost() {
		return partsCost;
	}

	public double getHourlyLaborCost() {
		return hourlyLaborCost;
	}

}