package com.jazwa.delegation.model;

import javax.persistence.*;

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
    private Long cardNumber;
    @ManyToOne
    private Department department;


    public Employee() {
    }
}