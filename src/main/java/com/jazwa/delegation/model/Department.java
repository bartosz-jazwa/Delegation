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
    private Employee head;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "employee")
    private Set<Employee> employees = new HashSet<>();

    public Department() {
    }
}