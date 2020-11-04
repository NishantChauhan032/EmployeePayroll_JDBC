package com.capg.jdbc.employeepayroll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PayrollServiceTest 
{
	static EmployeePayrollServiceDB employeePayrollServiceDB;
	static Map<String, Double> employeeDataGroupByGender;
	static List<EmployeePayrollData> employeePayrollList;
	@BeforeClass
	public static void setUp()  {
		employeePayrollServiceDB = new EmployeePayrollServiceDB();
		employeeDataGroupByGender = new HashMap<>();
		employeePayrollList = new ArrayList<>();
	}

	@Test
	public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchEmpCount() throws DBServiceException{
		List<EmployeePayrollData> empPayrollList = employeePayrollServiceDB.viewEmployeePayroll();
		Assert.assertEquals(4, empPayrollList.size());
	}
	
	@Test
	public void givenUpdatedSalary_WhenRetrieved_ShouldBeSyncedWithDB() throws DBServiceException{
		employeePayrollServiceDB.updateEmployeeSalary("Terisa", 3000000.00);
		boolean checkIfSynced = employeePayrollServiceDB.checkForDBSync("Terisa");
		Assert.assertTrue(checkIfSynced);
	}
	
	@Test
	public void givenUpdatedSalary_WhenRetrieved_ShouldBeSyncedWithDBUsingPreparedStatement() throws DBServiceException{
		employeePayrollServiceDB.updateEmployeeSalaryUsingPreparedStatement("Terisa", 3000000.00);
		boolean checkIfSynced = employeePayrollServiceDB.checkForDBSync("Terisa");
		Assert.assertTrue(checkIfSynced);
	}
	
	@Test
	public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() throws DBServiceException{
		List<EmployeePayrollData> employeePayrollList = employeePayrollServiceDB.showEmployeeJoinedWithinADateRange(LocalDate.of(2018,01,01), LocalDate.now() );
		Assert.assertEquals(4, employeePayrollList.size());
	}
	
	@Test
	public void givenEmployeeDB_WhenRetrievedSum_ShouldReturnSumGroupedByGender() throws DBServiceException {
		employeeDataGroupByGender = employeePayrollServiceDB.showEmployeeDataGroupedByGender("salary" , "sum");
		Assert.assertEquals(4000000, employeeDataGroupByGender.get("M"), 0);
		Assert.assertEquals(3000000, employeeDataGroupByGender.get("F"), 0);
	}

	@Test
	public void givenEmployeeDB_WhenRetrievedAvg_ShouldReturnAvgByGroupedGender() throws DBServiceException {
		employeeDataGroupByGender = employeePayrollServiceDB.showEmployeeDataGroupedByGender("salary" , "avg");
		Assert.assertEquals(3000000, employeeDataGroupByGender.get("M"), 0);
		Assert.assertEquals(3000000, employeeDataGroupByGender.get("F"), 0);
	}

	@Test
	public void givenEmployeeDB_WhenRetrievedMax_ShouldReturnMaxGroupedByGender() throws DBServiceException {
		employeeDataGroupByGender = employeePayrollServiceDB.showEmployeeDataGroupedByGender("salary" , "max");
		Assert.assertEquals(5000000, employeeDataGroupByGender.get("M"), 0);
		Assert.assertEquals(3000000, employeeDataGroupByGender.get("F"), 0);
	}
	
	@Test
	public void givenEmployeeDB_WhenRetrievedMin_ShouldReturnMinGroupedByGender() throws DBServiceException {
		employeeDataGroupByGender = employeePayrollServiceDB.showEmployeeDataGroupedByGender("salary" , "min");
		Assert.assertEquals(1000000, employeeDataGroupByGender.get("M"), 0);
		Assert.assertEquals(3000000, employeeDataGroupByGender.get("F"), 0);
	}

	@Test
	public void givenEmployeeDB_WhenRetrievedCount_ShouldReturnCountGroupedByGender() throws DBServiceException {
		employeeDataGroupByGender = employeePayrollServiceDB.showEmployeeDataGroupedByGender("salary", "count");
		Assert.assertEquals(2, employeeDataGroupByGender.get("M"), 0);
		Assert.assertEquals(1, employeeDataGroupByGender.get("F"), 0);
	}
	
	@Test
	public void addedNewEmployee_WhenRetrieved_ShouldBeSyncedWithDB() throws DBServiceException{
		employeePayrollServiceDB.addNewEmployeeToDB("Mark" , "M", 5000000.0 , LocalDate.now());
		boolean checkIfSynced = employeePayrollServiceDB.checkForDBSync("Mark");
		Assert.assertTrue(checkIfSynced);
	}
}
