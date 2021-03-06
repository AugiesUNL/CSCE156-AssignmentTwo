package com.bc.model;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private final String code;
    private final String lastName;
    private final String firstName;
    private final Address address;
    private final List<String> emails;

    public Person(String code, String lastName, String firstName, Address address, List<String> emails) {
        this.code = code;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.emails = emails;
    }

    public Person(String code, String lastName, String firstName, Address address) {
        this.code = code;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.emails = new ArrayList<>();
    }


    /*
    Owner:
        Battle, Petra
        [danextpm@canada.gov]
        1 Blue Jays Way
        Toronto, ON Canada M5V 1J1
     */
    @Override
    public String toString() {
        String emailsString = emails.size() > 0 ? emails.toString() : "[no emails on record]";
        return String.format("Owner:%n        %s, %s%n        %s%n        %s", lastName, firstName, emailsString, address);
    }

    public String getName(){
        return this.lastName + ", " + this.firstName;
    }

    public String getCode() {
        return code;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public Address getAddress() {
        return address;
    }

    public List<String> getEmails() {
        return emails;
    }

    public int getNumOfEmails() {
        return emails.size();
    }
}
