package com.jazwa.delegation.service;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepo employeeRepo;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }

    @Override
    public List<Employee> getByLastName(String lastName) {
        return employeeRepo.findAllByLastName(lastName);
    }

    @Override
    public List<Employee> getByDepartment(Department department) {
        return employeeRepo.findAllByDepartment(department);
    }

    @Override
    public Optional<Employee> getById(Integer id) {
        return employeeRepo.findById(id);
    }

    @Override
    public Optional<Employee> addNew(Employee employee) {
        Optional<Employee> result;
        try {
            result = Optional.ofNullable(employeeRepo.save(employee));
        } catch (DataIntegrityViolationException e) {
            return Optional.empty();
        }
        return result;
    }

    @Override
    public Optional<Employee> deleteById(Integer id) {
        Optional<Employee> result = employeeRepo.findById(id);
        result.ifPresent(employee -> employeeRepo.delete(employee));
        return result;
    }

    @Override
    public Optional<Employee> getByCard(Long cardNumber) {
        return employeeRepo.findByCardNumber(cardNumber);
    }

    @Override
    public Optional<Employee> getByLogin(String login){
        return employeeRepo.findByUsername(login);
    }

    @Override
    public Optional<Employee> update(Employee employee) {
        Optional<Employee> result;
        try {
            result = Optional.ofNullable(employeeRepo.save(employee));
        } catch (DataIntegrityViolationException e) {
            return Optional.empty();
        }
        return result;
    }
}
