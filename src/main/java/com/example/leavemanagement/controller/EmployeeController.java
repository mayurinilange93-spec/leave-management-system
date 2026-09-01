package com.example.leavemanagement.controller;

import com.example.leavemanagement.model.Employee;
import com.example.leavemanagement.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController = @Controller + @ResponseBody
// It means every method's return value is automatically converted to JSON.
@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Management", description = "APIs for managing employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // GET /api/employees - list all employees
    @GetMapping
    @Operation(summary = "Get all employees", description = "Retrieve a list of all employees in the system")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // GET /api/employees/1 - get one employee by id
    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID", description = "Retrieve a specific employee by their ID")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    // POST /api/employees - create a new employee
    @PostMapping
    @Operation(summary = "Create new employee", description = "Add a new employee to the system")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    // PUT /api/employees/1 - update an existing employee
    @PutMapping("/{id}")
    @Operation(summary = "Update employee", description = "Update an existing employee's information")
    public Employee updateEmployee(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    // DELETE /api/employees/1 - delete an employee
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee", description = "Remove an employee from the system")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "Employee with id " + id + " deleted successfully";
    }
}
