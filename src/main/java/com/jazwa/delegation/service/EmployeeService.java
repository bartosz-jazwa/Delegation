package com.jazwa.delegation.service;

import com.jazwa.delegation.dto.EmployeeDto;
import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    List<Employee> getByLastName(String lastName);
    List<Employee> getByDepartment(Department department);
    Optional<Employee> getById(Integer id);
    Optional<Employee> addNew(Employee employee);
    Optional<Employee> deleteById(Integer id);
    Optional<Employee> getByCard(Long cardNumber);
    Optional<Employee> getByLogin(String login);
}
