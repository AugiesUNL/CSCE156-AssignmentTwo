package edu.unl.csce.assignments.two.io.handlers.dat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.unl.csce.assignments.two.Main;
import edu.unl.csce.assignments.two.data.Address;
import edu.unl.csce.assignments.two.data.Customer;
import edu.unl.csce.assignments.two.data.Person;
import edu.unl.csce.assignments.two.io.utils.Utils;

public class CustomerDatHandler {
    private static final File CUSTOMERS_DAT_FILE = new File("./data/Customers.dat");

    /**
     * Deserializes a customer
     * @param customerString the String to deserialize
     * @return the customer object
     * customerCode;customerType;customerName;primaryContactCode;address
     */
    public Customer deserialize(String customerString){
        List<String> customerData = Arrays.asList(customerString.split(";"));
        Utils.trimContents(customerData);
        return new Customer(
                customerData.get(0),
                customerData.get(1).charAt(0),
                customerData.get(2), 
                Main.getIoManager().getPersonDatHandler().getPerson(customerData.get(3)),
                Address.fromString(customerData.get(4))
        );
    }

    

    public List<Customer> getCustomerFromDat(){
        System.out.println("Converting Customers.dat...");
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(CUSTOMERS_DAT_FILE));
            int linesToRead = Integer.parseInt(bufferedReader.readLine());
            for(int i = 0; i < linesToRead; i++){
                customers.add(deserialize(bufferedReader.readLine()));
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        System.out.println("Converted!");
        return customers;
    }
}
