package com.jazwa.delegation.controller;

import com.jazwa.delegation.dto.EmployeeAddNewDto;
import com.jazwa.delegation.dto.EmployeeChangePasswordDto;
import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.service.DepartmentService;
import com.jazwa.delegation.service.EmployeeDetails;
import com.jazwa.delegation.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static com.jazwa.delegation.model.Role.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    PasswordEncoder passwordEncoder;

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
                                         @AuthenticationPrincipal(expression = "employee") Employee e) {

        Optional<Employee> resultOptional = employeeService.getById(id);
        Optional<Department> departmentOptional = departmentService.getByEmployee(e);

        switch (e.getRole()) {
            case ROLE_ADMIN:
                return ResponseEntity.of(resultOptional);
            case ROLE_HEAD:
                if (resultOptional.isPresent() && departmentOptional.isPresent()) {
                    if (resultOptional.get().getDepartment().getHead().equals(departmentOptional.get().getHead())) {
                        return ResponseEntity.of(resultOptional);
                    }
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            default:
                return ResponseEntity.of(employeeService.getById(e.getId()));
        }
    }

    @GetMapping("/{id}/head")
    ResponseEntity<Employee> getHead(@PathVariable Integer id,
                                     @AuthenticationPrincipal(expression = "employee") Employee e) {

        Optional<Employee> resultEmployee;
        Optional<Employee> resultHead;
        if (e.getRole() == ROLE_ADMIN) {
            resultEmployee = employeeService.getById(id);
        } else {
            resultEmployee = employeeService.getById(e.getId());
        }
        try {
            resultHead = resultEmployee.orElseThrow(EntityNotFoundException::new).getDepartment().getEmployees().stream()
                    .filter(employee -> employee.getRole().equals(ROLE_HEAD))
                    .findFirst();
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.of(resultHead);
    }

    @GetMapping("/{id}/applications")
    ResponseEntity<Set<Application>> getApplications(@PathVariable Integer id,
                                                     @AuthenticationPrincipal(expression = "employee") Employee e) {

        Optional<Employee> employeeOptional;
        if (e.getRole() == ROLE_ADMIN) {
            employeeOptional = employeeService.getById(id);
        } else {
            employeeOptional = employeeService.getById(e.getId());
        }

        Set<Application> applicationSet;
        try {
            applicationSet = employeeOptional.orElseThrow(EntityNotFoundException::new).getApplications();
        } catch (EntityNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
        if (applicationSet.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(applicationSet);
    }

    //TODO validate email
    @PostMapping
    @Secured("ROLE_ADMIN")
    ResponseEntity<Employee> addNewEmployee(@RequestBody @Valid EmployeeAddNewDto employee) {
        Optional<Department> departmentOptional = departmentService.getById(employee.getDepartmentId());
        Employee newEmployee = new Employee(employee);
        newEmployee.setPassword(passwordEncoder.encode(employee.getPassword()));
        departmentOptional.ifPresent(newEmployee::setDepartment);

        Optional<Employee> result = employeeService.addNew(newEmployee);
        if (result.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result.get());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
            //return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    ResponseEntity<Employee> deleteEmployee(@PathVariable Integer id) {
        Integer delId;
        try {
            delId = id;
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.of(employeeService.deleteById(delId));
    }

    @PutMapping("/{id}")
    ResponseEntity<Employee> changePassword(@PathVariable Integer id,
                                            @AuthenticationPrincipal(expression = "employee") Employee e,
                                            @RequestBody @Valid EmployeeChangePasswordDto passwordDto) {

        Optional<Employee> resultOptional = employeeService.getById(id);
        Employee employeeToUpdate = resultOptional.orElseThrow(EntityNotFoundException::new);
        if (e.getId() == employeeToUpdate.getId()){
            if (passwordEncoder.matches(passwordDto.getOldPassword(),employeeToUpdate.getPassword())){
                if (passwordDto.getNewPassword().equals(passwordDto.getRepeatPassword())){
                    employeeToUpdate.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
                    return ResponseEntity.of(employeeService.update(employeeToUpdate));
                }else {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //TODO add promoteEmployee method to change role from employee to head
    //TODO add transferEmployee method to move employee to different department
    //TODO add reset password
}
