package com.jazwa.delegation.dto;

import com.jazwa.delegation.model.document.PlanItem;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class ApplicationAddNewDto {
    private String project;
    private String country;
    private String city;
    private String transport;
    private LocalDate startDate;
    private LocalDate finishDate;
    private Float advanceAmount;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
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
}
