package com.jazwa.delegation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jazwa.delegation.config.SpringSecurityTestConfig;
import com.jazwa.delegation.dto.EmployeeAddNewDto;
import com.jazwa.delegation.model.Department;
import com.jazwa.delegation.model.Employee;
import com.jazwa.delegation.model.Role;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.repository.EmployeeRepo;
import com.jazwa.delegation.service.DepartmentService;
import com.jazwa.delegation.service.EmployeeDetails;
import com.jazwa.delegation.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.*;

import static org.assertj.core.internal.bytebuddy.implementation.FixedValue.value;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = {"HEAD"})
    public void loggedHeadOfDepartment_whenGetAll_returnUnauthorize() throws Exception {

        List<Employee> employees = new ArrayList<>();
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = {"EMPLOYEE"})
    public void loggedRegularEmployee_whenGetAll_returnUnauthorized() throws Exception {
        List<Employee> employees = new ArrayList<>();
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    //@WithMockUser(roles = {"ADMIN"})
    //@WithUserDetails("admin")
    public void loggedAdmin_whenGetEmployee_returnOk() throws Exception {

        Employee bartek = new Employee();
        bartek.setRole(Role.ROLE_EMPLOYEE);
        bartek.setPosition("programista");

        Employee admin = new Employee("admin","admin");
        admin.setRole(Role.ROLE_ADMIN);
        EmployeeDetails adminDetails = new EmployeeDetails(admin);

        Set<Employee> employees = new HashSet<>();
        Department so = new Department();
        so.setEmployees(employees);

        when(employeeService.getById(1)).thenReturn(Optional.of(bartek));
        when(departmentService.getByEmployee(admin)).thenReturn(Optional.of(so));

        mvc.perform(get("/employees/1").with(user(adminDetails)))
                .andExpect(jsonPath("$.position").value("programista"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void loggedHead_whenGetEmployee_returnOkWhenEmployeeFromSameDepartment() throws Exception {

        Employee bartek = new Employee("bartek","bartek");
        bartek.setRole(Role.ROLE_EMPLOYEE);

        Employee head = new Employee("head","head");
        head.setRole(Role.ROLE_HEAD);
        EmployeeDetails headDetails = new EmployeeDetails(head);

        Set<Employee > employees = new HashSet<>();
        employees.add(head);
        employees.add(bartek);
        Department so = new Department();
        so.setEmployees(employees);
        bartek.setDepartment(so);
        head.setDepartment(so);

        when(employeeService.getById(1)).thenReturn(Optional.of(bartek));
        when(departmentService.getByEmployee(head)).thenReturn(Optional.of(so));

        mvc.perform(get("/employees/1").with(user(headDetails)))
                .andExpect(status().isOk());
    }

    @Test
    public void loggedHead_whenGetEmployee_returnForbiddenWhenEmployeeFromDifferentDepartment() throws Exception {

        Employee bartek = new Employee("bartek","bartek");
        bartek.setId(1);
        bartek.setRole(Role.ROLE_EMPLOYEE);

        Employee barteksHead = new Employee("bHead","bHead");
        barteksHead.setRole(Role.ROLE_HEAD);
        barteksHead.setId(3);

        Employee head = new Employee("head","head");
        head.setId(2);
        head.setRole(Role.ROLE_HEAD);
        EmployeeDetails headDetails = new EmployeeDetails(head);

        Set<Employee> soEmployees = new HashSet<>();
        soEmployees.add(head);
        Set<Employee> mechEmployees = new HashSet<>();
        mechEmployees.add(bartek);
        mechEmployees.add(barteksHead);

        Department so = new Department();
        so.setId(1);
        so.setEmployees(soEmployees);
        head.setDepartment(so);
        Department mech = new Department();
        mech.setId(2);
        mech.setEmployees(mechEmployees);
        bartek.setDepartment(mech);
        barteksHead.setDepartment(mech);


        when(employeeService.getById(1)).thenReturn(Optional.of(bartek));
        when(departmentService.getByEmployee(head)).thenReturn(Optional.of(so));

        mvc.perform(get("/employees/1").with(user(headDetails)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void loggedEmployee_whenGetEmployee_returnOk() throws Exception {

        Employee bartek = new Employee("bartek","bartek");
        bartek.setPosition("programista");
        bartek.setId(1);
        bartek.setRole(Role.ROLE_EMPLOYEE);

        EmployeeDetails bartekDetails = new EmployeeDetails(bartek);

        Set<Employee> soEmployees = new HashSet<>();
        soEmployees.add(bartek);

        Department so = new Department();
        so.setId(1);
        so.setEmployees(soEmployees);

        when(employeeService.getById(1)).thenReturn(Optional.of(bartek));
       //when(departmentService.getByEmployee(bartek)).thenReturn(Optional.of(so));

        mvc.perform(get("/employees/1").with(user(bartekDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.position").value("programista"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

    }

    @Test
    public void loggedRegularEmployee_whenGetHead_returnOk() throws Exception{
        Department so = new Department();

        Employee bartek = new Employee("bartek","bartek");
        bartek.setPosition("programista");
        bartek.setId(1);
        bartek.setRole(Role.ROLE_EMPLOYEE);
        bartek.setDepartment(so);
        EmployeeDetails bartekDetails = new EmployeeDetails(bartek);

        Employee head = new Employee("head","head");
        head.setId(2);
        head.setRole(Role.ROLE_HEAD);
        head.setDepartment(so);

        Set<Employee> employees = new HashSet<>();
        employees.add(bartek);
        employees.add(head);
        so.setEmployees(employees);

        when(employeeService.getById(1)).thenReturn(Optional.of(bartek));

        mvc.perform(get("/employees/1/head").with(user(bartekDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("ROLE_HEAD"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void loggedRegularEmployee_whenGetHeadAndNoHeadInDepartment_returnNoContent() throws Exception{
        Department so = new Department();

        Employee bartek = new Employee("bartek","bartek");
        bartek.setPosition("programista");
        bartek.setId(1);
        bartek.setRole(Role.ROLE_EMPLOYEE);
        bartek.setDepartment(so);
        EmployeeDetails bartekDetails = new EmployeeDetails(bartek);

        Set<Employee> employees = new HashSet<>();
        employees.add(bartek);
        //employees.add(head);
        so.setEmployees(employees);

        when(employeeService.getById(1)).thenReturn(Optional.of(bartek));

        mvc.perform(get("/employees/1/head").with(user(bartekDetails)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void loggedAdmin_whenAddNewEmployee_returnNewEmployee() throws Exception{
        EmployeeAddNewDto newEmployeeDto = new EmployeeAddNewDto("b","j","p","j@r.pl",1,"ROLE_EMPLOYEE","bartek","123");

        Employee newEmployee = new Employee(newEmployeeDto);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestBody = objectWriter.writeValueAsString(newEmployeeDto);

        when(employeeService.addNew(any(Employee.class))).thenReturn(Optional.of(newEmployee));

        mvc.perform(post("/employees")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void loggedAdmin_whenAddNewEmployeeEmpty_returnUnprocessable() throws Exception{
        EmployeeAddNewDto newEmployeeDto = new EmployeeAddNewDto("b","j","p","j@r.pl",1,"ROLE_EMPLOYEE","bartek","123");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        String requestBody = objectWriter.writeValueAsString(newEmployeeDto);

        when(employeeService.addNew(any(Employee.class))).thenReturn(Optional.empty());

        mvc.perform(post("/employees")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnprocessableEntity());
    }
    //TODO when email validation ready, add tests

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void loggedAdmin_whenDeleteEmployee_returnOK() throws Exception{
        EmployeeAddNewDto deletedEmployeeDto = new EmployeeAddNewDto("b","j","p","j@r.pl",1,"ROLE_EMPLOYEE","bartek","123");
        Employee employeeToBeDeleted = new Employee(deletedEmployeeDto);
        when(employeeService.deleteById(1)).thenReturn(Optional.of(employeeToBeDeleted));

        mvc.perform(delete("/employees/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void loggedAdmin_whenDeleteEmployeeButNoIntAsPathVariable_returnBadRequest() throws Exception{
        EmployeeAddNewDto deletedEmployeeDto = new EmployeeAddNewDto("b","j","p","j@r.pl",1,"ROLE_EMPLOYEE","bartek","123");
        Employee employeeToBeDeleted = new Employee(deletedEmployeeDto);
        when(employeeService.deleteById(1)).thenReturn(Optional.of(employeeToBeDeleted));

        mvc.perform(delete("/employees/a"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void loggedAdmin_whenDeleteEmployeeReturnsEmpty_returnNotFound() throws Exception{
        EmployeeAddNewDto deletedEmployeeDto = new EmployeeAddNewDto("b","j","p","j@r.pl",1,"ROLE_EMPLOYEE","bartek","123");
        Employee employeeToBeDeleted = new Employee(deletedEmployeeDto);
        when(employeeService.deleteById(1)).thenReturn(Optional.empty());

        mvc.perform(delete("/employees/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getApplications() {
    }
}