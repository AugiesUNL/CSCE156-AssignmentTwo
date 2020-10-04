package com.bc.io.handlers.dat;

import com.bc.DataConverter;
import com.bc.data.Customer;
import com.bc.data.Invoice;
import com.bc.data.InvoiceProductData;
import com.bc.data.Person;
import com.bc.data.containers.InvoiceProductContainer;
import com.bc.data.products.*;
import com.bc.io.utils.Utils;
import com.bc.managers.IoManager;

import java.io.File;
import java.util.*;

public class InvoiceDatHandler {
    private static final File INVOICES_DAT_FILE = new File("./data/Invoices.dat");

    public Invoice deserialize(String invoiceString, List<Person> allPeople, List<Customer> allCustomers, List<Product> allProducts){
        List<String> invoiceData = Arrays.asList(invoiceString.split(";"));

        String invoiceCode = invoiceData.get(0);
        String ownerCode = invoiceData.get(1);
        String customerCode = invoiceData.get(2);
        String productListString = invoiceData.get(3);

        Person owner = Utils.getPersonWithCode(allPeople,ownerCode);
        Customer customer = Utils.getCustomerWithCode(allCustomers,customerCode);
        List<InvoiceProductContainer> invoiceProducts = deserializeProductData(productListString, allProducts);

        return new Invoice(invoiceCode,owner,customer,invoiceProducts);
    }

    private List<InvoiceProductContainer> deserializeProductData(String productListString, List<Product> allProducts){
        String[] productStringEntries = productListString.split(",");
        List<InvoiceProductContainer> products = new ArrayList<>();

        for(String productStringEntry : productStringEntries){
            List<String> productEntryData = Arrays.asList(productStringEntry.split(":"));
            Product product = Utils.getProductWithCode(allProducts,productEntryData.get(0));
            InvoiceProductData invoiceProductData = new InvoiceProductData(null,null,null,null,null);
            if(product instanceof Rental){
                int daysRented = Integer.parseInt(productEntryData.get(1));
                invoiceProductData = new InvoiceProductData(daysRented,null,null,null,null);
            }else if(product instanceof Repair){
                int hoursWorked = Integer.parseInt(productEntryData.get(1));
                invoiceProductData = new InvoiceProductData(null,hoursWorked,null,null,null);
            } else if(product instanceof Concession){
                int quantity = Integer.parseInt(productEntryData.get(1));
                Product associatedRepair = productEntryData.size() == 3 ? Utils.getProductWithCode(allProducts,productEntryData.get(2)) : null;
                invoiceProductData = new InvoiceProductData(null,null,quantity,associatedRepair,null);
            } else if (product instanceof Towing){
                int milesTowed = Integer.parseInt(productEntryData.get(1));
                invoiceProductData = new InvoiceProductData(null,null,null,null,milesTowed);
            }else{
                System.err.println("Error parsing productStringEntry: " + productStringEntry);
            }
            products.add(new InvoiceProductContainer(product,invoiceProductData));
        }
        return products;
    }

    public List<Invoice> getInvoicesFromDat(){
        System.out.println("Converting Invoices.dat...");
        List<Invoice> invoices = new ArrayList<>();
        String invoicesString = DataConverter.getIoManager().getContentsAsString(INVOICES_DAT_FILE, true);

        IoManager ioManager = DataConverter.getIoManager();

        List<Person> people = ioManager.getPersonDatHandler().getPeopleFromDat();
        List<Customer> customers = ioManager.getCustomerDatHandler().getCustomersFromDat();
        List<Product> products = ioManager.getProductDatHandler().getProductsFromDat();

        for (String invoicesEntry : invoicesString.split("\n")) {
            invoices.add(deserialize(invoicesEntry,people,customers,products));
        }
        System.out.println("Converted!");
        return invoices;
    }
}
