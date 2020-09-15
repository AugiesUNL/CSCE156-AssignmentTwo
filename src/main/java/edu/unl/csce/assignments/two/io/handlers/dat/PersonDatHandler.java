package edu.unl.csce.assignments.two.io.handlers.dat;

import edu.unl.csce.assignments.two.Main;
import edu.unl.csce.assignments.two.data.Address;
import edu.unl.csce.assignments.two.data.Person;
import edu.unl.csce.assignments.two.io.utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonDatHandler {
    private static final File PERSONS_DAT_FILE = new File("./data/Persons.dat");

    /**
     * Deserializes a person
     * @param personString the String to deserialize
     * @return the person object
     */
    public Person deserialize(String personString){
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
     * @param person the person to be serialized
     * @return the serialized representation of a person
     */
    public String serialize(Person person){
        StringBuilder sb = new StringBuilder();
        sb.append(person.getPersonCode())
                .append(";")
                .append(person.getLastName())
                .append(", ")
                .append(person.getFirstName())
                .append(";")
                .append(person.getAddress());
        for(String emailAddress : person.getEmails()){
            sb.append(emailAddress).append(",");
        }
        return sb.toString();
    }

    public List<Person> getPeopleFromDat(){
        System.out.println("Converting Persons.dat...");
        ArrayList<Person> people = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(PERSONS_DAT_FILE));
            int linesToRead = Integer.parseInt(bufferedReader.readLine());
            for(int i = 0; i < linesToRead; i++){
                people.add(deserialize(bufferedReader.readLine()));
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        System.out.println("Converted!");
        return people;
    }
}
