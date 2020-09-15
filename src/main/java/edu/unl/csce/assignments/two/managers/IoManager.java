package edu.unl.csce.assignments.two.managers;

import edu.unl.csce.assignments.two.io.handlers.dat.PersonDatHandler;
import edu.unl.csce.assignments.two.io.handlers.json.PersonJsonHandler;
import edu.unl.csce.assignments.two.io.handlers.xml.PersonXmlHandler;

import java.io.*;


public class IoManager {
    private final PersonDatHandler personDatHandler = new PersonDatHandler();
    private final PersonJsonHandler personJsonHandler = new PersonJsonHandler();
    private final PersonXmlHandler personXmlHandler = new PersonXmlHandler();

    public PersonDatHandler getPersonDatHandler(){
        return this.personDatHandler;
    }

    public PersonJsonHandler getPersonJsonHandler() {
        return this.personJsonHandler;
    }

    public PersonXmlHandler getPersonXmlHandler(){
        return this.personXmlHandler;
    }

    public String getContentsAsString(File file){
        return getContentsAsString(file,false);
    }

    public String getContentsAsString(File file, boolean hasFirstLine){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            if(hasFirstLine){
                bufferedReader.readLine();
            }
            String nextLine;
            sb.append(bufferedReader.readLine()); //Do one iteration to make \n's easier
            while((nextLine = bufferedReader.readLine()) != null){
                sb.append("\n").append(nextLine);
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return sb.toString();
    }
}
