package edu.unl.csce.assignments.two.io.handlers.xml;

import com.thoughtworks.xstream.XStream;
import edu.unl.csce.assignments.two.Main;
import edu.unl.csce.assignments.two.data.Address;
import edu.unl.csce.assignments.two.data.Person;
import edu.unl.csce.assignments.two.data.Persons;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonXmlHandler {
    private static final File PERSONS_XML_FILE = new File("./data/Persons.xml");

    private final XStream xStream;

    public PersonXmlHandler(){
        xStream = new XStream()
        xStream.alias("person",Person.class);
        xStream.alias("persons", Persons.class);
        xStream.alias("Address", Address.class);
        xStream.addImplicitCollection(Person.class,"emails");
        xStream.addImplicitCollection(Persons.class,"persons");
    }

    public List<Person> getPeopleFromXml(){
        Persons persons = (Persons) xStream.fromXML(PERSONS_XML_FILE);
        return persons.getPersons();
    }
}
