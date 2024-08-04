package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Query("select e from Employee e where e.deptNo=:deptNo")
	public List<Employee> findAllByDeptNo(Integer deptNo);
	 
}