package com.jazwa.delegation.model.document;

import com.jazwa.delegation.model.Employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Application {
    private long number;
    private LocalDate applicationDate;
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
        List<PlanItem> list = new ArrayList<>();

        for (LocalDate i = start; i.isBefore(finish); i.plusDays(1)) {
            list.add(new PlanItem(i,""));
        }
        return list;
    }
}
