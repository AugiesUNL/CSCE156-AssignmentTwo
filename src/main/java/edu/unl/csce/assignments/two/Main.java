package edu.unl.csce.assignments.two;

import edu.unl.csce.assignments.two.data.Person;
import edu.unl.csce.assignments.two.managers.IoManager;

import java.io.File;
import java.util.ArrayList;

public class Main {
    private static IoManager ioManager;
    private static ArrayList<Person> people;

    public static void main(String [] args){
        ioManager = new IoManager();
        people = new ArrayList<>();
        people.addAll(ioManager.getPersonXmlHandler().getPeopleFromXml());
        System.out.println(people); //Is actually everyone from the xml file.
    }

    public static IoManager getIoManager(){
        return ioManager;
    }

    public static ArrayList<Person> getPeople(){
        return people;
    }
}
