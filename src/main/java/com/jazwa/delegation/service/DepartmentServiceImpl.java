package com.jazwa.delegation.service;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.repository.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepo.findAll();
    }

    @Override
    public Optional<Department> getById(Integer id) {
        return departmentRepo.findById(id);
    }

    @Override
    public Optional<Department> addNew(Department department) {
        return Optional.empty();
    }

    @Override
    public Optional<Department> deleteById(Integer id) {
        return Optional.empty();
    }
}
