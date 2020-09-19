package edu.unl.csce.assignments.two.data;

import java.util.List;

public class Customers {
	 private List<Customer> customers;

	    public Customers(List<Customer> customers){
	        this.customers = customers;
	    }

	    public List<Customer> getCustomers(){
	        return this.customers;
	    }
}
