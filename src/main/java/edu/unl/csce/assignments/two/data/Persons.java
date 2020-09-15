package edu.unl.csce.assignments.two.data;

import java.util.List;

/**
 * A container class made to avoid the pain of a JsonObject with nothing in it but a JsonArray
 * Makes parsing the Persons.json to a {@link java.util.Collection} easier.
 */
public class Persons {
    private List<Person> persons;

    public Persons(List<Person> persons){
        this.persons = persons;
    }

    public List<Person> getPersons(){
        return this.persons;
    }
}
