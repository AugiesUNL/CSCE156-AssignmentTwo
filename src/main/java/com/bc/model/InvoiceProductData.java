package com.bc.model;

public class InvoiceProductData {
    private final Double daysRented;
    private final Double hoursWorked;
    private final Integer quantity;
    private final Product associatedRepair;
    private final Integer milesTowed;

    public InvoiceProductData(Double daysRented, Double hoursWorked, Integer quantity, Product associatedRepair, Integer milesTowed) {
        this.daysRented = daysRented;
        this.hoursWorked = hoursWorked;
        this.quantity = quantity;
        this.associatedRepair = associatedRepair;
        this.milesTowed = milesTowed;
    }

    public Double getDaysRented() {
        return daysRented;
    }

    public Double getHoursWorked() {
        return hoursWorked;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product getAssociatedRepair() {
        return associatedRepair;
    }

    public Integer getMilesTowed() {
        return milesTowed;
    }
}
