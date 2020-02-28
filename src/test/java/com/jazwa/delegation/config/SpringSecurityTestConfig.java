package com.jazwa.delegation.config;

import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.Role;
import com.jazwa.delegation.service.EmployeeDetails;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@TestConfiguration
public class SpringSecurityTestConfig  {

    @Bean
    @Primary
    public UserDetailsService employeeDetailsService(){
        Employee employee = new Employee("emloyee","employee");
        employee.setRole(Role.ROLE_EMPLOYEE);

        EmployeeDetails employeeDetails = new EmployeeDetails(employee);

        Employee head = new Employee("head","head");
        head.setRole(Role.ROLE_HEAD);

        EmployeeDetails headDetails = new EmployeeDetails(head);

        Employee admin = new Employee("admin","admin");
        admin.setRole(Role.ROLE_ADMIN);

        EmployeeDetails adminDetails = new EmployeeDetails(admin);

        return new InMemoryUserDetailsManager(Arrays.asList(adminDetails,headDetails,employeeDetails));
    }
}
