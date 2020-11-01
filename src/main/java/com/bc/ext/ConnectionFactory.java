package com.bc.ext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * A class to establish a connection to a CSE database.
 */
public class ConnectionFactory {
	private static boolean initializedDriver = false;
    private static final String PARAMETERS = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "";
	private static final String PASSWORD = "";
	private static final String URL = "jdbc:mysql://cse.unl.edu/" + USERNAME + PARAMETERS;

    public static Connection getConnection() {
    	try {
    		if(!initializedDriver){
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				initializedDriver = true;
			}
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
    	
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
