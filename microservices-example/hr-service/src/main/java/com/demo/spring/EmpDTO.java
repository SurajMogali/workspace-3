package com.demo.spring;

public class EmpDTO {

	private Integer employeeId;

	private String name;

	private String city;

	private Double salary;

	private Integer deptNo;

	public EmpDTO() {

	}

	public EmpDTO(Integer employeeId, String name, String city, Double salary, Integer deptNo) {
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