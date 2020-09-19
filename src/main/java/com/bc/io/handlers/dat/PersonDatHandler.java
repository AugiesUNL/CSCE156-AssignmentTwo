package com.bc.io.handlers.dat;

import com.bc.DataConverter;
import com.bc.data.Address;
import com.bc.data.Person;
import com.bc.io.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonDatHandler {
    private static final File PERSONS_DAT_FILE = new File("./data/Persons.dat");

    /**
     * Deserializes a person
     *
     * @param personString the String to deserialize
     * @return the person object
     */
    public Person deserialize(String personString) {
        List<String> personData = Arrays.asList(personString.split(";"));
        List<String> nameData = Arrays.asList(personData.get(1).split(","));
        List<String> emails = personData.size() > 3 ? Arrays.asList(personData.get(3).split(",")) : new ArrayList<>();
        Utils.trimContents(personData);
        Utils.trimContents(nameData);
        Utils.trimContents(emails);
        return new Person(
                personData.get(0),
                nameData.get(0),
                nameData.get(1), //Because it's lastName, firstName (includes space)
                Address.fromString(personData.get(2)),
                emails
        );
    }

    /**
     * personCode;lastName, firstName;address;emailAddress(es)
     *
     * @param person the person to be serialized
     * @return the serialized representation of a person
     */
    public String serialize(Person person) {
        StringBuilder sb = new StringBuilder();
        sb.append(person.getCode())
                .append(";")
                .append(person.getLastName())
                .append(", ")
                .append(person.getFirstName())
                .append(";")
                .append(person.getAddress());
        for (String emailAddress : person.getEmails()) {
            sb.append(emailAddress).append(",");
        }
        return sb.toString();
    }

    /**
     * Gets all the People from the Dat file
     * @return the list of the Dat file's people
     */
    public List<Person> getPeopleFromDat() {
        System.out.println("Converting Persons.dat...");
        List<Person> people = new ArrayList<>();
        String peopleString = DataConverter.getIoManager().getContentsAsString(PERSONS_DAT_FILE, true);
        for (String personEntry : peopleString.split("\n")) {
            people.add(deserialize(personEntry));
        }
        System.out.println("Converted!");
        return people;
    }
}
