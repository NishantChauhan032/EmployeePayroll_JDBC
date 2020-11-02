package com.capg.jdbc.employeepayroll;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollServiceDB {

	EmployeePayrollData employeePayrollData = null;

	public List<EmployeePayrollData> viewEmployeePayroll() throws DBServiceException {

		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		
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
		String query = String.format("select * from employee_payroll where name = ?;", name);
		new PayrollService();
		try (Connection connection = PayrollService.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name );
			ResultSet resultSet = preparedStatement.executeQuery();
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
		try (Connection connection = PayrollService.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setDouble(1, salary);
			preparedStatement.setString(2, name);
			int result = preparedStatement.executeUpdate();
			employeePayrollData = getEmployeePayrollData(name);
			if (result > 0 && employeePayrollData != null)
				employeePayrollData.setSalary(salary);
		} catch (Exception e) {
			throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
		}

	}

	private EmployeePayrollData getEmployeePayrollData(String name) throws DBServiceException {
		return viewEmployeePayroll().stream()
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

	public List<EmployeePayrollData> showEmployeeJoinedWithinADateRange(LocalDate startDate, LocalDate endDate)
			throws DBServiceException {
		List<EmployeePayrollData> employeePayrollListJoinedWithinADateRange = new ArrayList<>();
		String query = "select * from Employee_Payroll where start_date between ? and  ?";
		new PayrollService();
		try (Connection con = PayrollService.getConnection()) {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setDate(1, Date.valueOf(startDate));
			preparedStatement.setDate(2, Date.valueOf(endDate));
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				String gender = resultSet.getString(3);
				double salary = resultSet.getDouble(4);
				LocalDate start = resultSet.getDate(5).toLocalDate();
				employeePayrollData = new EmployeePayrollData(id, name, gender, salary, start);
				employeePayrollListJoinedWithinADateRange.add(employeePayrollData);
			}
		} catch (Exception e) {
			throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
		}
		return employeePayrollListJoinedWithinADateRange;
	}

}
