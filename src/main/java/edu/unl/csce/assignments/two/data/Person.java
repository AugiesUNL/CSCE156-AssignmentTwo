package edu.unl.csce.assignments.two.data;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private final String personCode;
    private final String lastName;
    private final String firstName;
    private final Address address;
    private final List<String> emails;

    public Person(String personCode, String lastName, String firstName, Address address, List<String> emails) {
        this.personCode = personCode;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.emails = emails;
    }

    public Person(String personCode, String lastName, String firstName, Address address) {
        this.personCode = personCode;
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.emails = new ArrayList<>();
    }

    public String getPersonCode() {
        return personCode;
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
}
