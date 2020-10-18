package com.bc.data.products;

import com.bc.data.InvoiceProductData;

public class Rental extends Product {
    private double dailyCost;
    private double deposit;
    private double cleaningFee;

    public Rental(String code, String label, double dailyCost, double deposit, double cleaningFee) {
        super(code, 'R', label);
        this.dailyCost = dailyCost;
        this.deposit = deposit;
        this.cleaningFee = cleaningFee;
    }

    public void setDailyCost(double dailyCost){
        this.dailyCost = dailyCost;
    }

    public void setDeposit(double deposit){
        this.deposit = deposit;
    }

    public void setCleaningFee(double cleaningFee){
        this.cleaningFee = cleaningFee;
    }

    public double getDailyCost() {
        return dailyCost;
    }

    public double getDeposit() {
        return deposit;
    }

    public double getCleaningFee() {
        return cleaningFee;
    }

    @Override
    public double getSubtotal(InvoiceProductData invoiceProductData) {
        return getCleaningFee() + getDeposit() + getDailyCost() * invoiceProductData.getDaysRented();
    }
}
