package com.capg.jdbc.employeepayroll;

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
		employeePayrollServiceDB.updateEmployeeSalary("Terisa", 3000000.0);
		boolean checkIfSynced = employeePayrollServiceDB.checkForDBSync("Terisa");
		Assert.assertTrue(checkIfSynced);
	}
}
