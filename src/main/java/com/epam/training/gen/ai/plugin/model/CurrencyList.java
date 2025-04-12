package com.epam.training.gen.ai.plugin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyList {
    private List<String> currencies = List.of("Belarusian Ruble",
            "Euro",
            "USD",
            "Japanese Yen",
            "Swiss Franc",
            "Swedish Krona");


}
