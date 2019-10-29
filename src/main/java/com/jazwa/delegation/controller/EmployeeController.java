package com.jazwa.delegation.controller;

import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.jazwa.delegation.model.Role.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping
    @Secured("ROLE_ADMIN")
    ResponseEntity<List<Employee>> getAll() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        if (employeeList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/{id}")
    ResponseEntity<Employee> getEmployee(@PathVariable Integer id,
                                         @AuthenticationPrincipal(expression = "employee")Employee e){

        if (e.getRole()== ROLE_ADMIN) {
            return ResponseEntity.of(employeeService.getById(id));
        } else {
            return ResponseEntity.of(employeeService.getById(e.getId()));
        }
    }
}
