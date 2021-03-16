package com.jazwa.delegation.service.CurrencyExchange;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Currency;

@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    @Override
    public Double calculate(Double amount, Currency currencyFrom, Currency currencyTo) {
        return null;
    }

    @Override
    public Double getRate(Currency currency, LocalDate exchangeDate) {

        StringBuilder urlBuilder = new StringBuilder("http://api.nbp.pl/api/exchangerates/rates/c/")
                .append(currency.toString())
                .append("/")
                .append(exchangeDate.toString())
                .append("/?format=xml");

        URL exchangeTable;
        try {
            exchangeTable = new URL(urlBuilder.toString());
        } catch (MalformedURLException e) {
            return -1.0d;
        }
        ObjectMapper objectMapper =  new XmlMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        ExchangeRate exchangeRate = null;
        try {
            exchangeRate = objectMapper.readValue(exchangeTable, ExchangeRate.class);
        } catch (IOException e) {
            return -1.0d;
        }
        return exchangeRate.rates.get(0).getRate().doubleValue();
    }
}
