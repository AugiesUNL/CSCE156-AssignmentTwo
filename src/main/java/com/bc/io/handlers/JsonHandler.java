package com.bc.io.handlers;

import com.bc.DataConverter;
import com.bc.model.Customer;
import com.bc.model.Invoice;
import com.bc.model.Person;
import com.bc.model.containers.Customers;
import com.bc.model.containers.Invoices;
import com.bc.model.containers.Persons;
import com.bc.model.containers.Products;
import com.bc.model.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonHandler {
    private static final File CUSTOMERS_JSON_FILE = new File("./data/Customers.json");
    private static final File INVOICES_JSON_FILE = new File("./data/Invoices.json");
    private static final File PERSONS_JSON_FILE = new File("./data/Persons.json");
    private static final File PRODUCTS_JSON_FILE = new File("./data/Products.json");
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create(); //defaults should be fine

    /**
     * Saves the customers to a json file
     */
    public void saveCustomersToJson() {
        System.out.println("Saving to Customers.json...");
        List<Customer> customers = DataConverter.getIoManager().getDatHandler().getCustomersFromDat();
        try { //writing to file
            FileWriter fileWriter = new FileWriter(CUSTOMERS_JSON_FILE);
            gson.toJson(new Customers(customers), fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> loadCustomersFromJson() {
        System.out.println("Loading from Customers.json");
        Customers customers = gson.fromJson(DataConverter.getIoManager().getContentsAsString(CUSTOMERS_JSON_FILE), Customers.class);
        System.out.println("Loaded!");
        return customers.getCustomers();
    }

    /**
     * Saves the invoices to a json file
     */
    public void saveInvoicesToJson() {
        System.out.println("Saving to Invoices.json...");
        List<Invoice> invoices = DataConverter.getIoManager().getDatHandler().getInvoicesFromDat();
        try { //writing to file
            FileWriter fileWriter = new FileWriter(INVOICES_JSON_FILE);
            gson.toJson(new Invoices(invoices), fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Invoice> loadInvoicesFromJson() {
        System.out.println("Loading from Invoices.json");
        Invoices invoices = gson.fromJson(DataConverter.getIoManager().getContentsAsString(INVOICES_JSON_FILE), Invoices.class);
        System.out.println("Loaded!");
        return invoices.getInvoices();
    }

    /**
     * Saves the peope to a json file
     */
    public void savePeopleToJson() {
        System.out.println("Saving to Persons.json...");
        List<Person> people = DataConverter.getIoManager().getDatHandler().getPeopleFromDat();
        try { //writing to file
            FileWriter fileWriter = new FileWriter(PERSONS_JSON_FILE);
            gson.toJson(new Persons(people), fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Person> loadPersonsFromJson() {
        System.out.println("Loading from Persons.json");
        Persons persons = gson.fromJson(DataConverter.getIoManager().getContentsAsString(PERSONS_JSON_FILE), Persons.class);
        System.out.println("Loaded!");
        return persons.getPersons();
    }

    /**
     * Saves the products to a json file
     */
    public void saveProductsToJson() {
        System.out.println("Saving to Products.json...");
        List<Product> products = DataConverter.getIoManager().getDatHandler().getProductsFromDat();
        try { //writing to file
            FileWriter fileWriter = new FileWriter(PRODUCTS_JSON_FILE);
            gson.toJson(new Products(products), fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Product> loadProductsFromJson() {
        System.out.println("Loading from Products.json");
        Products products = gson.fromJson(DataConverter.getIoManager().getContentsAsString(PRODUCTS_JSON_FILE), Products.class);
        System.out.println("Loaded!");
        return products.getProducts();
    }
}
