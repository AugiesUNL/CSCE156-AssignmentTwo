package com.bc.io.handlers.json;

import com.bc.DataConverter;
import com.bc.data.Customer;
import com.bc.data.containers.Customers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerJsonHandler {

    private static final File CUSTOMERS_JSON_FILE = new File("./data/Customers.json");

    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create(); //defaults should be fine

    public void saveCustomersToJson() {
        System.out.println("Saving to Customers.json...");
        List<Customer> customers = DataConverter.getIoManager().getCustomerDatHandler().getCustomersFromDat();
        try {
            FileWriter fileWriter = new FileWriter(CUSTOMERS_JSON_FILE);
            gson.toJson(new Customers(customers), fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}