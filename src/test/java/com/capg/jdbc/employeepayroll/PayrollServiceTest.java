package com.capg.jdbc.employeepayroll;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
		assertEquals(3, empPayrollList.size());
	}
}
