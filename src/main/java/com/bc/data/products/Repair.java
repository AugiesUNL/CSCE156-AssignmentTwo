package com.bc.data.products;

public class Repair extends Product {
    private final double partsCost;
    private final double hourlyLaborCost;

    public Repair(String code, String label, double partsCost, double hourlyLaborCost) {
        super(code, 'F', label);
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
