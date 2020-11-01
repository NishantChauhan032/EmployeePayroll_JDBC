package com.capg.jdbc.employeepayroll;

import java.util.List;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeePayrollServiceDB {
	public List<EmployeePayrollData> viewEmployeePayroll() throws DBServiceException {
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		PayrollService obj = new PayrollService();
		EmployeePayrollData employeePayrollData = null;
		String query = "select * from employee_payroll";

		try (Connection connection = obj.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				int id = resultSet.getInt(1);
				String name = resultSet.getString(2);
				double salary = resultSet.getDouble(3);
				LocalDate start = resultSet.getDate(4).toLocalDate();
				employeePayrollData = new EmployeePayrollData(id, name, salary, start);
				employeePayrollList.add(employeePayrollData);
			}
		} catch (Exception e) {
			throw new DBServiceException("SQL Exception", DBServiceExceptionType.SQL_EXCEPTION);
		}
		return employeePayrollList;
	}

}
