package com.jazwa.delegation.model.document;

import com.jazwa.delegation.model.Employee;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long number;
    private LocalDate applicationDate;
    @ManyToOne
    private Employee employee;
    private String project;
    private Locale country;
    private String city;
    private String transport;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Float advanceAmount;
    private ApplicationStatus status;
    @OneToMany(mappedBy = "application")
    private List<PlanItem> plan;

    public Application() {
        this.applicationDate = LocalDate.now();
        this.project = "jakis projekt";
        this.country = Locale.CANADA;
        this.city = "Krakow";
        this.transport = "samolot";
        this.startDate = LocalDate.now().plusDays(1);
        this.finishDate = this.startDate.plusDays(2);
        this.advanceAmount = 56.56f;
        this.status = ApplicationStatus.PENDING;
        this.plan = new ArrayList<>();
    }

    public List<PlanItem> initPlan(LocalDate start, LocalDate finish){
        List<PlanItem> planList = new ArrayList<>();

        for (LocalDate i = start; i.isBefore(finish); i.plusDays(1)) {
            planList.add(new PlanItem(i,""));
        }
        return planList;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
