package com.jazwa.delegation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jazwa.delegation.dto.EmployeeAddNewDto;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.service.DepartmentService;
import com.jazwa.delegation.service.DepartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import javax.persistence.*;
import java.util.*;

@Entity
public class Employee{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String firstName;
    private String lastName;
    private String position;
    @Column(unique = true,nullable = false)
    private String username;
    private String password;
    @Column(unique = true,nullable = true)
    private String email;
    @Column(unique = true,nullable = true)
    private Long cardNumber;
    private Role role;
    @ManyToOne
    @JsonManagedReference
    private Department department;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "employee")
    private Set<Application> applications;

    public Employee(){
    }

    public Employee(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Employee(EmployeeAddNewDto employeeDto){
        this.firstName = employeeDto.getFirstName();
        this.lastName = employeeDto.getLastName();
        this.position = employeeDto.getPosition();
        this.username = employeeDto.getUsername();
        this.password = employeeDto.getPassword();
        this.email = employeeDto.getEmail();
        this.cardNumber = null;
        this.role = Role.valueOf(employeeDto.getRole());
        this.applications = new HashSet<Application>();
    }

    //@OneToMany(cascade = CascadeType.ALL,mappedBy = "employee")
    //private Set<Delegation> delegations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public void setApplications(Set<Application> applications) {
        this.applications = applications;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*  public Set<Delegation> getDelegations() {
        return delegations;
    }

    public void setDelegations(Set<Delegation> delegations) {
        this.delegations = delegations;
    }*/
}