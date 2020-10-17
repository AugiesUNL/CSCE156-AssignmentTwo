package com.bc.io.handlers;

import com.bc.DataConverter;
import com.bc.data.*;
import com.bc.data.products.*;
import com.bc.io.utils.Utils;

import java.io.File;
import java.util.*;

public class DatHandler {
    private static final File CUSTOMERS_DAT_FILE = new File("./data/Customers.dat");
    private static final File INVOICES_DAT_FILE = new File("./data/Invoices.dat");
    private static final File PERSONS_DAT_FILE = new File("./data/Persons.dat");
    private static final File PRODUCTS_DAT_FILE = new File("./data/Products.dat");

    /**
     * Deserializes a customer
     *
     * @param customerString the String to deserialize
     * @return the customer object
     */
    public Customer deserializeCustomer(String customerString, List<Person> people) {
        List<String> customerData = Arrays.asList(customerString.split(";"));
        Utils.trimContents(customerData);
        return new Customer(
                customerData.get(0),
                customerData.get(1).charAt(0),
                customerData.get(2),
                Utils.getPersonWithCode(people, customerData.get(3)),
                Address.fromString(customerData.get(4))
        );
    }

    /**
     * Gets all the Customers from the Dat file
     *
     * @return the list of the Dat file's customers
     */
    public List<Customer> getCustomersFromDat() {
        System.out.println("Converting Customers.dat...");
        ArrayList<Customer> customers = new ArrayList<>();
        List<Person> people = getPeopleFromDat();
        String customersString = DataConverter.getIoManager().getContentsAsString(CUSTOMERS_DAT_FILE, true);
        for (String customerEntry : customersString.split("\n")) {
            customers.add(deserializeCustomer(customerEntry, people));
        }
        System.out.println("Converted!");
        return customers;
    }

    public Invoice deserializeInvoice(String invoiceString, List<Person> allPeople, List<Customer> allCustomers, List<Product> allProducts) {
        List<String> invoiceData = Arrays.asList(invoiceString.split(";"));

        String invoiceCode = invoiceData.get(0);
        String ownerCode = invoiceData.get(1);
        String customerCode = invoiceData.get(2);
        String productListString = invoiceData.get(3);

        Person owner = Utils.getPersonWithCode(allPeople, ownerCode);
        Customer customer = Utils.getCustomerWithCode(allCustomers, customerCode);
        Map<Product, InvoiceProductData> invoiceProducts = deserializeProductData(productListString, allProducts);

        return new Invoice(invoiceCode, owner, customer, invoiceProducts);
    }

    private Map<Product, InvoiceProductData> deserializeProductData(String productListString, List<Product> allProducts) {
        String[] productStringEntries = productListString.split(",");
        Map<Product, InvoiceProductData> products = new HashMap<>();

        for (String productStringEntry : productStringEntries) {
            List<String> productEntryData = Arrays.asList(productStringEntry.split(":"));
            Product product = Utils.getProductWithCode(allProducts, productEntryData.get(0));
            InvoiceProductData invoiceProductData = new InvoiceProductData(null, null, null, null, null);
            if (product instanceof Rental) {
                int daysRented = Integer.parseInt(productEntryData.get(1));
                invoiceProductData = new InvoiceProductData(daysRented, null, null, null, null);
            } else if (product instanceof Repair) {
                int hoursWorked = Integer.parseInt(productEntryData.get(1));
                invoiceProductData = new InvoiceProductData(null, hoursWorked, null, null, null);
            } else if (product instanceof Concession) {
                int quantity = Integer.parseInt(productEntryData.get(1));
                Product associatedRepair = productEntryData.size() == 3 ? Utils.getProductWithCode(allProducts, productEntryData.get(2)) : null;
                invoiceProductData = new InvoiceProductData(null, null, quantity, associatedRepair, null);
            } else if (product instanceof Towing) {
                int milesTowed = Integer.parseInt(productEntryData.get(1));
                invoiceProductData = new InvoiceProductData(null, null, null, null, milesTowed);
            } else {
                System.err.println("Error parsing productStringEntry: " + productStringEntry);
            }
            products.put(product, invoiceProductData);
        }
        return products;
    }

