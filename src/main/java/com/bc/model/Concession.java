package com.bc.model;

public class Concession extends Product {
    private double unitCost;

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
        if(invoiceProductData.getAssociatedRepair()!=null) {
        	discount-=getSubtotal(invoiceProductData)*.1;
        }
        return discount;
    }
}
