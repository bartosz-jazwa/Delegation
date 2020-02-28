package com.jazwa.delegation;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.Role;
import com.jazwa.delegation.repository.DepartmentRepo;
import com.jazwa.delegation.repository.EmployeeRepo;
import com.jazwa.delegation.repository.document.ApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        departmentRepo.save(pss);

        Department pks = new Department();
        pks.setId(2);
        pks.setName("pks");
        departmentRepo.save(pks);

        Employee adam = new Employee("adam",encoder.encode("123"));
        adam.setRole(Role.ROLE_ADMIN);
        adam.setDepartment(pss);

        employeeRepo.save(adam);

        Employee jan = new Employee("jan",encoder.encode("qwe"));
        jan.setRole(Role.ROLE_HEAD);
        jan.setDepartment(pss);
        employeeRepo.save(jan);

        Employee bartek = new Employee("bartek",encoder.encode("jaz"));
        bartek.setRole(Role.ROLE_EMPLOYEE);
        bartek.setDepartment(pks);
        employeeRepo.save(bartek);

    }
}
