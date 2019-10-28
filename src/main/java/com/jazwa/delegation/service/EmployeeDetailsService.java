package com.jazwa.delegation.service;

import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.service.EmployeeDetails;
import com.jazwa.delegation.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class EmployeeDetailsService implements UserDetailsService {

    @Autowired
    EmployeeService employeeService;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Employee employee = employeeService.getByLogin(login)
                    .orElseThrow(() -> new UsernameNotFoundException(login));

        return new EmployeeDetails(employee);
    }
}
