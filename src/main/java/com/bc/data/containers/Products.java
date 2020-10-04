package com.bc.data.containers;

import com.bc.data.products.Product;

import java.util.List;

/**
 * Class to contain a list of {@link Product} to make parsing easier
 */
public class Products {
    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return this.products;
    }
}
