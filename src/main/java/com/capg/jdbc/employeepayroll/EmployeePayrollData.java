package com.capg.jdbc.employeepayroll;

import java.time.LocalDate;

public class EmployeePayrollData {
	private int id;
	private String name;
	private String gender;
	private double salary;
	private LocalDate start_date;
	
	public EmployeePayrollData(int id, String name, String gender, double salary, LocalDate start_date) {
		this(name,gender,salary,start_date);
		this.id = id;
	}
	
	public EmployeePayrollData(String name, String gender, double salary, LocalDate start_date) {
		super();
		this.name = name;
		this.gender = gender;
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
	

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "EmployeePayrollData [ID=" + id + ", name=" + name + ", salary=" + salary + ", start_date=" + start_date
				+ "]";
	}
	
	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null)
			return false;
		if (getClass() != object.getClass())
			return false;
		
		EmployeePayrollData other = (EmployeePayrollData) object;
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
		if (start_date == null) {
			if (other.start_date != null)
				return false;
		} else if (!start_date.equals(other.start_date))
			return false;
		return true;
	}


}
