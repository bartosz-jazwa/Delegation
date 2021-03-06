package com.jazwa.delegation.service;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DepartmentService {
    List<Department> getAllDepartments();
    Optional<Department> getById(Integer id);
    Optional<Department> getByEmployee(Employee employee);
    Optional<Department> addNew(Department department);
    Optional<Department> deleteById(Integer id);
}
