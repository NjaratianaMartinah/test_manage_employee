package com.adm.test.dao;

import com.adm.test.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> findEmployeeEntityByFullName(String fullName);
}
