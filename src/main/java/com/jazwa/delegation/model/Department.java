package com.jazwa.delegation.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Department {
    @Id
    private int id;
    @NotNull
    private String name;
    @OneToOne(mappedBy = "department")
    private HeadOfDep head;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "department")
    private Set<Employee> employees = new HashSet<>();

    public Department() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Employee getHead() {
        return head;
    }

    public void setHead(HeadOfDep head) {
        this.head = head;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}