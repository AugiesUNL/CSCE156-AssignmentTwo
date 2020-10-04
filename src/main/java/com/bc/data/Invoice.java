package com.bc.data;

import com.bc.data.products.Product;

import java.util.List;
import java.util.Map;

public class Invoice {
    private final String code;
    private final Person owner;
    private final Customer customer;
    private final Map<Product,InvoiceProductData> products;

    public Invoice(String code, Person owner, Customer customer, Map<Product,InvoiceProductData> products) {
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

    public Map<Product,InvoiceProductData> getProducts() {
        return products;
    }
}
