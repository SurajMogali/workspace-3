package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MYDEPT")
public class Dept {

	@Id
	@Column(name= "DNO")
	private Integer deptNo;
	@Column(name = "DNAME")
	private String deptName;
	private String manager;

	public Dept() {
		super();
	}

	public Dept(Integer deptNo, String deptName, String manager) {
		super();
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.manager = manager;
	}

	public Integer getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(Integer deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

}
