package com.jazwa.delegation.model.document;

import java.time.LocalDate;
import java.util.Currency;

public class Bill {
    private LocalDate date;
    private Currency currency;
    private String subject;
    private Float value;
    private boolean payment; //true - cash;

    public Bill(LocalDate date, Currency currency, String subject, Float value, boolean payment) {
        this.date = date;
        this.currency = currency;
        this.subject = subject;
        this.value = value;
        this.payment = payment;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
