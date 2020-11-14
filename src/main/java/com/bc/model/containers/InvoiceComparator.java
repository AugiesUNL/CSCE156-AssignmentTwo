package com.bc.model.containers;

import com.bc.model.Invoice;

import java.util.Comparator;

public class InvoiceComparator implements Comparator<Invoice> {

    @Override
    public int compare(Invoice o1, Invoice o2) {
        return Comparator.comparing(Invoice::getInvoiceTotal).compare(o1,o2);
    }
}
