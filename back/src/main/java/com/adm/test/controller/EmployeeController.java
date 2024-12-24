package com.adm.test.controller;

import com.adm.test.dto.request.EmployeeDto;
import com.adm.test.dto.response.ApiResponse;
import com.adm.test.entity.Employee;
import com.adm.test.service.EmployeeService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.adm.test.dto.response.ApiResponse.buildResponse;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final static Logger log = LogManager.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> find(@RequestParam(value = "name", required = false) String name,
                                            @PageableDefault(sort = "fullName", direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("Fetching employees with name: {} and pageable: {}", name, pageable);
        Page<Employee> employees = employeeService.findAll(name, pageable);
        log.info("Fetched {} employees", employees.getTotalElements());
        return buildResponse(employees, OK, "Employees fetched");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable int id) {
        log.info("Fetching employee with ID: {}", id);
        ResponseEntity<ApiResponse> response = buildResponse(employeeService.findById(id), OK, "Employee fetched");
        log.info("Employee with ID: {} fetched successfully", id);
        return response;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody EmployeeDto employee) {
        log.info("Creating a new employee: {}", employee);
        ResponseEntity<ApiResponse> response = buildResponse(employeeService.saveEmployee(employee), CREATED, "Employee created");
        log.info("Employee created successfully with ID: {}", response.getBody().getData());
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable int id,
                                              @Valid @RequestBody EmployeeDto employee) {
        log.info("Updating employee with ID: {}", id);
        employeeService.updateEmployee(id, employee);
        log.info("Employee with ID: {} updated successfully", id);
        return buildResponse(null, OK, "Employee updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int id) {
        log.info("Deleting employee with ID: {}", id);
        employeeService.delete(id);
        log.info("Employee with ID: {} deleted successfully", id);
        return buildResponse(null, OK, "Employee deleted");
    }
}
