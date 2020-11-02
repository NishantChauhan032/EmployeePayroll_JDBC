package com.capg.jdbc.employeepayroll;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PayrollServiceTest 
{
	static EmployeePayrollServiceDB employeePayrollServiceDB;
	@BeforeClass
	public static void setUp()  {
		employeePayrollServiceDB = new EmployeePayrollServiceDB();
	}

	@Test
	public void givenEmpPayrollDB_WhenRetrieved_ShouldMatchEmpCount() throws DBServiceException{
		List<EmployeePayrollData> empPayrollList = employeePayrollServiceDB.viewEmployeePayroll();
		Assert.assertEquals(3, empPayrollList.size());
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
		Assert.assertEquals(3, employeePayrollList.size());
	}
}
