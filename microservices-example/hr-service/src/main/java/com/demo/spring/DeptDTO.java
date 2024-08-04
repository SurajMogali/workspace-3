package com.demo.spring;

import java.util.ArrayList;
import java.util.List;

public class DeptDTO {

	private Integer deptNo;
	private String deptName;
	private String manager;
	private List<EmpDTO> empList = new ArrayList<>();

	public DeptDTO() {

	}

	public DeptDTO(Integer deptNo, String deptName, String manager, List<EmpDTO> empList) {
		super();
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.manager = manager;
		this.empList = empList;
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

	public List<EmpDTO> getEmpList() {
		return empList;
	}

	public void setEmpList(List<EmpDTO> empList) {
		this.empList = empList;
	}

}
