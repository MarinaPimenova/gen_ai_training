package com.epam.training.gen.ai.plugin.model;

import java.util.List;

public class CurrencyList {
    private static final List<String> currencies = List.of("Belarusian Ruble",
            "Euro",
            "USD",
            "Japanese Yen",
            "Swiss Franc",
            "Swedish Krona");

    public static List<String> getCurrenciesList() {
        return currencies;
    }
}
