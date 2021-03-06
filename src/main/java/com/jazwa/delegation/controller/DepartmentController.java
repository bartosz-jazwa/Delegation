package com.jazwa.delegation.controller;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.model.document.ApplicationStatus;
import com.jazwa.delegation.service.DepartmentService;
import com.jazwa.delegation.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.jazwa.delegation.model.Role.ROLE_ADMIN;
import static com.jazwa.delegation.model.Role.ROLE_HEAD;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;

    @Secured("ROLE_ADMIN")
    @GetMapping
    ResponseEntity<List<Department>> getAll() {
        List<Department> resultSet = departmentService.getAllDepartments();
        if (resultSet.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    ResponseEntity<Department> getOne(@PathVariable Integer id,
                                      @AuthenticationPrincipal(expression = "employee") Employee e) {

        if (e.getRole() == ROLE_ADMIN) {
            return ResponseEntity.of(departmentService.getById(id));
        } else {
            int departmentId = e.getDepartment().getId();
            return ResponseEntity.of(departmentService.getById(departmentId));
        }
    }

    @GetMapping("/{id}/employees")
    @Secured("ROLE_HEAD")
    ResponseEntity<List<Employee>> getEmployees(@PathVariable Integer id,
                                                @AuthenticationPrincipal(expression = "employee") Employee e) {

        Optional<Department> departmentOptional = departmentService.getById(id);
        Department department = departmentOptional.orElseThrow(EntityNotFoundException::new);
        if (e.getDepartment().getId()==department.getId()) {
            List<Employee> employeeList = employeeService.getByDepartment(department);
            if (employeeList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(employeeList);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/{id}/pendingApplications")
    @Secured("ROLE_HEAD")
    ResponseEntity<List<Application>> getPendingApplications(@PathVariable Integer id,
                                                             @AuthenticationPrincipal(expression = "employee") Employee e) {

        Optional<Department> departmentOptional = departmentService.getById(id);
        Department department = departmentOptional.orElseThrow(EntityNotFoundException::new);

        if (e.getDepartment().getId()==department.getId()) {
            List<Application> applicationList = department.getEmployees().stream()
                    .flatMap(employee -> employee.getApplications().stream())
                    .filter(application -> application.getStatus().equals(ApplicationStatus.PENDING))
                    .collect(Collectors.toList());
            if (applicationList.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(applicationList);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
