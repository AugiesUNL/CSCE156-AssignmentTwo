package com.bc.data.products;

public class Concession extends Product {
    private final double unitCost;

    public Concession(String code, String label, double unitCost) {
        super(code, 'C', label);
        this.unitCost = unitCost;
    }

    public double getUnitCost() {
        return unitCost;
    }

    @Override
    public String toString() {
        return super.toString() +  "Concession{" +
                "unitCost=" + unitCost +
                ;
    }
}
