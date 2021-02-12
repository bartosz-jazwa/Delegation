package com.jazwa.delegation.controller;

import com.jazwa.delegation.dto.ApplicationAddNewDto;
import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.model.document.ApplicationStatus;
import com.jazwa.delegation.model.document.Delegation;
import com.jazwa.delegation.service.DepartmentService;
import com.jazwa.delegation.service.EmployeeService;
import com.jazwa.delegation.service.document.ApplicationService;
import com.jazwa.delegation.service.document.DelegationService;
import com.jazwa.delegation.service.document.PlanItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;
    @Autowired
    DelegationService delegationService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    PlanItemService planItemService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping
    ResponseEntity<Set<Application>> getAllApplications(@RequestParam(required = false) Integer requestEmployee,
                                                        @AuthenticationPrincipal(expression = "employee") Employee loggedInEmployee) {

        Optional<Employee> employeeOptional;

        Department department = departmentService.getByEmployee(loggedInEmployee).orElseGet(Department::new);
        switch (loggedInEmployee.getRole()) {
            case ROLE_ADMIN:
                employeeOptional = employeeService.getById(requestEmployee);
                break;

            case ROLE_HEAD:
                if (department.getEmployees().stream().anyMatch(employee -> requestEmployee == employee.getId())) {
                    employeeOptional = employeeService.getById(requestEmployee);
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

    @GetMapping("/{id}")
    ResponseEntity<Application> getOne(@PathVariable Long id){

        return ResponseEntity.of(applicationService.getById(id));
    }

    @PostMapping
    //@Secured("ROLE_EMPLOYEE")
    ResponseEntity<Application> apply(@RequestBody ApplicationAddNewDto applicationDto,
                                      @AuthenticationPrincipal(expression = "employee") Employee loggedInEmployee){

        Application application = new Application(applicationDto);
        application.setEmployee(loggedInEmployee);
        return ResponseEntity.of(applicationService.save(application));
    }

    // TODO approved application becomes delegation

    @PutMapping("/{id}")
    @Secured("ROLE_HEAD")
    ResponseEntity<Application> updateStatus(@PathVariable Long id,
                                                             @RequestParam(required = true) ApplicationStatus status,
                                                             @AuthenticationPrincipal(expression = "employee") Employee head){
        Optional<Application> applicationOptional = applicationService.getById(id);
        Employee applyingEmployee;
        Application application;
        if (applicationOptional.isPresent()){
            application = applicationOptional.get();
            applyingEmployee = application.getEmployee();

        }else {
            return ResponseEntity.notFound().build();
        }

        if (applyingEmployee.getDepartment().getId() == head.getDepartment().getId()){

            application.setStatus(status);

            switch (application.getStatus()){
                case PENDING:

                case APPROVED:
                    return ResponseEntity.of(applicationService.approveApplication(application));

                default:
                    return ResponseEntity.of(applicationService.save(application));
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    //TODO add forward method, head of department sends application to his head
    //TODO add cancel method (employee can cancel his own application)
    //TODO add comments (date, who is commenting)
}
