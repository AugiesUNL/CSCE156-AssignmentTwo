package com.bc.model.containers;

import com.bc.model.Customer;

import java.util.List;

/**
 * Class to contain a list of {@link Customer} to make parsing easier
 * Allows Gson to parse as Json Objects rather than Json Arrays
 */
public class Customers {
    private List<Customer> customers;

    public Customers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return this.customers;
    }

    public void setCustomers(List<Customer> customers){
        this.customers = customers;
    }
}
