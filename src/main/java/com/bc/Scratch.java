package com.bc;

import com.bc.data.*;
import com.bc.data.products.Product;
import com.bc.data.products.Rental;
import com.bc.data.products.Towing;
import com.bc.io.handlers.InvoiceData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Scratch {
    public static void main(String[] args) {
        Address address1 = new Address("13906 Poppleton Circle", "Omaha", "NE", "68144", "United States"); //String street, String city, String state, String zip, String country
        Address address2 = new Address("2325 N. 64 Street", "Omaha", "NE", "68104", "United States");
        String[] emails1 = {"shald.augie@gmail.com", "augies@cox.net", "ashald2@huskers.unl.edu"};
        Person person = new Person("personCode", "lastName", "firstName", address2, Arrays.asList(emails1)); //String code, String lastName, String firstName, Address address, List<String> emails
        Customer customer = new Customer("customerCode", 'B', "The Business Name", person, address1);
        Towing product1 = new Towing("product1", "Towing Label", 13.52);
        Rental product2 = new Rental("product2", "Rental Label", 91.23, 200, 39.51);
        InvoiceProductData invoiceProductData1 = new InvoiceProductData(null, null, null, null, 123);
        InvoiceProductData invoiceProductData2 = new InvoiceProductData(4, null, null, null, null);
        Map<Product, InvoiceProductData> products = new HashMap<>();
        products.put(product1, invoiceProductData1);
        products.put(product2, invoiceProductData2);
        Invoice invoice = new Invoice("code", person, customer, products); //String code, Person owner, Customer customer, List<InvoiceProductContainer> products

        Address address3 = new Address("13906 Poppleton Circle", "Omaha", "NE", "68144", "United States"); //String street, String city, String state, String zip, String country
        Address address4 = new Address("2325 N. 64 Street", "Omaha", "NE", "68104", "United States");
        String[] emails3 = {"shald.augie@gmail.com", "augies@cox.net", "ashald2@huskers.unl.edu"};
        Person person2 = new Person("personCode", "lastName", "firstName", address3, Arrays.asList(emails3)); //String code, String lastName, String firstName, Address address, List<String> emails
        Customer customer2 = new Customer("customerCode", 'B', "The Business Name", person2, address4);
        Towing product3 = new Towing("product1", "Towing Label", 13.52);
        Rental product4 = new Rental("product2", "Rental Label", 91.23, 200, 39.51);
        InvoiceProductData invoiceProductData3 = new InvoiceProductData(null, null, null, null, 123);
        InvoiceProductData invoiceProductData4 = new InvoiceProductData(4, null, null, null, null);
        Map<Product, InvoiceProductData> products2 = new HashMap<>();
        products.put(product3, invoiceProductData3);
        products.put(product4, invoiceProductData4);
        Invoice invoice2 = new Invoice("code", person2, customer2, products2); //String code, Person owner, Customer customer, List<InvoiceProductContainer> products

        ArrayList<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice);
        invoices.add(invoice2);

        InvoiceReport.printSummary(invoices);

        InvoiceData.removeAllPersons();
    }
}
