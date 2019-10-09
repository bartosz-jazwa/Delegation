package com.jazwa.delegation.model.document;

import java.time.LocalDate;

public class PlanItem {
    private LocalDate date;
    private String description;

    public PlanItem(LocalDate date, String description) {
        this.date = date;
        this.description = description;
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
}
