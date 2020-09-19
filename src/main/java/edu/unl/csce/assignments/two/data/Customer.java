package edu.unl.csce.assignments.two.data;

import java.util.List;

public class Customer {
	
	private String code;
	private String name;
	private Person contact;
	private Address address;
	private char type;
	
	public Customer(String code, char type, String name, Person contact, Address address) {
		this.code = code;
		this.type = type;
		this.name = name;
		this.address = address;
		this.contact = contact;
		// TODO Auto-generated constructor stub
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public Address getAddress() {
		return address;
	}
	public Person getContact()
	{
		return contact;
	}
	
	public char getType()
	{
		return type;
	}
	

}
