package com.capg.jdbc.employeepayroll;

import java.time.LocalDate;

public class EmployeePayrollData {
	private int id;
	private String name;
	private double salary;
	private LocalDate start_date;

	public EmployeePayrollData(int id, String name, double salary, LocalDate start_date) {
		super();
		this.id = id;
		this.name = name;
		this.salary = salary;
		this.start_date = start_date;
	}

	public int getID() {
		return id;
	}

	public void setID(int iD) {
		id = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	@Override
	public String toString() {
		return "EmployeePayrollData [ID=" + id + ", name=" + name + ", salary=" + salary + ", start_date=" + start_date
				+ "]";
	}

}
