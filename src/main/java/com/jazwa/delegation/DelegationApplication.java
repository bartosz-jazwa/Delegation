package com.jazwa.delegation;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.Role;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.repository.DepartmentRepo;
import com.jazwa.delegation.repository.EmployeeRepo;
import com.jazwa.delegation.repository.document.ApplicationRepo;
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

    @Autowired
    ApplicationRepo applicationRepo;

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

        Application application1 = new Application();
        Application application2 = new Application();
        Set<Application> applicationsAdam= new HashSet<>();
        applicationsAdam.add(application1);
        applicationsAdam.add(application2);

        Employee adam = new Employee();
        adam.setLogin("adam");
        adam.setPassword(encoder.encode("123"));
        adam.setRole(Role.ROLE_ADMIN);
        adam.setDepartment(pss);
        adam.setApplications(applicationsAdam);
        employeeRepo.save(adam);

        Application application3 = new Application();
        applicationRepo.save(application3);
        Application application4 = new Application();
        Set<Application> applicationsJan= new HashSet<>();
        applicationsJan.add(application3);
        applicationsJan.add(application4);

        Employee jan = new Employee();
        jan.setLogin("jan");
        jan.setPassword(encoder.encode("qwe"));
        jan.setRole(Role.ROLE_HEAD);
        jan.setDepartment(pss);
        adam.setApplications(applicationsJan);
        employeeRepo.save(jan);

        Set<Employee> employees = new HashSet<>();
        employees.add(adam);
        employees.add(jan);


    }
}
