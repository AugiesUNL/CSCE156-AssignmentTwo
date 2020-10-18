package com.bc.data.containers;

import com.bc.data.products.Product;

import java.util.List;

/**
 * Class to contain a list of {@link Product} to make parsing easier
 * Allows Gson to parse as Json Objects rather than Json Arrays
 */
public class Products {
    private List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public void setProducts(List<Product> products){
        this.products = products;
    }

    public List<Product> getProducts() {
        return this.products;
    }
}
