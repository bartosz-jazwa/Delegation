package com.jazwa.delegation.model;

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
    @ManyToOne
    private Department department;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "application")
    private Set<Application> applications;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "delegation")
    private Set<Delegation> delegations;

    public Employee() {
    }
}