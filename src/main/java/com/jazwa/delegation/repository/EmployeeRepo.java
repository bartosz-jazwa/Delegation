package com.jazwa.delegation.repository;

import com.jazwa.delegation.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
    Set<Employee>findAllByLastName(String lastName);
    Set<Employee>findAllByPositionLike(String position);
}
