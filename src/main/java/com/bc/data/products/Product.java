package com.bc.data.products;

import com.bc.data.Invoice;
import com.bc.data.InvoiceProductData;
import com.bc.data.products.properties.Discountable;

public abstract class Product implements Discountable {
    private String code;
    private char type;
    private String label;

    public Product(String code, char type, String label) {
        this.code = code;
        this.type = type;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public char getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public void setCode(String code){
        this.code = code;
    }

    public void setType(char type){
        this.type = type;
    }

    public void setLabel(String label){
        this.label = label;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", type=" + type +
                ", label='" + label + '\'' +
                '}';
    }

    public abstract double getSubtotal(InvoiceProductData invoiceProductData);

    public double getTotalBeforeTaxes(InvoiceProductData invoiceProductData, Invoice invoice) {
        return getSubtotal(invoiceProductData) + getDiscount(invoiceProductData, invoice);
    }

    public double getTaxes(InvoiceProductData invoiceProductData, Invoice invoice) {
        return getTotalBeforeTaxes(invoiceProductData, invoice) * invoice.getTaxRate();
    }

    public double getTotal(InvoiceProductData invoiceProductData, Invoice invoice) {
        return getTotalBeforeTaxes(invoiceProductData, invoice) - getTaxes(invoiceProductData, invoice);
    }
}
