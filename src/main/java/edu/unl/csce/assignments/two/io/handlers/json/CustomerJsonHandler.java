package edu.unl.csce.assignments.two.io.handlers.json;

import java.io.File;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.unl.csce.assignments.two.Main;
import edu.unl.csce.assignments.two.data.Customer;
import edu.unl.csce.assignments.two.data.Customers;
import edu.unl.csce.assignments.two.data.Person;
import edu.unl.csce.assignments.two.data.Persons;

public class CustomerJsonHandler {
    
	private static final File CUSTOMERS_JSON_FILE = new File("./data/Customers.json");

    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create(); //defaults should be fine

    /**
     * Loads all the {@link Customers}'s from the Json File
     * @return a List of the Json File's Customer
     */
    public List<Customer> getCustomersFromJson(){
        System.out.println("Converting Customers.json...");

        String jsonContents = Main.getIoManager().getContentsAsString(CUSTOMERS_JSON_FILE);

        Customers customersFromFile = gson.fromJson(jsonContents,Customers.class);
        return customersFromFile.getCustomers();
    
    }
}
