package com.bc.io.handlers.json;

import com.bc.DataConverter;
import com.bc.data.Person;
import com.bc.data.containers.Persons;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PersonJsonHandler {
    private static final File PERSONS_JSON_FILE = new File("./data/Persons.json");

    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create(); //defaults should be fine

    public void savePeopleToJson() {
        System.out.println("Saving to Persons.json...");
        List<Person> people = DataConverter.getIoManager().getPersonDatHandler().getPeopleFromDat();
        try {
            FileWriter fileWriter = new FileWriter(PERSONS_JSON_FILE);
            gson.toJson(new Persons(people), fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
