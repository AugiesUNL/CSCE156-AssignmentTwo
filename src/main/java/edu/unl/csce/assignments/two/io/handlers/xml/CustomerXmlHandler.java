package edu.unl.csce.assignments.two.io.handlers.xml;

import java.io.File;
import java.util.List;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import edu.unl.csce.assignments.two.data.Address;
import edu.unl.csce.assignments.two.data.Customer;
import edu.unl.csce.assignments.two.data.Person;
import edu.unl.csce.assignments.two.data.Persons;

public class CustomerXmlHandler {
    private static final File PERSONS_XML_FILE = new File("./data/Customers.xml");

    private final XStream xStream;

    public CustomerXmlHandler(){
        xStream = new XStream(new DomDriver());
        xStream.alias("customer",Customer.class);
        //xStream.alias("persons", Persons.class);
        xStream.alias("Address", Address.class);
        xStream.aliasField("Address",Customer.class,"address");
        xStream.addImplicitCollection(Persons.class,"persons", Person.class);
    }

    public List<Person> getPeopleFromXml(){
        Persons persons = (Persons) xStream.fromXML(PERSONS_XML_FILE);
        return persons.getPersons();
    }
}
