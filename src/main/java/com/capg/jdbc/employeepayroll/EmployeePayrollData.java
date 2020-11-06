package com.capg.jdbc.employeepayroll;

import java.time.LocalDate;

public class EmployeePayrollData {
	private int id;
	private int company_id;
	private String name;
	private String gender;
	private String department;
	private double salary;
	private double basic_pay;
	private double deductions;
	private double taxablePay;
	private double tax;
	private double netPay;
	private LocalDate start;

	public EmployeePayrollData(int id, String name, String gender, double salary, LocalDate start) {
		this.name = name;
		this.gender = gender;
		this.salary = salary;
		this.start = start;
		this.id = id;
	}

	public EmployeePayrollData(String name, String gender, double salary, LocalDate start_date, int company_id) {
		super();
		this.name = name;
		this.gender = gender;
		this.salary = salary;
		this.start = start_date;
		this.company_id = company_id;
	}

	public EmployeePayrollData(int id, String name, String gender, double salary, LocalDate start, double basic_pay,
			double deductions, double taxable_pay, double tax, double net_pay) {
		this(id, name, gender, salary, start);
		this.basic_pay = basic_pay;
		this.deductions = deductions;
		this.taxablePay = taxable_pay;
		this.tax = tax;
		this.netPay = net_pay;

	}

	public EmployeePayrollData(String name, String gender, double salary, LocalDate start) {
		this.name = name;
		this.gender = gender;
		this.salary = salary;
		this.start = start;
	}

	public EmployeePayrollData(String name, String gender, double salary, LocalDate start, int company_id,
			String department) {
		this.name = name;
		this.gender = gender;
		this.salary = salary;
		this.start = start;
		this.company_id = company_id;
		this.department = department;
	}

	public EmployeePayrollData(String name, double salary) {
		this.name = name;
		this.salary = salary;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public LocalDate getStart_date() {
		return start;
	}

	public void setStart_date(LocalDate start_date) {
		this.start = start_date;
	}

	@Override
	public String toString() {
		return "EmployeePayrollData [emp_id=" + id + ", name=" + name + ", gender=" + gender + ", salary=" + salary
				+ ", start_date=" + start + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		EmployeePayrollData other = (EmployeePayrollData) obj;
		if (id != other.id)
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;

		if (Double.doubleToLongBits(salary) != Double.doubleToLongBits(other.salary))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		return true;
	}

}