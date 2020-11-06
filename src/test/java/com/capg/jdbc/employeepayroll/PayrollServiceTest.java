package com.capg.jdbc.employeepayroll;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class PayrollServiceTest {
	static EmployeePayrollServiceDB employeePayrollServiceDB;
	static Map<String, Double> employeeDataGroupByGender;
	static List<EmployeePayrollData> employeePayrollList;

	@BeforeClass
	public static void setUp() {
		employeePayrollServiceDB = new EmployeePayrollServiceDB();
		employeeDataGroupByGender = new HashMap<>();
		employeePayrollList = new ArrayList<>();
	}

	@Ignore
	@Test
	public void givenEmployeePayrollDB_WhenRetrieved_ShouldMatchEmpCount() throws DBServiceException {
		List<EmployeePayrollData> empPayrollList = employeePayrollServiceDB.viewEmployeePayroll();
		Assert.assertEquals(4, empPayrollList.size());
	}

	@Ignore
	@Test
	public void givenUpdatedSalary_WhenRetrieved_ShouldBeSyncedWithDB() throws DBServiceException {
		employeePayrollServiceDB.updateEmployeeSalary("Terisa", 3000000.00);
		boolean checkIfSynced = employeePayrollServiceDB.checkForDBSync("Terisa");
		Assert.assertTrue(checkIfSynced);
	}

	@Ignore
	@Test
	public void givenUpdatedSalary_WhenRetrieved_ShouldBeSyncedWithDBUsingPreparedStatement()
			throws DBServiceException {
		employeePayrollServiceDB.updateEmployeeSalaryUsingPreparedStatement("Terisa", 3000000.00);
		boolean checkIfSynced = employeePayrollServiceDB.checkForDBSync("Terisa");
		Assert.assertTrue(checkIfSynced);
	}

	@Ignore
	@Test
	public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() throws DBServiceException {
		List<EmployeePayrollData> employeePayrollList = employeePayrollServiceDB
				.showEmployeeJoinedWithinADateRange(LocalDate.of(2018, 01, 01), LocalDate.now());
		Assert.assertEquals(4, employeePayrollList.size());
	}

	@Ignore
	@Test
	public void givenEmployeeDB_WhenRetrievedSum_ShouldReturnSumGroupedByGender() throws DBServiceException {
		employeeDataGroupByGender = employeePayrollServiceDB.showEmployeeDataGroupedByGender("salary", "sum");
		Assert.assertEquals(4000000, employeeDataGroupByGender.get("M"), 0);
		Assert.assertEquals(3000000, employeeDataGroupByGender.get("F"), 0);
	}

	@Ignore
	@Test
	public void givenEmployeeDB_WhenRetrievedAvg_ShouldReturnAvgByGroupedGender() throws DBServiceException {
		employeeDataGroupByGender = employeePayrollServiceDB.showEmployeeDataGroupedByGender("salary", "avg");
		Assert.assertEquals(3000000, employeeDataGroupByGender.get("M"), 0);
		Assert.assertEquals(3000000, employeeDataGroupByGender.get("F"), 0);
	}

	@Ignore
	@Test
	public void givenEmployeeDB_WhenRetrievedMax_ShouldReturnMaxGroupedByGender() throws DBServiceException {
		employeeDataGroupByGender = employeePayrollServiceDB.showEmployeeDataGroupedByGender("salary", "max");
		Assert.assertEquals(5000000, employeeDataGroupByGender.get("M"), 0);
		Assert.assertEquals(3000000, employeeDataGroupByGender.get("F"), 0);
	}

	@Ignore
	@Test
	public void givenEmployeeDB_WhenRetrievedMin_ShouldReturnMinGroupedByGender() throws DBServiceException {
		employeeDataGroupByGender = employeePayrollServiceDB.showEmployeeDataGroupedByGender("salary", "min");
		Assert.assertEquals(1000000, employeeDataGroupByGender.get("M"), 0);
		Assert.assertEquals(3000000, employeeDataGroupByGender.get("F"), 0);
	}

	@Ignore
	@Test
	public void givenEmployeeDB_WhenRetrievedCount_ShouldReturnCountGroupedByGender() throws DBServiceException {
		employeeDataGroupByGender = employeePayrollServiceDB.showEmployeeDataGroupedByGender("salary", "count");
		Assert.assertEquals(2, employeeDataGroupByGender.get("M"), 0);
		Assert.assertEquals(1, employeeDataGroupByGender.get("F"), 0);
	}

	@Ignore
	@Test
	public void addedNewEmployee_WhenRetrieved_ShouldBeSyncedWithDB() throws DBServiceException {
		employeePayrollServiceDB.addNewEmployeeToDB("Mark", "M", 5000000.0, LocalDate.now(), 1, "Sales");
		boolean checkIfSynced = employeePayrollServiceDB.checkForDBSync("Mark");
		Assert.assertTrue(checkIfSynced);
	}

	@Ignore
	@Test
	public void addedNewEmployee_WhenRetrieved_ShouldReturnPayrollDetailsAndBeSyncedWithDB() throws DBServiceException {
		employeePayrollServiceDB.showEmployeeAndPayrollDetailsByName("Mark");
		boolean checkIfSynced = employeePayrollServiceDB.checkForDBSync("Mark");
		Assert.assertTrue(checkIfSynced);
	}

	@Ignore
	@Test
	public void givenEmployeeId_WhenDeleted_ShouldSyncWithDB() throws DBServiceException {
		employeePayrollServiceDB.removeExistingEmployeeFromDB(3);
		Assert.assertEquals(3, employeePayrollList.size());

	}

	@Ignore
	@Test
	public void givenEmployeeData_ShouldPrint_ExecutionTimeToConsole() throws DBServiceException {
		EmployeePayrollData[] employeesArray = { new EmployeePayrollData("Jeff Bezos", "M", 100000.0, LocalDate.now()),
				new EmployeePayrollData("Bill Gates", "M", 200000.0, LocalDate.now()),
				new EmployeePayrollData("Mark Zuckerberg", "M", 300000.0, LocalDate.now()),
				new EmployeePayrollData("Sundar", "M", 600000.0, LocalDate.now()),
				new EmployeePayrollData("Mukesh", "M", 500000.0, LocalDate.now()),
				new EmployeePayrollData("Anil", "M", 300000.0, LocalDate.now()) };
		Instant begin = Instant.now();
		employeePayrollServiceDB.addEmployeeToPayroll(Arrays.asList(employeesArray));
		Instant finish = Instant.now();
		System.out.println("Execution time without using threads : " + java.time.Duration.between(begin, finish));
	}
	
	
	@Test
	public void givenEmployeeData_ShouldPrint_ExecutionTimeToConsoleUsingThreads() throws DBServiceException {
		EmployeePayrollData[] employeesArray = {
				new EmployeePayrollData("Jeff Bezos","M", 100000.0, LocalDate.now()),
				new EmployeePayrollData("Bill Gates","M", 200000.0, LocalDate.now()),
				new EmployeePayrollData("Mark Zuckerberg","M", 300000.0, LocalDate.now()),
				new EmployeePayrollData("Sundar","M", 600000.0, LocalDate.now()),
				new EmployeePayrollData("Mukesh","M", 500000.0, LocalDate.now()),
				new EmployeePayrollData("Anil","M", 300000.0, LocalDate.now())
		};
		Instant begin = Instant.now();
		employeePayrollServiceDB.addEmployeeToPayrollUsingThreads(Arrays.asList(employeesArray));
		Instant finish = Instant.now();
		System.out.println("Execution time using threads : "+java.time.Duration.between(begin, finish));
	}
	
	@Test
	public void givenEmployeeData_ShouldPrint_ExecutionTimeToConsoleWithAndWithoutThreads() throws DBServiceException {
		EmployeePayrollData[] employeesArray = {
				new EmployeePayrollData("Jeff Bezos","M", 100000.0, LocalDate.now()),
				new EmployeePayrollData("Bill Gates","M", 200000.0, LocalDate.now()),
				new EmployeePayrollData("Mark Zuckerberg","M", 300000.0, LocalDate.now()),
				new EmployeePayrollData("Sundar","M", 600000.0, LocalDate.now()),
				new EmployeePayrollData("Mukesh","M", 500000.0, LocalDate.now()),
				new EmployeePayrollData("Anil","M", 300000.0, LocalDate.now())
		};
		Instant begin1 = Instant.now();
		employeePayrollServiceDB.addEmployeeToPayroll(Arrays.asList(employeesArray));
		Instant finish1 = Instant.now();
		System.out.println("Execution time without using threads : " + java.time.Duration.between(begin1, finish1));
		Instant begin = Instant.now();
		employeePayrollServiceDB.addEmployeeToPayrollUsingThreads(Arrays.asList(employeesArray));
		Instant finish = Instant.now();
		System.out.println("Execution time using threads : "+java.time.Duration.between(begin, finish));
	}
	
	@Test
	public void givenEmployeePayrollData_ShouldPrintInstanceTime_ToConsoleUsingThreads() throws DBServiceException {
		EmployeePayrollData[] employeesArray = {
				new EmployeePayrollData("Jeff Bezos","M", 100000.0, LocalDate.now(),501,"CoreTeam"),
				new EmployeePayrollData("Bill Gates","M", 200000.0, LocalDate.now(),502,"Marketing"),
				new EmployeePayrollData("Mark Zuckerberg","M", 300000.0, LocalDate.now(),503,"Tehnology"),
				new EmployeePayrollData("Sundar","M", 600000.0, LocalDate.now(),504,"Management"),
				new EmployeePayrollData("Mukesh","M", 500000.0, LocalDate.now(),505,"Sales"),
				new EmployeePayrollData("Anil","M", 300000.0, LocalDate.now(),506,"Advertising")
		};
		Instant begin = Instant.now();
		employeePayrollServiceDB.insertEmployeeToPayrollDetailsUsingThreads(Arrays.asList(employeesArray));
		Instant finish = Instant.now();
		System.out.println("Duration Without Thread: "+java.time.Duration.between(begin, finish));
	}
	
	@Test
	public void givenEmployeeSalary_WhenUpdated_ShouldSyncWithDB() {
		EmployeePayrollData[] employeesArray = {
				new EmployeePayrollData("Jeff Bezos", 100000.0),
				new EmployeePayrollData("Bill Gates", 200000.0),
				new EmployeePayrollData("Mark Zuckerberg", 300000.0),
				new EmployeePayrollData("Sundar", 400000.0),
				new EmployeePayrollData("Mukesh", 500000.0),
				new EmployeePayrollData("Anil", 600000.0)
		};
		Instant begin = Instant.now();
		employeePayrollServiceDB.updateEmployeeSalaryUsingThreads(Arrays.asList(employeesArray));
		Instant finish = Instant.now();
		System.out.println("Execution time using threads :  "+java.time.Duration.between(begin, finish));
	}
}
