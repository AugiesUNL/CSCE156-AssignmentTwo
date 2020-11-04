package com.bc.ext;

import com.bc.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for interacting with the database to pull data.
 */
public class DatabaseObjectLoader {

    /**
     * Gets a list of all the invoices stored within the database
     * @return the list of invoices
     */
    public static List<Invoice> getInvoicesFromDatabase(){
        Connection connection = ConnectionFactory.getConnection();
        if(connection==null){
            return null;
        }
        List<Invoice> invoices = new ArrayList<>();

        String getInvoicesQuery = "SELECT * FROM Invoices";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getInvoicesQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Person person = getPerson(resultSet.getInt("personId"));
                Customer customer = getCustomer(resultSet.getInt("customerId"));
                String invoiceCode = resultSet.getString("invoiceCode");
                Map<Product, InvoiceProductData> products = getProductsForInvoice(resultSet.getInt("invoiceId"));
                Invoice invoice = new Invoice(invoiceCode,person,customer,products);
                invoices.add(invoice);
            }
            connection.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return invoices;
    }

    /**
     * Gets the product associated with the invoiceProductData
     * @param invoiceProductDataId the primary key of the InvoiceProductData entry
     * @return the product
     */
    public static Product getProductForData(int invoiceProductDataId){
        Connection connection = ConnectionFactory.getConnection();
        if(connection==null){
            return null;
        }

        Product product = null;
        String getProductQuery = "SELECT productId FROM InvoiceProductsData WHERE invoiceProductsDataId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getProductQuery);
            preparedStatement.setInt(1,invoiceProductDataId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                product = getProduct(resultSet.getInt("productId"));
            }
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    /**
     * Fetches all products pertaining to an invoice
     * @param invoiceId the primary key of the invoice entry
     * @return the list of all the products and their corresponding invoiceProductData
     */
    public static Map<Product,InvoiceProductData> getProductsForInvoice(int invoiceId){
        Connection connection = ConnectionFactory.getConnection();
        if(connection==null) {
            return null;
        }
        Map<Product,InvoiceProductData> products = new HashMap<>();

        String getInvoiceProductsDataQuery = "SELECT * FROM InvoiceProductsData IPD WHERE invoiceId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getInvoiceProductsDataQuery);
            preparedStatement.setInt(1,invoiceId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Product product = getProduct(resultSet.getInt("productId"));
                InvoiceProductData invoiceProductData = new InvoiceProductData(
                        resultSet.getDouble("daysRented"),
                        resultSet.getDouble("hoursWorked"),
                        resultSet.getInt("quantity"),
                        getProductForData(resultSet.getInt("associatedRepairId")),
                        resultSet.getInt("milesTowed")
                );
                products.put(product,invoiceProductData);
            }
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Gets a product
     * @param productId the primary key of the product to retrieve
     * @return the product object
     */
    public static Product getProduct(int productId){
    	Product product = null;
        Connection connection = ConnectionFactory.getConnection();
        if(connection==null){
            return null;
        }

        String getProductQuery = "SELECT * FROM Products WHERE productId = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(getProductQuery);
            preparedStatement.setInt(1,productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                product = getProductWithResultSet(resultSet);
            }
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    /**
     * Gets a customer
     * @param customerId the primary key of the customer to retrieve
     * @return the customer object
     */
    public static Customer getCustomer(int customerId){
    	Customer customer = null;
        Connection connection = ConnectionFactory.getConnection();
        if(connection==null){
            return null;
        }

        String getCustomerQuery = "SELECT * FROM Customer WHERE customerId = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getCustomerQuery);
            preparedStatement.setInt(1,customerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                customer = new Customer(
                        resultSet.getString("customerCode"),
                        resultSet.getString("type").charAt(0),
                        resultSet.getString("name"),
                        getPerson(resultSet.getInt("personId")),
                        getAddress(resultSet.getInt("addressId"))
                );
            }
            connection.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
        return customer;
    }

    /**
     * Gets a person
     * @param personId the primary key of the person to retrieve
     * @return the person object
     */
    public static Person getPerson(int personId){
    	Person person = null;
        Connection connection = ConnectionFactory.getConnection();
        if(connection==null){
            return null;
        }

        String getPersonQuery = "SELECT * FROM Person WHERE personId = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getPersonQuery);
            preparedStatement.setInt(1,personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                person = new Person(
                        resultSet.getString("personCode"),
                        resultSet.getString("lastName"),
                        resultSet.getString("firstName"),
                        getAddress(resultSet.getInt("addressId")),
                        getEmailsForPerson(personId)
                );
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return person;
    }

    /**
     * Gets an address
     * @param addressId the primary key of the address to retrieve
     * @return the address object
     */
    public static Address getAddress(int addressId){
    	Address address = null;
        Connection connection = ConnectionFactory.getConnection();
        if(connection==null){
            return null;
        }

        String getAddressQuery = "SELECT * FROM Address WHERE addressId = ?";
        String getStateQuery = "SELECT * FROM State WHERE stateId = ?";
        String getCountryQuery = "SELECT * FROM Country WHERE countryId = ?";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getAddressQuery);
            preparedStatement.setInt(1,addressId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String street = resultSet.getString("street");
                String city = resultSet.getString("city");
                String zip = resultSet.getString("zip");
                String state;
                String country;
                preparedStatement = connection.prepareStatement(getStateQuery);
                preparedStatement.setInt(1,resultSet.getInt("stateId"));
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    state = resultSet.getString("name");
                    preparedStatement = connection.prepareStatement(getCountryQuery);
                    preparedStatement.setInt(1,resultSet.getInt("countryId"));
                    resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()){
                        country = resultSet.getString("name");
                        address = new Address(street, city, state, zip, country);
                    }
                }
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return address;
    }

    /**
     * Gets emails pertaining to a person
     * @param personId the primary key of the person
     * @return the corresponding list of emails
     */
    public static List<String> getEmailsForPerson(int personId){
        Connection connection = ConnectionFactory.getConnection();
        if(connection==null){
            return null;
        }

        String getEmailsQuery = "SELECT emailAddress FROM Email WHERE personId = ?";

        List<String> emails = new ArrayList<>();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(getEmailsQuery);
            preparedStatement.setInt(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                emails.add(resultSet.getString("emailAddress"));
            }
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }

        return emails;
    }


    /**
     * Method to help readability of code.
     * @param resultSet a resultSet with one Product entry in the database
     * @return a Product object reflective of the database entry
     */
    private static Product getProductWithResultSet(ResultSet resultSet){
    	Product product = null;
        try {
            char type = resultSet.getString("type").toUpperCase().charAt(0);
            String label = resultSet.getString("label");
            String productCode = resultSet.getString("productCode");
            switch (type) {
                case 'R': //Rental
                    product = new Rental(
                            productCode,
                            label,
                            resultSet.getDouble("dailyCost"),
                            resultSet.getDouble("deposit"),
                            resultSet.getDouble("cleaningFee")
                    );
                    break;
                case 'F': //Repair
                	product = new Repair(
                            productCode,
                            label,
                            resultSet.getDouble("partsCost"),
                            resultSet.getDouble("hourlyLaborCost")
                    );
                	break;
                case 'C': //Concession
                	product = new Concession(
                            productCode,
                            label,
                            resultSet.getDouble("unitCost")
                    );
                	break;
                case 'T':
                	product = new Towing(
                            productCode,
                            label,
                            resultSet.getDouble("costPerMile")
                    );
                	break;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return product;
    }


}
