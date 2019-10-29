package com.jazwa.delegation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jazwa.delegation.model.document.Application;
import com.jazwa.delegation.model.document.Delegation;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String firstName;
    private String lastName;
    private String position;
    private String login;
    private String password;
    private String email;
    @Column(unique = true,nullable = true)
    private Long cardNumber;
    private Role role;
    @ManyToOne
    @JsonManagedReference
    private Department department;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "employee")
    private Set<Application> applications;
    //@OneToMany(cascade = CascadeType.ALL,mappedBy = "employee")
    //private Set<Delegation> delegations;

    public Employee() {
    }

    public int getId() {
        return id;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

  /*  public Set<Delegation> getDelegations() {
        return delegations;
    }

    public void setDelegations(Set<Delegation> delegations) {
        this.delegations = delegations;
    }*/
}