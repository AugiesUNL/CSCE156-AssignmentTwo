package com.bc;

import com.bc.data.Customer;
import com.bc.data.Invoice;
import com.bc.data.Person;
import com.bc.data.products.Product;
import com.bc.managers.IoManager;

import java.util.List;

public class InvoiceReport {
    public static void main(String[] args) {
        IoManager ioManager = DataConverter.getIoManager();
        List<Person> people = ioManager.getJsonHandler().loadPersonsFromJson();
        List<Customer> customers = ioManager.getJsonHandler().loadCustomersFromJson();
        List<Product> products = ioManager.getJsonHandler().loadProductsFromJson();
        List<Invoice> invoices = ioManager.getJsonHandler().loadInvoicesFromJson();

        for (Invoice invoice : invoices) {
            System.out.println(invoice.toString());
        }
    }
}
