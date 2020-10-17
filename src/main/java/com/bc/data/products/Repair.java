package com.bc.data.products;

import com.bc.data.InvoiceProductData;

public class Repair extends Product {
    private double partsCost;
    private double hourlyLaborCost;

    /**
     * This is purposefully empty.
     * Without this, gson cannot instantiate without creating a {@link com.google.gson.InstanceCreator}
     */
    public Repair(){
        super();
    }

    public Repair(String code, String label, double partsCost, double hourlyLaborCost) {
        super(code, 'F', label);
        this.partsCost = partsCost;
        this.hourlyLaborCost = hourlyLaborCost;
    }

    public void setPartsCost(double partsCost){
        this.partsCost = partsCost;
    }

    public void setHourlyLaborCost(double hourlyLaborCost){
        this.hourlyLaborCost = hourlyLaborCost;
    }

    public double getPartsCost() {
        return partsCost;
    }

    public double getHourlyLaborCost() {
        return hourlyLaborCost;
    }

    @Override
    public double getSubtotal(InvoiceProductData invoiceProductData) {
        return getHourlyLaborCost() * invoiceProductData.getHoursWorked() + getPartsCost();
    }
}
