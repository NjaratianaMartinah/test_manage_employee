package com.adm.test.service;

import com.adm.test.dao.EmployeeDao;
import com.adm.test.dto.request.EmployeeDto;
import com.adm.test.entity.Employee;
import com.adm.test.specification.EmployeeSpecification;
import com.adm.test.utility.exception.NotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;


@Service
public class EmployeeService {

    private final EmployeeDao employeeDao;

    private final static Logger log = LogManager.getLogger(EmployeeService.class);

    public EmployeeService(final EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public Employee findById(int employeeId) {
        log.info("Attempting to find employee with id: {}", employeeId);
        return employeeDao.findById(employeeId)
                .orElseThrow(() -> {
                    log.error("No employee found with id: {}", employeeId);
                    return new NotFoundException("No Employee found with id " + employeeId);
                });
    }

    public Optional<Employee> findByFullName(String employeeFullName) {
        log.info("Searching for employee with full name: {}", employeeFullName);
        return employeeDao.findEmployeeEntityByFullName(employeeFullName);
    }

    Employee fromDto(EmployeeDto employeeDto) {
        log.info("Converting EmployeeDto to Employee: {}", employeeDto.getFullName());
        Employee employee = new Employee();
        employee.setFullName(employeeDto.getFullName());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        return employee;
    }

    public Employee saveEmployee(EmployeeDto employee) {
        log.info("Attempting to save employee with full name: {}", employee.getFullName());
        var optionalEmployee = findByFullName(employee.getFullName());

        if (optionalEmployee.isPresent()) {
            log.error("Employee already exists with full name: {}", employee.getFullName());
            throw new InvalidParameterException("Employee already exists");
        }

        Employee savedEmployee = employeeDao.save(fromDto(employee));
        log.info("Employee saved successfully with full name: {}", employee.getFullName());
        return savedEmployee;
    }

    public void updateEmployee(int employeeId, EmployeeDto employee) {
        log.info("Attempting to update employee with id: {}", employeeId);
        var employeeToBeUpdated = employeeDao.findById(employeeId)
                .orElseThrow(() -> {
                    log.error("Employee not found with id: {}", employeeId);
                    return new NotFoundException("" + employeeId);
                });

        employeeToBeUpdated.setFullName(employee.getFullName());
        employeeToBeUpdated.setDateOfBirth(employee.getDateOfBirth());
        employeeDao.save(employeeToBeUpdated);
        log.info("Employee updated successfully with id: {}", employeeId);
    }

    public Page<Employee> findAll(String name, Pageable pageable) {
        log.info("Fetching all employees with name filter: {}", name);
        Specification<Employee> specification = Specification.where(
                EmployeeSpecification.hasName(name)
        );

        Page<Employee> employees = employeeDao.findAll(specification, pageable);
        log.info("Fetched {} employees with name filter: {}", employees.getTotalElements(), name);
        return employees;
    }

    public void delete(int empId) {
        log.info("Attempting to delete employee with id: {}", empId);
        employeeDao.deleteById(empId);
        log.info("Employee deleted successfully with id: {}", empId);
    }
}
