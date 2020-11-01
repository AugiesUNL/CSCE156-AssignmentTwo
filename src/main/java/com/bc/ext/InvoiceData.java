package com.bc.ext;

import java.sql.*;

/* DO NOT change or remove the import statements beneath this.
 * They are required for the webgrader to run this phase of the project.
 * These lines may be giving you an error; this is fine.
 * These are webgrader code imports, you do not need to have these packages.
 */
import com.bc.model.*;


/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 * 16 methods in total, add more if required.
 * Do not change any method signatures or the package name.
 * <p>
 * Adapted from Dr. Hasan's original version of this file
 *
 * @author Chloe, Augies
 */
public class InvoiceData {

    /**
     * 1. Method that removes every person record from the database
     */
    public static void removeAllPersons() {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String[] queries = {
                "UPDATE InvoiceProductsData SET associatedRepairId = NULL;",
                "DELETE FROM InvoiceProductsData;",
                "DELETE FROM Invoices;",
                "DELETE FROM Products;",
                "DELETE FROM Customer;",
                "DELETE FROM Email;",
                "DELETE FROM Person;"
        };

        executeDeleteQueries(conn, queries);
    }

    /**
     * 2. Method to add a person record to the database with the provided data.
     *
     * @param personCode the code of the person to be added
     * @param firstName  the first name of the person
     * @param lastName   the last name of the person
     * @param street     the street address of the person
     * @param city       the city of the address
     * @param state      the state of the address
     * @param zip        the zip code of the address
     * @param country    the country of the address
     */
    public static void addPerson(String personCode, String firstName, String lastName, String street, String city, String state, String zip, String country) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        //Adding entries through stored procedures makes the java side look neater
        //And it helps to clarify the separation of concerns between the database and java application
        String query = "{call addPerson(?,?,?,?,?,?,?,?)}";
        CallableStatement callableStatement = null;
        try {
            callableStatement = prepStatementForStringList(conn, query,
                    personCode,
                    firstName,
                    lastName,
                    street,
                    city,
                    state,
                    zip,
                    country
                    );
            callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{callableStatement.close();} catch(Exception ignored){}
            try{conn.close();} catch(Exception ignored){}
        }
    }

    /**
     * 3. Adds an email record corresponding person record corresponding to the
     * provided <code>personCode</code>
     *
     * @param personCode the code of the person to update
     * @param email      the email to add to the person
     */
    public static void addEmail(String personCode, String email) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String personIdQuery = "SELECT personId FROM Person WHERE personCode = ?";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(personIdQuery);
            preparedStatement.setString(1, personCode);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                int personId = resultSet.getInt("personId");
                String insertEmailQuery = String.format("INSERT INTO Email(emailAddress,personId) VALUES('%s',%d)", email, personId);
                Statement statement = conn.createStatement();
                statement.execute(insertEmailQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{resultSet.close();} catch(Exception ignored){}
            try{preparedStatement.close();} catch(Exception ignored){}
            try{conn.close();} catch(Exception ignored){}
        }
    }

    /**
     * 4. Method that removes every customer record from the database
     */
    public static void removeAllCusomters() {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String[] queries = {
                "UPDATE InvoiceProductsData SET associatedRepairId = NULL;",
                "DELETE FROM InvoiceProductsData;",
                "DELETE FROM Invoices;",
                "DELETE FROM Customer;"
        };

        executeDeleteQueries(conn,queries);
    }

    /**
     * 5. Method to add a customer record to the database with the provided data
     *
     * @param customerCode             the code of the Customer
     * @param customerType             the type of Customer
     * @param primaryContactPersonCode the contact code of the customer
     * @param name                     the name of the customer
     * @param street                   the street of the address
     * @param city                     the city of the address
     * @param state                    the state of the address
     * @param zip                      the zip of the address
     * @param country                  the country of the address
     */
    public static void addCustomer(String customerCode, String customerType, String primaryContactPersonCode, String name, String street, String city, String state, String zip, String country) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String type = customerType.toUpperCase().substring(0, 1);
        String query = "{call addCustomer(?,?,?,?,?,?,?,?,?)}";
        CallableStatement callableStatement = null;

        try {
            callableStatement = prepStatementForStringList(conn, query,
                    customerCode,
                    type,
                    primaryContactPersonCode,
                    name,
                    street,
                    city,
                    state,
                    zip,
                    country
            );
            callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{callableStatement.close();} catch(Exception ignored){}
            try{conn.close();} catch(Exception ignored){}
        }
    }

    /**
     * 6. Removes all product records from the database
     */
    public static void removeAllProducts() {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String[] queries = {
                "UPDATE InvoiceProductsData SET associatedRepairId = NULL;",
                "DELETE FROM InvoiceProductsData;",
                "DELETE FROM Invoices;",
                "DELETE FROM Products;"
        };

        executeDeleteQueries(conn, queries);
    }

    private static void executeDeleteQueries(Connection conn, String[] queries) {
        PreparedStatement pre = null;
        try {
            for (String query : queries) {
                pre = conn.prepareStatement(query);
                pre.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{pre.close();} catch(Exception ignored){}
            try{conn.close();} catch(Exception ignored){}
        }
    }

    /**
     * 7. Adds a concession record to the database with the provided data.
     *
     * @param productCode  the code of the product to be added
     * @param productLabel the label of the product to be added
     * @param unitCost     the cost per unit of concession
     */
    public static void addConcession(String productCode, String productLabel, double unitCost) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String query = "{call addConcession(?,?,?)}";
        CallableStatement callableStatement = null;
        try {
            callableStatement = prepStatementForProduct(conn, query, productCode, productLabel);
            callableStatement.setDouble(3, unitCost);
            callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            try{callableStatement.close();} catch(Exception ignored){}
            try{conn.close();} catch(Exception ignored){}
        }
    }

    /**
     * 8. Adds a repair record to the database with the provided data.
     *
     * @param productCode  the code of the product to be added
     * @param productLabel the label of the product to be added
     * @param partsCost    the cost for parts
     * @param laborRate    the cost for labor per hour
     */
    public static void addRepair(String productCode, String productLabel, double partsCost, double laborRate) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String query = "{call addRepair(?,?,?,?)}";
        CallableStatement callableStatement = null;

        try {
            callableStatement = prepStatementForProduct(conn, query, productCode, productLabel);
            callableStatement.setDouble(3, partsCost);
            callableStatement.setDouble(4, laborRate);
            callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{callableStatement.close();} catch(Exception ignored){}
            try{conn.close();} catch(Exception ignored){}
        }
    }

    /**
     * 9. Adds a towing record to the database with the provided data.
     *
     * @param productCode  the code of the product to be added
     * @param productLabel the label of the product to be added
     * @param costPerMile  the cost per mile towed
     */
    public static void addTowing(String productCode, String productLabel, double costPerMile) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String query = "{call addTowing(?,?,?)}";
        CallableStatement callableStatement = null;

        try {
            callableStatement = prepStatementForProduct(conn, query, productCode, productLabel);
            callableStatement.setDouble(3, costPerMile);
            callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{callableStatement.close();} catch(Exception ignored){}
            try{conn.close();} catch(Exception ignored){}
        }
    }

    /**
     * 10. Adds a rental record to the database with the provided data.
     *
     * @param productCode  the code of the product to be added
     * @param productLabel the label of the product to be added
     * @param dailyCost    the cost per day rented
     * @param deposit      the initial deposit fee
     * @param cleaningFee  the fee to clean after rental
     */
    public static void addRental(String productCode, String productLabel, double dailyCost, double deposit, double cleaningFee) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String query = "{call addRental(?,?,?,?,?)}";
        CallableStatement callableStatement = null;

        try {
            callableStatement = prepStatementForProduct(conn, query, productCode, productLabel);
            callableStatement.setDouble(3, dailyCost);
            callableStatement.setDouble(4, deposit);
            callableStatement.setDouble(5, cleaningFee);
            callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{callableStatement.close();} catch(Exception ignored){}
            try{conn.close();} catch(Exception ignored){}
        }
    }

    /**
     * 11. Removes all invoice records from the database
     */
    public static void removeAllInvoices() {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String[] queries = {
                "UPDATE InvoiceProductsData SET associatedRepairId = NULL;",
                "DELETE FROM InvoiceProductsData;",
                "DELETE FROM Invoices;"
        };

        executeDeleteQueries(conn,queries);
    }

    /**
     * 12. Adds an invoice record to the database with the given data.
     *
     * @param invoiceCode  the code of the invoice to be added
     * @param ownerCode    the code of the owner for the invoice
     * @param customerCode the code of the customer of the invoice
     */
    public static void addInvoice(String invoiceCode, String ownerCode, String customerCode) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String query = "{call addInvoice(?,?,?)}";
        CallableStatement callableStatement = null;

        try {
            callableStatement = conn.prepareCall(query);
            callableStatement.setString(1, invoiceCode);
            callableStatement.setString(2, ownerCode);
            callableStatement.setString(3, customerCode);
            callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{callableStatement.close();} catch(Exception ignored){}
            try{conn.close();} catch(Exception ignored){}
        }
    }

    /**
     * 13. Adds a particular Towing (corresponding to <code>productCode</code>
     * to an invoice corresponding to the provided <code>invoiceCode</code> with
     * the given number of miles towed
     *
     * @param invoiceCode the code for the invoice to be added to
     * @param productCode the code for the associated towing
     * @param milesTowed  the number of miles towed
     */
    public static void addTowingToInvoice(String invoiceCode, String productCode, double milesTowed) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String query = "{call addTowingToInvoice(?,?,?)}";
        CallableStatement callableStatement = null;

        try {
            callableStatement = conn.prepareCall(query);
            prepStatementForAddToInvoice(callableStatement,invoiceCode,productCode);
            callableStatement.setDouble(3, milesTowed);
            callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{callableStatement.close();} catch(Exception ignored){}
            try{conn.close();} catch(Exception ignored){}
        }
    }

    /**
     * 14. Adds a particular Repair (corresponding to <code>productCode</code>
     * to an invoice corresponding to the provided <code>invoiceCode</code> with
     * the given number of hours worked
     *
     * @param invoiceCode the code for the invoice to be added to
     * @param productCode the code for the associated repair
     * @param hoursWorked the number of hours worked
     */
    public static void addRepairToInvoice(String invoiceCode, String productCode, double hoursWorked) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String query = "{call addRepairToInvoice(?,?,?)}";
        CallableStatement callableStatement = null;

        try {
            callableStatement = conn.prepareCall(query);
            prepStatementForAddToInvoice(callableStatement,invoiceCode,productCode);
            callableStatement.setDouble(3, hoursWorked);
            callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{callableStatement.close();} catch(Exception ignored){}
            try{conn.close();} catch(Exception ignored){}
        }
    }

    /**
     * 15. Adds a particular Concession (corresponding to <code>productCode</code> to an
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of quantity.
     * NOTE: repairCode may be null
     *
     * @param invoiceCode the code for the invoice to be added to
     * @param productCode the code for the associated repair
     * @param quantity    the quantity of concessions purchased
     * @param repairCode  the code of an associated repair, if any
     */
    public static void addConcessionToInvoice(String invoiceCode, String productCode, int quantity, String repairCode) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String query = "{call addConcessionToInvoice(?,?,?,?)}";
        CallableStatement callableStatement = null;

        try {
            callableStatement = conn.prepareCall(query);
            prepStatementForAddToInvoice(callableStatement,invoiceCode,productCode);
            callableStatement.setInt(3, quantity);
            callableStatement.setString(4, repairCode);
            callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{callableStatement.close();} catch(Exception ignored){}
            try{conn.close();} catch(Exception ignored){}
        }
    }

    /**
     * 16. Adds a particular Rental (corresponding to <code>productCode</code> to an
     * invoice corresponding to the provided <code>invoiceCode</code> with the given
     * number of days rented.
     *
     * @param invoiceCode the code for the invoice to be added to
     * @param productCode the code for the associated repair
     * @param daysRented  the days the rental was rented
     */
    public static void addRentalToInvoice(String invoiceCode, String productCode, double daysRented) {
        Connection conn = ConnectionFactory.getConnection();
        if (conn == null) {
            System.err.println("Error: Unable to establish connection.");
            return;
        }

        String query = "{call addRentalToInvoice(?,?,?)}";
        CallableStatement callableStatement = null;

        try {
            callableStatement = conn.prepareCall(query);
            prepStatementForAddToInvoice(callableStatement,invoiceCode,productCode);
            callableStatement.setDouble(3, daysRented);
            callableStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{callableStatement.close();} catch(Exception ignored){}
            try{conn.close();} catch(Exception ignored){}
        }
    }

    /**
     * A utility method to prevent code duplication between similar methods
     * {@link #addRentalToInvoice(String,String,double)}
     * {@link #addConcessionToInvoice(String,String,int,String)}
     * {@link #addRepairToInvoice(String, String, double)}
     * {@link #addTowingToInvoice(String, String, double)}
     * @param callableStatement the callableStatement to modify
     * @param invoiceCode the code of the invoice
     * @param productCode the code of the product
     */
    private static void prepStatementForAddToInvoice(CallableStatement callableStatement, String invoiceCode, String productCode){
        try {
            callableStatement.setString(1, invoiceCode);
            callableStatement.setString(2, productCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * A utility method to prevent code duplication between similar methods
     * {@link #addTowing(String, String, double)}
     * {@link #addRental(String, String, double, double, double)}
     * {@link #addRepair(String, String, double, double)}
     * {@link #addConcession(String, String, double)}
     * @param connection the connection to work off of
     * @param query the query to prepare
     * @param productCode the code of the product
     * @param productLabel the label for the product
     * @return a {@link CallableStatement} with the first two variables inserted
     */
    private static CallableStatement prepStatementForProduct(Connection connection, String query, String productCode, String productLabel){
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall(query);
            callableStatement.setString(1, productCode);
            callableStatement.setString(2, productLabel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return callableStatement;
    }

    /**
     * Utility method to prevent code duplication between similar methods
     * Prepares a {@link CallableStatement} with Strings inserted for index 1-variables.length
     * @param connection the connection to work off of
     * @param query the query to prepare
     * @param variables the variables to insert into the query
     * @return a prepared {@link CallableStatement}
     */
    private static CallableStatement prepStatementForStringList(Connection connection, String query, String... variables){
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall(query);
            for(int i = 0; i < variables.length; i++){
                callableStatement.setString(i+1,variables[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return callableStatement;
    }
}

