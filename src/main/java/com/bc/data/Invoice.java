package com.bc.data;

import com.bc.data.containers.InvoiceProductContainer;
import com.bc.data.products.Product;

import java.util.List;
import java.util.Map;

public class Invoice {
    private final String code;
    private final Person owner;
    private final Customer customer;
    private final List<InvoiceProductContainer> products;

    public Invoice(String code, Person owner, Customer customer, List<InvoiceProductContainer> products) {
        this.code = code;
        this.owner = owner;
        this.customer = customer;
        this.products = products;
    }

    public String getCode() {
        return code;
    }

    public Person getOwner() {
        return owner;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<InvoiceProductContainer> getProducts() {
        return products;
    }
}
