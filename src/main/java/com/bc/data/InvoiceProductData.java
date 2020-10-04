package com.bc.data;

import com.bc.data.products.Product;

public class InvoiceProductData {
    private final Integer daysRented;
    private final Integer hoursWorked;
    private final Integer quantity;
    private final Product associatedRepair;
    private final Integer milesTowed;

    public InvoiceProductData(Integer daysRented, Integer hoursWorked, Integer quantity, Product associatedRepair, Integer milesTowed) {
        this.daysRented = daysRented;
        this.hoursWorked = hoursWorked;
        this.quantity = quantity;
        this.associatedRepair = associatedRepair;
        this.milesTowed = milesTowed;
    }

    public Integer getDaysRented() {
        return daysRented;
    }

    public Integer getHoursWorked() {
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
