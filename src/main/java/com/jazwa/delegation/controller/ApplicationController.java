package com.jazwa.delegation.controller;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.service.DepartmentService;
import com.jazwa.delegation.service.EmployeeService;
import com.jazwa.delegation.service.document.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DepartmentService departmentService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    ResponseEntity<Set<Application>> getAllApplications(@RequestParam(required = true) Integer employee,
                                                        @AuthenticationPrincipal(expression = "employee") Employee e) {

        Optional<Employee> employeeOptional;
        Department department = departmentService.getByEmployee(e).orElseGet(Department::new);
        switch (e.getRole()) {
            case ROLE_ADMIN:
                employeeOptional = employeeService.getById(employee);
                break;

            case ROLE_HEAD:
                if (department.getEmployees().stream().anyMatch(emp -> employee == emp.getId())) {
                    employeeOptional = employeeService.getById(employee);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
                break;

            default:
                employeeOptional = employeeService.getById(e.getId());
        }

        Employee resultEmployee;
        try {
            resultEmployee = employeeOptional.orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }

        Set<Application> applications = applicationService.getByEmployee(resultEmployee);
        return ResponseEntity.ok(applications);
    }
}
