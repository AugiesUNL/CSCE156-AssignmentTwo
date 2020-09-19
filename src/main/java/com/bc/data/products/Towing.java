package com.bc.data.products;

import com.bc.data.Product;

public class Towing extends Product {
    private final double costPerMile;

    public Towing(String code, char type, String label, double costPerMile) {
        super(code, type, label);
        this.costPerMile = costPerMile;
    }

    public double getCostPerMile() {
        return costPerMile;
    }
}
