package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.demo.spring.entity.Emp;


public interface EmpRepository extends JpaRepository<Emp, Integer> {

	@Query("UPDATE Emp e set e.salary=:salary where empID=:empid")
	@Modifying
	@Transactional
	public int updateSalary(int empid, double salary);

}