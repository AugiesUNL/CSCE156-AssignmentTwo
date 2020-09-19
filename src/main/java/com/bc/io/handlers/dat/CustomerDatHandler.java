package com.bc.io.handlers.dat;

import com.bc.DataConverter;
import com.bc.data.Address;
import com.bc.data.Customer;
import com.bc.data.Person;
import com.bc.io.utils.Utils;
import com.bc.managers.IoManager;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerDatHandler {
    private static final File CUSTOMERS_DAT_FILE = new File("./data/Customers.dat");

    /**
     * Deserializes a customer
     *
     * @param customerString the String to deserialize
     * @return the customer object
     * customerCode;customerType;customerName;primaryContactCode;address
     */
    public Customer deserialize(String customerString, List<Person> people) {
        List<String> customerData = Arrays.asList(customerString.split(";"));
        Utils.trimContents(customerData);
        return new Customer(
                customerData.get(0),
                customerData.get(1).charAt(0),
                customerData.get(2),
                getPersonWithCode(people, customerData.get(3)),
                Address.fromString(customerData.get(4))
        );
    }

    private Person getPersonWithCode(List<Person> people, String code){
        for(Person person : people){
            if(person.getCode().equals(code)){
                return person;
            }
        }
        return null;
    }

    public List<Customer> getCustomersFromDat() {
        System.out.println("Converting Customers.dat...");
        ArrayList<Customer> customers = new ArrayList<>();
        List<Person> people = DataConverter.getIoManager().getPersonDatHandler().getPeopleFromDat();
        String customersString = DataConverter.getIoManager().getContentsAsString(CUSTOMERS_DAT_FILE, true);
        for (String customerEntry : customersString.split("\n")) {
            customers.add(deserialize(customerEntry, people));
        }
        System.out.println("Converted!");
        return customers;
    }
}