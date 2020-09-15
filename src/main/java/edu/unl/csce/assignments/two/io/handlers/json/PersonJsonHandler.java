package edu.unl.csce.assignments.two.io.handlers.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.unl.csce.assignments.two.Main;
import edu.unl.csce.assignments.two.data.Person;
import edu.unl.csce.assignments.two.data.Persons;

import java.io.*;
import java.util.List;

public class PersonJsonHandler {
    private static final File PERSONS_JSON_FILE = new File("./data/Persons.json");

    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create(); //defaults should be fine

    /**
     * Loads all the {@link Person}'s from the Json File
     * @return a List of the Json File's People
     */
    public List<Person> getPeopleFromJson(){
        System.out.println("Converting Persons.json...");

        String jsonContents = Main.getIoManager().getContentsAsString(PERSONS_JSON_FILE);

        Persons personsFromFile = gson.fromJson(jsonContents,Persons.class);
        return personsFromFile.getPersons();
    }
}
