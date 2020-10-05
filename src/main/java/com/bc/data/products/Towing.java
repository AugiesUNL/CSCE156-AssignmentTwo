package com.bc.data.products;

public class Towing extends Product {
    private final double costPerMile;

    public Towing(String code, String label, double costPerMile) {
        super(code, 'T', label);
        this.costPerMile = costPerMile;
    }

    public double getCostPerMile() {
        return costPerMile;
    }
}
