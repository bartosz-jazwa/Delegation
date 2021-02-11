package com.jazwa.delegation.model.document;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jazwa.delegation.model.Employee;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Locale;
import java.util.spi.LocaleNameProvider;

@Entity
public class Delegation {
    @Id
    private long number;
    @ManyToOne
    @JsonManagedReference
    private Employee employee;
    private String  project;
    private Locale country;
    private String city;
//    private TravelTo travelTo;
//    private TravelBack travelBack;
    private Float advanceAmount;
    private Currency advanceCurrency;
    private Float sumToPay;
    private DelegationStatus status;
    private LocalDate submitDate;
//    private List<Bill> bills;


    public Delegation() {
    }

    public Delegation(Application application) {
        this.number = application.getNumber();
        this.employee = application.getEmployee();
        this.project = application.getProject();
        this.country = application.getCountry();
        this.city = application.getCity();
        this.advanceAmount = application.getAdvanceAmount();
        this.advanceCurrency = Currency.getInstance(this.country);
        this.sumToPay = 200.0f;
        this.status = DelegationStatus.FILED;
        this.submitDate = LocalDate.now();
    }


    /*public Float calcDiet(Float rate){

        Duration duration = Duration.between(
                travelTo.getCrossBorderTime(),
                travelBack.getCrossBorderTime()
        );

        long fullDays = duration.toDays();
        long minutes = duration.toMinutes()%1440;
        float hours = minutes/60;

        Float diet;

        if (hours>=12){
            diet = rate;
        }else if (hours>=8){
            diet = rate/2;
        }else if(hours>0){
            diet = rate/3;
        }else {
            diet = 0f;
        }

        diet = diet+ fullDays*rate;
        return diet;
    }*/

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Locale getCountry() {
        return country;
    }

    public void setCountry(Locale country) {
        this.country = country;

    }

    /*public TravelTo getTravelTo() {
        return travelTo;
    }*/

    /*public void setTravelTo(TravelTo travelTo) {
        this.travelTo = travelTo;
    }*/

   /* public TravelBack getTravelBack() {
        return travelBack;
    }*/

    /*public void setTravelBack(TravelBack travelBack) {
        this.travelBack = travelBack;
    }*/

    public Float getAdvance() {
        return advanceAmount;
    }

    public void setAdvance(Float advance) {
        this.advanceAmount = advance;
    }

/*    public List<Bill> getBills() {
        return bills;
    }*/

/*    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }*/

    public LocalDate getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(LocalDate fileDate) {
        this.submitDate = fileDate;
    }

    public Currency getAdvanceCurrency() {
        return advanceCurrency;
    }

    public void setAdvanceCurrency(Currency advanceCurrency) {
        this.advanceCurrency = advanceCurrency;
    }
}
