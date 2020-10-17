package com.bc.data.containers;

import com.bc.data.Invoice;

import java.util.List;

/**
 * Class to contain a list of {@link Invoice} to make parsing easier
 * Allows Gson to parse as Json Objects rather than Json Arrays
 */
public class Invoices {
    private final List<Invoice> invoices;

    public Invoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }
}
