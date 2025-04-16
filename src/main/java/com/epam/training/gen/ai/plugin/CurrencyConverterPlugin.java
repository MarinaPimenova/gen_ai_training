package com.epam.training.gen.ai.plugin;

import com.epam.training.gen.ai.plugin.model.ConvertingResult;

import com.epam.training.gen.ai.plugin.service.CurrencyConverterService;
import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyConverterPlugin {
    private final CurrencyConverterService currencyConverterService;

    @DefineKernelFunction(name = "get_currency_list", description = "Get currencies list", returnType = "java.util.List")
    public List<String> getCurrenciesList() {
        return currencyConverterService.getCurrenciesList();
    }

    @DefineKernelFunction(
            name = "convert_currency",
            description = "Convert currency",
            returnDescription = "Returns exchange rate",
            returnType = "com.epam.training.gen.ai.plugin.model.ConvertingResult")
    public ConvertingResult convertCurrency(
            @KernelFunctionParameter(name = "fromCurrency", description = "From currency", type = String.class, required = true, defaultValue = "EUR")
                    String fromCurrency,
            @KernelFunctionParameter(name = "toCurrency", description = "To currency", type = String.class)
                    String toCurrency,
            @KernelFunctionParameter(name = "amount", description = "Amount to be converted ", type = Double.class, defaultValue = "1")
                    Double amount
    ) {
        return currencyConverterService.convertCurrency(fromCurrency, toCurrency, amount);
    }
}
