package com.bc.data.products;

import com.bc.data.Product;

public class Concession extends Product {
    private final double unitCost;

    public Concession(String code, char type, String label, double unitCost) {
        super(code, type, label);
        this.unitCost = unitCost;
    }

    public double getUnitCost() {
        return unitCost;
    }
}
