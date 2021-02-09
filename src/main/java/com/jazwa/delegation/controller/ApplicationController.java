package com.jazwa.delegation.controller;

import com.jazwa.delegation.dto.ApplicationAddNewDto;
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
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
    ResponseEntity<Set<Application>> getAllApplications(@RequestParam(required = false) Integer empId,
                                                        @AuthenticationPrincipal(expression = "employee") Employee loggedInEmployee) {

        Optional<Employee> employeeOptional;

        Department department = departmentService.getByEmployee(loggedInEmployee).orElseGet(Department::new);
        switch (loggedInEmployee.getRole()) {
            case ROLE_ADMIN:
                employeeOptional = employeeService.getById(empId);
                break;

            case ROLE_HEAD:
                if (department.getEmployees().stream().anyMatch(employee -> empId == employee.getId())) {
                    employeeOptional = employeeService.getById(empId);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
                break;

            default:
                    employeeOptional = employeeService.getById(loggedInEmployee.getId());
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

    @PostMapping
    //@Secured("ROLE_EMPLOYEE")
    ResponseEntity<Application> apply(@RequestBody ApplicationAddNewDto applicationDto,
                                      @AuthenticationPrincipal(expression = "employee") Employee loggedInEmployee){

        Application application = new Application(applicationDto);
        application.setEmployee(loggedInEmployee);
        return ResponseEntity.of(applicationService.sendApplication(application));
    }

    //TODO add approve function (approved application becomes delegation)
    //TODO add deny(reject) function
    //TODO add comments (date, who is commenting)
}
