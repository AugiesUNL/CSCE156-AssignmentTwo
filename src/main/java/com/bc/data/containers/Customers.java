package com.bc.data.containers;

import com.bc.data.Customer;

import java.util.List;

/**
 * Class to contain a list of {@link Customer} to make parsing easier
 * Allows Gson to parse as Json Objects rather than Json Arrays
 */
public class Customers {
    private final List<Customer> customers;

    public Customers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return this.customers;
    }
}
