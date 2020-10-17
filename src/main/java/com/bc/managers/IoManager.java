package com.bc.managers;

import com.bc.io.handlers.DatHandler;
import com.bc.io.handlers.JsonHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * A manager of all Input/Output operations
 */
public class IoManager {
    private final DatHandler datHandler = new DatHandler();

    private final JsonHandler jsonHandler = new JsonHandler();

    public DatHandler getDatHandler() {
        return this.datHandler;
    }

    public JsonHandler getJsonHandler() {
        return this.jsonHandler;
    }

    public String getContentsAsString(File file) {
        return getContentsAsString(file, false);
    }

    /**
     * Gets the contents of a file as a String
     *
     * @param file         the file whose contents needs retrieved
     * @param hasFirstLine whether or not to skip the first line
     * @return a string with the file's contents
     */
    public String getContentsAsString(File file, boolean hasFirstLine) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            if (hasFirstLine) {
                bufferedReader.readLine();
            }
            String nextLine;
            sb.append(bufferedReader.readLine()); //Do one iteration to make \n's easier
            while ((nextLine = bufferedReader.readLine()) != null) {
                sb.append("\n").append(nextLine);
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return sb.toString();
    }
}
