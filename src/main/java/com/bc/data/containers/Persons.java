package com.bc.data.containers;

import com.bc.data.Person;

import java.util.List;

/**
 * Class to contain a list of {@link Person} to make parsing easier
 * Allows Gson to parse as Json Objects rather than Json Arrays
 */
public class Persons {
    private List<Person> persons;

    public Persons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Person> getPersons() {
        return this.persons;
    }

    public void setPersons(List<Person> persons){
        this.persons = persons;
    }
}
