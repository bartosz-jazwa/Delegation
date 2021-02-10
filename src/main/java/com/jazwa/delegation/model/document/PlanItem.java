package com.jazwa.delegation.model.document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class PlanItem {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private LocalDate date;
    private String description;
    @ManyToOne
    @JsonBackReference
    private Application application;

    public PlanItem(LocalDate date, String description) {
        this.date = date;
        this.description = description;
    }

    public PlanItem(LocalDate date, String description, Application application) {
        this.application = application;
        this.date = date;
        this.description = description;
    }

    public PlanItem(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
