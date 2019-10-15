package com.jazwa.delegation.service;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;

import java.util.Optional;
import java.util.Set;

public interface EmployeeService {
    Set<Employee> getAllEmployees();
    Set<Employee> getByLastName(String lastName);
    Set<Employee> getByDepartment(Department department);
    Optional<Employee> getById(Integer id);
    Optional<Employee> addNew(Employee employee);
    Optional<Employee> deleteById(Integer id);
    Optional<Employee> getByCard(Long cardNumber);
    Optional<Employee> getByEmail(String email);
}
