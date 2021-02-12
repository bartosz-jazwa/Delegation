package com.jazwa.delegation.model.document;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Optional;

@Entity
public class Bill {
    @Id
    private String billNumber;
    private LocalDate date;
    private Currency currency;
    private String description;
    private Float value;
    private Integer cardNumber;
    private Payment payment;
    @ManyToOne
    @JsonManagedReference
    private Delegation delegation;

    public Bill() {
    }

    public Bill(String billNumber, LocalDate date, Currency currency, String description, Float value, Integer cardNumber, Payment payment) {
        this.billNumber = billNumber;
        this.date = date;
        this.currency = currency;
        this.description = description;
        this.value = value;
        this.cardNumber = payment.equals(Payment.CARD)?cardNumber:-1;
        this.payment = payment;
        //this.delegation = delegation;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String subject) {
        this.description = subject;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Integer getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Delegation getDelegation() {
        return delegation;
    }

    public void setDelegation(Delegation delegation) {
        this.delegation = delegation;
    }
}
