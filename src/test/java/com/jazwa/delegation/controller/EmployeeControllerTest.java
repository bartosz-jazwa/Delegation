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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @WithMockUser(username = "bartek",password = "bartek",roles = {"ADMIN"})

    @Test
    public void getAll() throws Exception {
        Employee bartek = new Employee();
        //bartek.setLogin("bartek");
        //bartek.setPassword("bartek");
        List<Employee> employees = Arrays.asList(bartek);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mvc.perform(get("/employees"))

                .andExpect(status().isOk());
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