package com.bc.data.products;

import com.bc.data.Invoice;
import com.bc.data.InvoiceProductData;

public class Concession extends Product {
    private double unitCost;

    /**
     * This is purposefully empty.
     * Without this, gson cannot instantiate without creating a {@link com.google.gson.InstanceCreator}
     */
    public Concession(){
        super();
    }

    public Concession(String code, String label, double unitCost) {
        super(code, 'C', label);
        this.unitCost = unitCost;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost){
        this.unitCost = unitCost;
    }

    @Override
    public double getSubtotal(InvoiceProductData invoiceProductData) {
        return getUnitCost() * invoiceProductData.getQuantity();
    }

    @Override
    public double getDiscount(InvoiceProductData invoiceProductData, Invoice invoice) {
        double discount = 0;
        if (invoice.hasConcessionWithAssociatedRepair()) {
            discount -= getSubtotal(invoiceProductData) * .1;
        }
        return discount;
    }
}
