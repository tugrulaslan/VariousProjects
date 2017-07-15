package com.tugrulaslan.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.tugrulaslan.domain.Country;

public class DatabaseDAO{
	
	private static final String jdbcDriver = "com.mysql.jdbc.Driver";
	private static final String connectionMysql = "jdbc:mysql://localhost:3306/world";
	private static final String dbUser = "root";
	private static final String dbPass = "root";
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private final static String q = "SELECT * FROM country";

	public ArrayList<Country> countryList() {
		ArrayList<Country> countries = new ArrayList<Country>();
		
		try {
			Class.forName(jdbcDriver);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		}

		try {
			connection = DriverManager.getConnection(connectionMysql,
					dbUser, dbPass);
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		try {
			preparedStatement = connection.prepareStatement(q);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Country country = new Country();
				country.setCode(resultSet.getString("Code").trim());
				country.setName(resultSet.getString("Name").trim());
				country.setContinent(resultSet.getString("Continent").trim());
				country.setRegion(resultSet.getString("Region").trim());
				country.setPopulation(resultSet.getInt("Population"));
				countries.add(country);
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println("Exception to close Oracle connection " + e);
				System.out.println(e);
			}
		}
		return countries;
	}
}
