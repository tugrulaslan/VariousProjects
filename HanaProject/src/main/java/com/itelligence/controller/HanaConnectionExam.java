package com.itelligence.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import com.sap.db.jdbc.Driver;

public class HanaConnectionExam {
	public static void main(String[] argv) throws ClassNotFoundException {
		Connection connection = null;
		try {
//			Class<Driver> dnm = (Class<Driver>) Class.forName("com.sap.db.jdbc.Driver");
//			dnm.newInstance().connect(arg0, arg1)
//			Enumeration<java.sql.Driver> drvs = DriverManager.getDrivers();
//			while(drvs.hasMoreElements()){
//				drvs.nextElement().connect("jdbc:sap://10.70.17.60:30015/?autocommit=false", null);
//			}
			
			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			
			connection = DriverManager.getConnection("jdbc:sap://10.70.17.60:30015/?autocommit=false", "HYBRISUSER",
					"Tac1976*");
		} catch (SQLException e) {
			System.err.println("Connection Failed. User/Passwd Error?");
			return;
		}
		if (connection != null) {
			try {
				System.out.println("Connection to HANA successful!");
				Statement stmt = connection.createStatement();
				ResultSet resultSet = stmt.executeQuery("Select 'hello world' from dummy");
				resultSet.next();
				String hello = resultSet.getString(1);
				System.out.println(hello);
			} catch (SQLException e) {
				System.err.println("Query failed!");
			}
		}
	}
}