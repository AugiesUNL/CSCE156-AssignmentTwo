package com.bc.data.products;

public class Rental extends Product {
    private final double dailyCost;
    private final double deposit;
    private final double cleaningFee;


    public Rental(String code, char type, String label, double dailyCost, double deposit, double cleaningFee) {
        super(code, type, label);
        this.dailyCost = dailyCost;
        this.deposit = deposit;
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
}
