package com.jazwa.delegation;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.Role;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.model.document.ApplicationStatus;
import com.jazwa.delegation.repository.DepartmentRepo;
import com.jazwa.delegation.repository.EmployeeRepo;
import com.jazwa.delegation.repository.document.ApplicationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Locale;

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
        adam.setFirstName("adam");
        adam.setLastName("adam");
        adam.setEmail("adam@a.pl");
        employeeRepo.save(adam);

        Employee jan = new Employee("jan",encoder.encode("qwe"));
        jan.setRole(Role.ROLE_HEAD);
        jan.setDepartment(pss);
        jan.setFirstName("jan");
        jan.setLastName("jan");
        jan.setEmail("jan@a.pl");
        employeeRepo.save(jan);

        Employee bartek = new Employee("bartek",encoder.encode("jaz"));
        bartek.setRole(Role.ROLE_EMPLOYEE);
        bartek.setDepartment(pks);
        bartek.setFirstName("bartek");
        bartek.setLastName("bartek");
        bartek.setEmail("bartek@a.pl");
        employeeRepo.save(bartek);

        Application app1 = new Application();
        app1.setStatus(ApplicationStatus.PENDING);
        app1.setAdvanceAmount(500f);
        app1.setApplicationDate(LocalDate.now());
        app1.setCity("Rzeszów");
        app1.setCountry(Locale.CANADA);
        app1.setEmployee(adam);
        applicationRepo.save(app1);

        Application app2 = new Application();
        app2.setStatus(ApplicationStatus.PENDING);
        app2.setAdvanceAmount(500f);
        app2.setApplicationDate(LocalDate.now());
        app2.setCity("Rzeszów");
        app2.setCountry(Locale.CANADA);
        app2.setEmployee(jan);
        applicationRepo.save(app2);
    }
}
