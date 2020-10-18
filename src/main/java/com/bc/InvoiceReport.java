package com.bc;

import com.bc.data.Invoice;
import com.bc.managers.IoManager;

import java.util.List;

public class InvoiceReport {
    public static void main(String[] args) {
        IoManager ioManager = DataConverter.getIoManager();
        List<Invoice> invoices = ioManager.getDatHandler().getInvoicesFromDat();

        for (Invoice invoice : invoices) {
            System.out.println(invoice.toString());
        }
    }
}
