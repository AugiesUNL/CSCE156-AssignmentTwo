package com.bc.data.products;

import com.bc.data.Product;

public class Repair extends Product {
    private final double partsCost;
    private final double hourlyLaborCost;

    public Repair(String code, char type, String label, double partsCost, double hourlyLaborCost) {
        super(code, type, label);
        this.partsCost = partsCost;
        this.hourlyLaborCost = hourlyLaborCost;
    }


    public double getPartsCost() {
        return partsCost;
    }

    public double getHourlyLaborCost() {
        return hourlyLaborCost;
    }

}
