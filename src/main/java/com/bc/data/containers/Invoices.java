package com.bc.data.containers;

import com.bc.data.Invoice;

import java.util.List;

public class Invoices {
    private final List<Invoice> invoices;

    public Invoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }
}
