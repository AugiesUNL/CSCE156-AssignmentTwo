package edu.unl.csce.assignments.two.data;

public class Product {
	private String code;
	private char type;
	private String label;
//	private double dialyCost;
//	private double deposit;
//	private double cleaingFee;
//	private double partsCost;
//	private double hourlyLaborCost;
//	private double unitCost;
//	private double costPerMile;
	
	public Product(String code, char type, String label)
	{
		this.code = code;
		this.type = type;
		this.label = label;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public char getType()
	{ 
		return type;
	}
	
	public String getlabel()
	{
		return label;
	}
}
