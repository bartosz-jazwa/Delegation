package com.jazwa.delegation.controller;

import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.Role;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.model.document.ApplicationStatus;
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

import java.util.*;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
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
    @MockBean
    private EmployeeService employeeService;
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
                .andExpect(jsonPath("$.name").value("pss"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }
    @Test
    public void loggedHeadOfDepartment_givenNoEmployees_whenGetEmployees_returnNoContent() throws Exception{
        Employee head = new Employee("head","head");
        head.setRole(Role.ROLE_HEAD);

        Department pss = new Department();
        pss.setId(1);;
        pss.setName("pss");

        head.setDepartment(pss);
        EmployeeDetails headDetails = new EmployeeDetails(head);

        when(departmentService.getById(1)).thenReturn(Optional.of(pss));
        when(employeeService.getByDepartment(any(Department.class))).thenReturn(new ArrayList<Employee>());

        mvc.perform(get("/departments/1/employees").with(user(headDetails)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void loggedHeadOfDepartment_givenTwoEmployees_whenGetEmployees_returnOk() throws Exception{
        Employee head = new Employee("head","head");
        head.setRole(Role.ROLE_HEAD);

        Employee employee1 = new Employee("employee2","employee2");
        employee1.setRole(Role.ROLE_EMPLOYEE);
        Employee employee2 = new Employee("employee2","employee2");
        employee2.setRole(Role.ROLE_EMPLOYEE);

        Department pss = new Department();
        pss.setId(1);;
        pss.setName("pss");

        head.setDepartment(pss);
        employee1.setDepartment(pss);
        employee2.setDepartment(pss);

        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);

        EmployeeDetails headDetails = new EmployeeDetails(head);

        when(departmentService.getById(1)).thenReturn(Optional.of(pss));
        when(employeeService.getByDepartment(any(Department.class))).thenReturn(employees);

        mvc.perform(get("/departments/1/employees").with(user(headDetails)))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void loggedHeadOfDifferentDepartment_whenGetEmployees_returnUnauthorized() throws Exception{

        Department pss = new Department();
        pss.setId(1);;
        pss.setName("pss");

        Employee head = new Employee("head","head");
        head.setRole(Role.ROLE_HEAD);
        head.setDepartment(pss);

        Department pkm = new Department();
        pkm.setId(2);;
        pkm.setName("pkm");

        Employee diffHead = new Employee("diffHead","diffHead");
        diffHead.setRole(Role.ROLE_HEAD);
        diffHead.setDepartment(pkm);

        EmployeeDetails headDetails = new EmployeeDetails(diffHead);
        when(departmentService.getById(1)).thenReturn(Optional.of(pss));

        mvc.perform(get("/departments/1/employees").with(user(headDetails)))
                .andExpect(status().isUnauthorized());
    }
    @Test
    public void loggedHeadOfDepartment_givenTwoPendingApplications_whenGetPendingApplications_returnOk() throws Exception{
        Application app1 = new Application();
        app1.setStatus(ApplicationStatus.PENDING);
        Application app2 = new Application();
        app2.setStatus(ApplicationStatus.PENDING);
        Set<Application> applications = new HashSet<>();
        applications.add(app1);
        applications.add(app2);

        Department pss = new Department();
        pss.setId(1);;
        pss.setName("pss");

        Employee head = new Employee("head","head");
        head.setRole(Role.ROLE_HEAD);
        head.setApplications(applications);
        head.setDepartment(pss);

        Set<Employee> employees = new HashSet<>();
        employees.add(head);

        pss.setEmployees(employees);

        EmployeeDetails headDetails = new EmployeeDetails(head);

        when(departmentService.getById(1)).thenReturn(Optional.of(pss));

        mvc.perform(get("/departments/1/pendingApplications").with(user(headDetails)))
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void loggedHeadOfDifferentDepartment_whenGetPendingApplications_returnUnauthorized() throws Exception{
        Department pss = new Department();
        pss.setId(1);;
        pss.setName("pss");

        Employee head = new Employee("head","head");
        head.setRole(Role.ROLE_HEAD);
        head.setDepartment(pss);

        Department pkm = new Department();
        pkm.setId(2);;
        pkm.setName("pkm");

        Employee diffHead = new Employee("diffHead","diffHead");
        diffHead.setRole(Role.ROLE_HEAD);
        diffHead.setDepartment(pkm);

        EmployeeDetails headDetails = new EmployeeDetails(diffHead);
        when(departmentService.getById(1)).thenReturn(Optional.of(pss));

        mvc.perform(get("/departments/1/pendingApplications").with(user(headDetails)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void loggedHeadOfDepartment_givenNoPendingApplications_whenGetPendingApplications_returnNoContent() throws Exception{
        Application app1 = new Application();
        app1.setStatus(ApplicationStatus.PENDING);
        Application app2 = new Application();
        app2.setStatus(ApplicationStatus.PENDING);
        Set<Application> applications = new HashSet<>();
        //applications.add(app1);
        //applications.add(app2);

        Department pss = new Department();
        pss.setId(1);;
        pss.setName("pss");

        Employee head = new Employee("head","head");
        head.setRole(Role.ROLE_HEAD);
        head.setApplications(applications);
        head.setDepartment(pss);

        Set<Employee> employees = new HashSet<>();
        employees.add(head);

        pss.setEmployees(employees);

        EmployeeDetails headDetails = new EmployeeDetails(head);

        when(departmentService.getById(1)).thenReturn(Optional.of(pss));

        mvc.perform(get("/departments/1/pendingApplications").with(user(headDetails)))
                //.andExpect(jsonPath("$",hasSize(2)))
                .andExpect(status().isNoContent());
    }
}