package com.bc.model.containers;

import com.bc.model.Invoice;

import java.util.List;

/**
 * Class to contain a list of {@link Invoice} to make parsing easier
 * Allows Gson to parse as Json Objects rather than Json Arrays
 */
public class Invoices {
    private List<Invoice> invoices;

    public Invoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices){
        this.invoices = invoices;
    }
}
