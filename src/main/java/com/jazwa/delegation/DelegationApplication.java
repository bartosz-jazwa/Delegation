package com.jazwa.delegation;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.Role;
import com.jazwa.delegation.repository.DepartmentRepo;
import com.jazwa.delegation.repository.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DelegationApplication implements CommandLineRunner {

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    DepartmentRepo departmentRepo;

    public static void main(String[] args) {
        SpringApplication.run(DelegationApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        Department pss = new Department();
        pss.setId(1);
        pss.setName("pss");
        //pss.setEmployees(employees);
        departmentRepo.save(pss);

        Employee adam = new Employee();
        adam.setLogin("adam");
        adam.setPassword(encoder.encode("123"));
        adam.setRole(Role.ROLE_ADMIN);
        adam.setDepartment(pss);
        employeeRepo.save(adam);

        Employee jan = new Employee();
        jan.setLogin("jan");
        jan.setPassword(encoder.encode("qwe"));
        jan.setRole(Role.ROLE_HEAD);
        jan.setDepartment(pss);
        employeeRepo.save(jan);

        Set<Employee> employees = new HashSet<>();
        employees.add(adam);
        employees.add(jan);


    }
}
