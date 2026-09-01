package com.example.leavemanagement.repository;

import com.example.leavemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// JpaRepository already gives us save(), findById(), findAll(), deleteById(), etc.
// We don't need to write any implementation - Spring Data JPA generates it for us.
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);
}
