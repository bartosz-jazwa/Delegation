package com.jazwa.delegation.controller;

import com.jazwa.delegation.config.SpringSecurityTestConfig;
import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.Role;
import com.jazwa.delegation.repository.EmployeeRepo;
import com.jazwa.delegation.service.DepartmentService;
import com.jazwa.delegation.service.EmployeeDetails;
import com.jazwa.delegation.service.EmployeeService;
import com.sun.security.auth.UserPrincipal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.validator.ValidateWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.*;

import static org.assertj.core.internal.bytebuddy.implementation.FixedValue.value;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest//(classes = SpringSecurityTestConfig.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private DepartmentService departmentService;
    @MockBean
    private EmployeeRepo employeeRepo;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenEmployees_whenGetAll_returnOk() throws Exception {
        Employee bartek = new Employee();
        bartek.setFirstName("Bartek");
        bartek.setPosition("programista");
        Employee gienek = new Employee();
        gienek.setFirstName("Gienek");
        gienek.setPosition("elektryk");
        List<Employee> employees = Arrays.asList(bartek,gienek);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenNoEmployees_whenGetAll_returnEmpty() throws Exception {

        List<Employee> employees = new ArrayList<>();
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                //.andExpect(jsonPath("$",hasSize(2)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"HEAD"})
    public void loggedHeadOfDepartment_whenGetAll_returnUnauthorize() throws Exception {

        List<Employee> employees = new ArrayList<>();
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                //.andExpect(jsonPath("$",hasSize(2)))
                .andExpect(status().isForbidden());
    }

    @Test
    //@WithUserDetails(value = "admin",userDetailsServiceBeanName = "employeeDetailsService")
    @WithMockUser(roles = {"ADMIN"})
    public void loggedAdmin_whengetEmployee_returnOk() throws Exception {

        Employee bartek = new Employee();
        bartek.setFirstName("Bartek");
        bartek.setPosition("programista");

        Employee admin = new Employee();
        admin.setLogin("admin");
        admin.setPassword("admin");
        admin.setRole(Role.ROLE_ADMIN);

        Employee head = new Employee();
        head.setLogin("head");
        head.setPassword("head");
        head.setRole(Role.ROLE_HEAD);

        Set<Employee > employees = new HashSet<>();
        employees.add(head);
        Department so = new Department();
        so.setEmployees(employees);

        when(employeeService.getById(1)).thenReturn(Optional.of(bartek));
        when(departmentService.getByEmployee(admin)).thenReturn(Optional.of(so));

        mvc.perform(get("/employees/1"))
                //.andExpect(jsonPath("$.position").value("programista"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void getHead() {
    }

    @Test
    public void addNewEmployee() {
    }

    @Test
    public void deleteEmployee() {
    }
}