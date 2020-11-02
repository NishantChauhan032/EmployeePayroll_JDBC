package com.capg.jdbc.employeepayroll;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollServiceDB {

	List<EmployeePayrollData> employeePayrollList;
	EmployeePayrollData employeePayrollData = null;

	public List<EmployeePayrollData> viewEmployeePayroll() throws DBServiceException {

		employeePayrollList = new ArrayList<>();
		EmployeePayrollData employeePayrollData = null;
		String query = "select * from employee_payroll";

		new PayrollService();
		try (Connection connection = PayrollService.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String gender = resultSet.getString(3);
				double salary = resultSet.getDouble(4);
				LocalDate start = resultSet.getDate(5).toLocalDate();
				employeePayrollData = new EmployeePayrollData(id, name, gender, salary, start);
				employeePayrollList.add(employeePayrollData);
			}
		} catch (Exception e) {
			throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
		}
		return employeePayrollList;
	}

	public List<EmployeePayrollData> showEmployeePayrollByName(String name) throws DBServiceException {
		List<EmployeePayrollData> employeePayrollListByName = new ArrayList<>();
		String query = String.format("select * from employee_payroll where name = '%s';", name);
		new PayrollService();
		try (Connection connection = PayrollService.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery(query);
			if (resultSet.next()) {
				int id = resultSet.getInt(1);
				String gender = resultSet.getString(3);
				double salary = resultSet.getDouble(4);
				LocalDate start = resultSet.getDate(5).toLocalDate();
				employeePayrollData = new EmployeePayrollData(id, name, gender, salary, start);
				employeePayrollListByName.add(employeePayrollData);
			}
		} catch (Exception e) {
			throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
		}
		return employeePayrollListByName;
	}

	public void updateEmployeeSalary(String name, double salary) throws DBServiceException {
		String query = String.format("update employee_payroll set salary = %.2f where name = '%s';", salary, name);
		new PayrollService();
		try (Connection connection = PayrollService.getConnection()) {
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate(query);
			employeePayrollData = getEmployeePayrollData(name);
			if (result > 0 && employeePayrollData != null)
				employeePayrollData.setSalary(salary);
		} catch (Exception e) {
			throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
		}
	}
	
	public void updateEmployeeSalaryUsingPreparedStatement(String name, double salary) throws DBServiceException {

		String query = "update employee_payroll set salary = ? where name = ?";
		new PayrollService();
		try(Connection connection = PayrollService.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setDouble(1,salary);
			preparedStatement.setString(2,name);
			int result = preparedStatement.executeUpdate();
			employeePayrollData = getEmployeePayrollData(name);
			if(result > 0 && employeePayrollData != null)
				employeePayrollData.setSalary(salary);
		}catch (Exception e) {
			throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
		}
		
	}

	private EmployeePayrollData getEmployeePayrollData(String name) {
		return employeePayrollList.stream()
				                  .filter(e -> e.getName()
				                  .equals(name))
				                  .findFirst()
				                  .orElse(null);
	}

	public boolean checkForDBSync(String name) throws DBServiceException {
		boolean result = false;
		try {
			result = (showEmployeePayrollByName(name).get(0)).equals(getEmployeePayrollData(name));
		} catch (IndexOutOfBoundsException e) {
		} catch (Exception e) {
			throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
		}
		return result;
	}

	
}
