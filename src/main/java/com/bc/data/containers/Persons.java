package com.bc.data.containers;

import com.bc.data.Customer;
import com.bc.data.Person;

import java.util.List;

/**
 * Class to contain a list of {@link Person} to make parsing easier
 */
public class Persons {
    private final List<Person> persons;

    public Persons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return this.persons;
    }
}
