package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MYEMP")
public class Employee {
	@Id
	@Column(name = "EMPNO")
	private Integer employeeId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ADDRESS")
	private String city;

	@Column(name = "SALARY")
	private Double salary;

	@Column(name = "DNO")
	private Integer deptNo;

	public Employee() {

	}

	public Employee(Integer employeeId, String name, String city, Double salary, Integer deptNo) {
		this.employeeId = employeeId;
		this.name = name;
		this.city = city;
		this.salary = salary;
		this.deptNo = deptNo;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Integer getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(Integer deptNo) {
		this.deptNo = deptNo;
	}

}