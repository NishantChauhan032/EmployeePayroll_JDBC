package com.capg.jdbc.employeepayroll;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			preparedStatement.setString(1, name);
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
		return viewEmployeePayroll().stream().filter(e -> e.getName().equals(name)).findFirst().orElse(null);
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
		String query = "select * from employee_payroll where start between ? and  ?";
		new PayrollService();
		try (Connection connection = PayrollService.getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
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

	public Map<String, Double> showEmployeeDataGroupedByGender(String column, String operation)
			throws DBServiceException {
		Map<String, Double> employeeDataGroupByGender = new HashMap<>();
		String query = String.format("select gender , %s(%s) from employee_payroll group by gender;", operation,
				column);
		try (Connection connection = new PayrollService().getConnection()) {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				employeeDataGroupByGender.put(resultSet.getString(1), resultSet.getDouble(2));
			}
		} catch (Exception e) {
			throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
		}
		return employeeDataGroupByGender;
	}

	public List<EmployeePayrollData> addNewEmployeeToDB(String name, String gender, double salary,
			LocalDate start_date, int company_id, String department) throws DBServiceException {
		Connection connection = null;
		int id = -1;
		try {
			connection = PayrollService.getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = String.format(
				"insert into employee_payroll(name , gender, salary , start,company_id)"
						+ "values ('%s','%s','%s','%s','%s');",
				name, gender, salary, Date.valueOf(start_date), company_id);
		try (Statement statement = connection.createStatement()) {

			int rowAffected = statement.executeUpdate(query, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					id = resultSet.getInt(1);
				employeePayrollData = new EmployeePayrollData(name, gender, salary, start_date, company_id);
				viewEmployeePayroll().add(employeePayrollData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
				return viewEmployeePayroll();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try (Statement statement = connection.createStatement()) {
			String query3 = String.format("insert into department(id,dept_name)values('%s','%s');", id,
					department);
			int rowAffected = statement.executeUpdate(query3, Statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try (Statement statement = connection.createStatement()) {
			double deductions = salary * 0.2;
			double taxablePay = salary - deductions;
			double tax = taxablePay * 0.1;
			double netPay = taxablePay = tax;
			String query1 = String.format(
					"insert into payroll_details(id,basic_pay,deductions,taxablePay,tax,netPay)"
							+ " values ('%s','%s','%s','%s','%s','%s');",
					id, salary, deductions, taxablePay, tax, netPay);
			int rowAffected = statement.executeUpdate(query1);
			if (rowAffected == 1) {
				employeePayrollData = new EmployeePayrollData(id, name, gender, salary, start_date);
			}
		} catch (SQLException e3) {
			e3.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		try {
			connection.commit();
		} catch (SQLException e4) {
			e4.printStackTrace();
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e5) {
					e5.printStackTrace();
				}
		}
		return viewEmployeePayroll();
	}

}
