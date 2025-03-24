package com.epam.training.gen.ai.chat.util;

import java.util.Locale;

import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;

public class LocaleParser {
    private LocaleParser() {
    }

    @SuppressWarnings("StringSplitter")
    public static Locale parseLocale(String locale) {
        Locale parsedLocale;

        if (locale == null
                || "".equals(locale.trim())
                || KernelFunctionParameter.NO_DEFAULT_VALUE.equals(locale)) {
            return Locale.getDefault();
        } else if (locale.contains("-")) {
            parsedLocale = Locale.forLanguageTag(locale);
        } else if (locale.contains("_")) {
            String[] parts = locale.split("_");
            parsedLocale = new Locale(parts[0], parts[1]);
        } else {
            parsedLocale = new Locale(locale);
        }

        return parsedLocale;
    }
}
