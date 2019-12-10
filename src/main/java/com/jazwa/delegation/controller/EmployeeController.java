package com.jazwa.delegation.controller;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.service.DepartmentService;
import com.jazwa.delegation.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
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
                                         @AuthenticationPrincipal(expression = "employee") Employee e){

        Optional<Employee> resultOptional = employeeService.getById(id);
        Optional<Department> departmentOptional = departmentService.getByEmployee(e);

        switch (e.getRole()){
            case ROLE_ADMIN:
                return ResponseEntity.of(resultOptional);
            case ROLE_HEAD:
                if (resultOptional.isPresent()&&departmentOptional.isPresent()){
                    if (resultOptional.get().getDepartment().getHead().equals(departmentOptional.get().getHead())){
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
                                     @AuthenticationPrincipal(expression = "employee")Employee e){

        Optional<Employee> resultEmployee;
        Optional<Employee> resultHead;
        Employee emp;
        if (e.getRole()== ROLE_ADMIN) {
            resultEmployee = employeeService.getById(id);
        } else {
            resultEmployee = employeeService.getById(e.getId());
        }
        try {
            resultHead = resultEmployee.orElseThrow(EntityNotFoundException::new).getDepartment().getEmployees().stream()
                    .filter(employee -> employee.getRole().equals(ROLE_HEAD))
                    .findFirst();
        } catch (Exception ex){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.of(resultHead);
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employee){
        Employee newEmployee = employee;
        try {
            newEmployee.setPassword(passwordEncoder.encode(employee.getPassword()));
        }catch (NullPointerException e){
            return ResponseEntity.unprocessableEntity().body(employee);
        }
        Optional<Employee> result = employeeService.addNew(newEmployee);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    ResponseEntity<Employee> deleteEmployee(@PathVariable Integer id){
        Integer delId;
        try{
            delId = id;
        }catch (NumberFormatException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.of(employeeService.deleteById(delId));
    }
}
