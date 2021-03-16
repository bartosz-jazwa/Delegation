package com.jazwa.delegation.service.CurrencyExchange;

import java.time.LocalDate;
import java.util.Currency;

public interface CurrencyExchangeService {

    Double calculate(Double amount, Currency currencyFrom, Currency currencyTo);
    Double getRate(Currency currency, LocalDate exchangeDate);
}
