package com.jazwa.delegation.controller;

import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.repository.EmployeeRepo;
import com.jazwa.delegation.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private EmployeeRepo employeeRepo;
    @WithMockUser(roles = {"ADMIN"})

    @Test
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
    public void getEmployee() {
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