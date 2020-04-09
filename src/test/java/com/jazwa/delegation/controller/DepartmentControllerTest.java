package com.jazwa.delegation.controller;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.Role;
import com.jazwa.delegation.service.DepartmentService;
import com.jazwa.delegation.service.EmployeeDetails;
import com.jazwa.delegation.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest//(classes = SpringSecurityTestConfig.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class DepartmentControllerTest {

    @MockBean
    private DepartmentService departmentService;
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenDepartments_whenGetAll_returnOk() throws Exception{
        Department pss = new Department();
        pss.setId(1);
        pss.setName("pss");

        Department pkm = new Department();
        pkm.setId(2);
        pkm.setName("pkm");

        List<Department> departments = Arrays.asList(pss,pkm);

        when(departmentService.getAllDepartments()).thenReturn(departments);

        mvc.perform(get("/departments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void givenNoDepartments_whenGetAll_returnEmpty() throws Exception{
        List<Department> departments = new ArrayList<>();

        when(departmentService.getAllDepartments()).thenReturn(departments);

        mvc.perform(get("/departments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"EMPLOYEE","HEAD"})
    public void loggedEmployeeOrHead_whenGetAll_returnForbidden() throws Exception{
        List<Department> departments = new ArrayList<>();

        when(departmentService.getAllDepartments()).thenReturn(departments);

        mvc.perform(get("/departments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void loggedAdmin_whenGetOne_returnOk() throws Exception{
        Employee admin = new Employee("admin","admin");
        admin.setRole(Role.ROLE_ADMIN);
        EmployeeDetails adminDetails = new EmployeeDetails(admin);

        Department pss = new Department();
        pss.setId(1);;
        pss.setName("pss");

        when(departmentService.getById(1)).thenReturn(Optional.of(pss));

        mvc.perform(get("/departments/1").with(user(adminDetails)))
                .andExpect(jsonPath("$.name").value("pss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void loggedEmployeeFromSameDepartment_whenGetOne_returnOk() throws Exception{
        Employee employee = new Employee("employee","employee");
        employee.setRole(Role.ROLE_EMPLOYEE);

        Department pss = new Department();
        pss.setId(1);;
        pss.setName("pss");

        employee.setDepartment(pss);
        EmployeeDetails employeeDetails = new EmployeeDetails(employee);

        when(departmentService.getById(1)).thenReturn(Optional.of(pss));

        mvc.perform(get("/departments/1").with(user(employeeDetails)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
    @Test
    public void getEmployees() {
    }

    @Test
    public void getPendingApplications() {
    }
}