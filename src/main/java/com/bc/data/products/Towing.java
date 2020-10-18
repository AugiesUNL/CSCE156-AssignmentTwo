package com.bc.data.products;

import com.bc.data.Invoice;
import com.bc.data.InvoiceProductData;

public class Towing extends Product {
    private double costPerMile;

    public Towing(String code, String label, double costPerMile) {
        super(code, 'T', label);
        this.costPerMile = costPerMile;
    }

    public void setCostPerMile(double costPerMile){
        this.costPerMile = costPerMile;
    }

    public double getCostPerMile() {
        return costPerMile;
    }

    @Override
    public double getSubtotal(InvoiceProductData invoiceProductData) {
        return getCostPerMile() * invoiceProductData.getMilesTowed();
    }

    @Override
    public double getDiscount(InvoiceProductData towingProductContainer, Invoice invoice) {
        double discount = 0;
        if (invoice.hasTowingRepairRental()) {
            discount -= getSubtotal(towingProductContainer);
        }
        return discount;
    }
}