    public List<Invoice> getInvoicesFromDat() {
        System.out.println("Converting Invoices.dat...");
        List<Invoice> invoices = new ArrayList<>();
        String invoicesString = DataConverter.getIoManager().getContentsAsString(INVOICES_DAT_FILE, true);

        List<Person> people = getPeopleFromDat();
        List<Customer> customers = getCustomersFromDat();
        List<Product> products = getProductsFromDat();

        for (String invoicesEntry : invoicesString.split("\n")) {
            invoices.add(deserializeInvoice(invoicesEntry, people, customers, products));
        }
        System.out.println("Converted!");
        return invoices;
    }

    /**
     * Deserializes a person
     *
     * @param personString the String to deserialize
     * @return the person object
     */
    public Person deserializePerson(String personString) {
        List<String> personData = Arrays.asList(personString.split(";"));
        List<String> nameData = Arrays.asList(personData.get(1).split(","));
        List<String> emails = personData.size() > 3 ? Arrays.asList(personData.get(3).split(",")) : new ArrayList<>();
        Utils.trimContents(personData);
        Utils.trimContents(nameData);
        Utils.trimContents(emails);
        return new Person(
                personData.get(0),
                nameData.get(0),
                nameData.get(1), //Because it's lastName, firstName (includes space)
                Address.fromString(personData.get(2)),
                emails
        );
    }

    /**
     * personCode;lastName, firstName;address;emailAddress(es)
     *
     * @param person the person to be serialized
     * @return the serialized representation of a person
     */
    public String serializePerson(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(person.getCode())
                .append(";")
                .append(person.getLastName())
                .append(", ")
                .append(person.getFirstName())
                .append(";")
                .append(person.getAddress());
        for (String emailAddress : person.getEmails()) {
            sb.append(emailAddress).append(",");
        }
        return sb.toString();
    }

    /**
     * Gets all the People from the Dat file
     *
     * @return the list of the Dat file's people
     */
    public List<Person> getPeopleFromDat() {
        System.out.println("Converting Persons.dat...");
        List<Person> people = new ArrayList<>();
        String peopleString = DataConverter.getIoManager().getContentsAsString(PERSONS_DAT_FILE, true);
        for (String personEntry : peopleString.split("\n")) {
            people.add(deserializePerson(personEntry));
        }
        System.out.println("Converted!");
        return people;
    }

    /**
     * Deserializes a product
     *
     * @param productString the String to deserialize
     * @return the product object
     */
    public Product deserializeProduct(String productString) {
        List<String> productData = Arrays.asList(productString.split(";"));

        Product product;
        switch (productData.get(1).toLowerCase()) {
            case "r": //rental
                product = new Rental(
                        productData.get(0),

                        productData.get(2),
                        Double.parseDouble(productData.get(3)),
                        Double.parseDouble(productData.get(4)),
                        Double.parseDouble(productData.get(5))
                );
                break;
            case "f": //repair
                product = new Repair(
                        productData.get(0),

                        productData.get(2),
                        Double.parseDouble(productData.get(3)),
                        Double.parseDouble(productData.get(4))
                );
                break;
            case "c": //concession
                product = new Concession(
                        productData.get(0),
                        productData.get(2),
                        Double.parseDouble(productData.get(3))
                );
                break;
            case "t": //towing
                product = new Towing(
                        productData.get(0),

                        productData.get(2),
                        Double.parseDouble(productData.get(3))
                );
                break;
            default:
                product = null;
                break;
        }
        return product;
    }

    /**
     * Gets all the Products from the Dat file
     *
     * @return the list of the Dat file's products
     */
    public List<Product> getProductsFromDat() {
        System.out.println("Converting Products.dat...");
        List<Product> products = new ArrayList<>();
        String productsString = DataConverter.getIoManager().getContentsAsString(PRODUCTS_DAT_FILE, true);
        for (String productsEntry : productsString.split("\n")) {
            products.add(deserializeProduct(productsEntry));
        }
        System.out.println("Converted!");
        return products;
    }
}
