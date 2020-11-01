package com.capg.jdbc.employeepayroll;

import java.sql.*;
import java.util.Enumeration;

public class PayrollService {
	public static final String URL = "jdbc:mysql://localhost:3306/payroll_service";
	public static final String USER = "root";
	public static final String PASSWORD = "Password@mysql1";
	private static Connection connection = null;

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver successfully loaded");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Connection successfully established!! " + connection);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot find driver", e);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		listDrivers();
		return connection;
	}

	private static void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println(driverClass.getClass().getName());
		}

	}
}
