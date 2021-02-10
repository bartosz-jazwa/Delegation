package com.jazwa.delegation.model.document;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jazwa.delegation.dto.ApplicationAddNewDto;
import com.jazwa.delegation.model.Employee;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Transactional
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long number;
    private LocalDate applicationDate;
    @ManyToOne
    @JsonManagedReference
    private Employee employee;
    private String project;
    private Locale country;
    private String city;
    private String transport;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Float advanceAmount;
    private ApplicationStatus status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "application")
    @JsonManagedReference
    private List<PlanItem> plan;

    public Application() {

    }

    public Application(ApplicationAddNewDto addNewDto){
        this.applicationDate = LocalDate.now();
        this.project = addNewDto.getProject();
        this.country = addNewDto.getCountry();
        this.city = addNewDto.getCity();
        this.transport = addNewDto.getTransport();
        this.startDate = addNewDto.getStartDate();
        this.finishDate = addNewDto.getFinishDate();
        this.advanceAmount = addNewDto.getAdvanceAmount();
        this.status = ApplicationStatus.PENDING;
        this.plan = initPlan(this.startDate,this.finishDate);
    }

    public List<PlanItem> initPlan(LocalDate start, LocalDate finish){
        List<PlanItem> planList = new ArrayList<>();

        LocalDate startDate = start;
        for (; startDate.isBefore(finish); startDate = startDate.plusDays(1)) {
            planList.add(new PlanItem(startDate,startDate.getDayOfWeek().toString(), this));

        }

        return planList;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Locale getCountry() {
        return country;
    }

    public void setCountry(Locale country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public Float getAdvanceAmount() {
        return advanceAmount;
    }

    public void setAdvanceAmount(Float advanceAmount) {
        this.advanceAmount = advanceAmount;
    }

    public List<PlanItem> getPlan() {
        return plan;
    }

    public void setPlan(List<PlanItem> plan) {
        this.plan = plan;
    }
}
