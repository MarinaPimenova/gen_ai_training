package com.epam.training.gen.ai.plugin.service;

import com.epam.training.gen.ai.plugin.model.ConvertingResult;
import com.epam.training.gen.ai.plugin.model.CurrencyList;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyConverterService {
    private static final Map<String, Double> EXCHANGE_RATES = new HashMap<>();

    static {
        // Dummy exchange rates (1 USD to others)
        EXCHANGE_RATES.put("USD", 1.14);
        EXCHANGE_RATES.put("EUR", 1.0);
        EXCHANGE_RATES.put("Belarusian Ruble", 3.71);
        EXCHANGE_RATES.put("Japanese Yen", 163.02);
        EXCHANGE_RATES.put("Swiss Franc", 0.93);
        EXCHANGE_RATES.put("Swedish Krona", 11.10);
    }

    public CurrencyList getCurrenciesList() {
        return CurrencyList.builder().build();
    }

    public ConvertingResult convertCurrency(
            String fromCurrency,
            String toCurrency,
            double amount
    ) {
        double fromRate = EXCHANGE_RATES.getOrDefault(fromCurrency.toUpperCase(), 1.0);
        double toRate = EXCHANGE_RATES.getOrDefault(toCurrency.toUpperCase(), 1.0);

        double exchangeRate = (1 / fromRate) * toRate;
        double converted = (amount / fromRate) * toRate;
        String result = String.format("%.2f %s is %.2f %s", amount, fromCurrency.toUpperCase(), converted, toCurrency.toUpperCase());
        return ConvertingResult.builder()
                .exchangeRate(exchangeRate)
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .exchangeResult(converted)
                .result(result)
                .build();
    }
}
