package com.jazwa.delegation.service.CurrencyExchange;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.time.LocalDate;

public class Rate {
    @JacksonXmlProperty(localName = "EffectiveDate")
    String effectiveDate;
    @JacksonXmlProperty(localName = "Mid")
    String midRate;

    public Float getRate(){
        return Float.parseFloat(midRate);
    }
    public LocalDate getDate(){
        return LocalDate.parse(effectiveDate);
    }
}
