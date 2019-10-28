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
    private Employee head;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "department")
    private Set<Employee> employees = new HashSet<>();

    public Department() {
    }
}