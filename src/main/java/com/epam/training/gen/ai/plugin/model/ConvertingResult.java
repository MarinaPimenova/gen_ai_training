package com.epam.training.gen.ai.plugin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConvertingResult {
    private String fromCurrency;
    private String toCurrency;
    private Double exchangeRate;
    private Double exchangeResult;
    private String result;
}
