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
    private List<PlanItem> plan;

    public List<PlanItem> initPlan(LocalDate start, LocalDate finish){
        List<PlanItem> planList = new ArrayList<>();

        for (LocalDate i = start; i.isBefore(finish); i.plusDays(1)) {
            planList.add(new PlanItem(i,""));
        }
        return planList;
    }
}
