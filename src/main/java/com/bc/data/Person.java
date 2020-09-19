package com.bc.data;

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

    @Override
    public String toString() {
        return "Person{" +
                "code='" + code + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", address=" + address +
                ", emails=" + emails +
                '}';
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
}
