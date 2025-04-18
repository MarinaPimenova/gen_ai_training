package com.epam.training.gen.ai.chat.plugin;

import com.epam.training.gen.ai.chat.util.LocaleParser;
import com.microsoft.semantickernel.semanticfunctions.annotations.DefineKernelFunction;
import com.microsoft.semantickernel.semanticfunctions.annotations.KernelFunctionParameter;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class TimePlugin {

    public static final String DAY_MONTH_DAY_YEAR = "EEEE, MMMM d, yyyy";

    public ZonedDateTime now() {
        return ZonedDateTime.now(ZoneId.systemDefault());
    }

    @DefineKernelFunction(name = "date", description = "Get the current date")
    public String date(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        // Example: Sunday, 12 January, 2025
        return DateTimeFormatter.ofPattern(DAY_MONTH_DAY_YEAR)
                .withLocale(parseLocale(locale))
                .format(now());
    }

    /**
     * Get the current time.
     *
     * <p>Example: {{time.time}} => 9:15:00 AM
     *
     * @return The current time.
     */
    @DefineKernelFunction(name = "time", description = "Get the current time")
    public String time(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        // Example: 09:15:07 PM
        return DateTimeFormatter.ofPattern("hh:mm:ss a")
                .withLocale(parseLocale(locale))
                .format(now());
    }

    @DefineKernelFunction(name = "utcNow", description = "Get the current UTC date and time")
    public String utcNow(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        return DateTimeFormatter.ofPattern(DAY_MONTH_DAY_YEAR + " h:mm a")
                .withLocale(parseLocale(locale))
                .format(now().withZoneSameInstant(ZoneOffset.UTC));
    }

    @DefineKernelFunction(name = "today", description = "Get the current date")
    public String today(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        return date(locale);
    }

    @DefineKernelFunction(name = "now", description = "Get the current date and time in the local time zone")
    public String now(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        return DateTimeFormatter.ofPattern(DAY_MONTH_DAY_YEAR + " h:mm a")
                .withLocale(parseLocale(locale))
                .format(now());
    }

    @DefineKernelFunction(name = "year", description = "Get the current year")
    public String year(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        return DateTimeFormatter.ofPattern("yyyy").withLocale(parseLocale(locale)).format(now());
    }

    @DefineKernelFunction(name = "month", description = "Get the current month name")
    public String month(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        return DateTimeFormatter.ofPattern("MMMM").withLocale(parseLocale(locale)).format(now());
    }

    @DefineKernelFunction(name = "monthNumber", description = "Get the current month number")
    public String monthNumber(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        return DateTimeFormatter.ofPattern("MM").withLocale(parseLocale(locale)).format(now());
    }

    @DefineKernelFunction(name = "day", description = "Get the current day of the month")
    public String day(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        return DateTimeFormatter.ofPattern("d").withLocale(parseLocale(locale)).format(now());
    }

    @DefineKernelFunction(name = "dayOfWeek", description = "Get the current day of the week")
    public String dayOfWeek(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        return DateTimeFormatter.ofPattern("EEEE").withLocale(parseLocale(locale)).format(now());
    }

    @DefineKernelFunction(name = "hour", description = "Get the current clock hour")
    public String hour(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        return DateTimeFormatter.ofPattern("h a").withLocale(parseLocale(locale)).format(now());
    }

    @DefineKernelFunction(name = "hourNumber", description = "Get the current clock 24-hour number")
    public String hourNumber(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        return DateTimeFormatter.ofPattern("HH").withLocale(parseLocale(locale)).format(now());
    }

    @DefineKernelFunction(name = "daysAgo", description = "Get the date of offset from today by a provided number of days")
    public String daysAgo(
            @KernelFunctionParameter(name = "days", description = "Number of days to offset from today.") String days,
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        int offsetDays = Integer.parseInt(days);
        return DateTimeFormatter.ofPattern(DAY_MONTH_DAY_YEAR)
                .withLocale(parseLocale(locale))
                .format(now().minusDays(offsetDays));
    }

    @DefineKernelFunction(name = "dateMatchingLastDayName", description = "Get the date of the last day matching the supplied week day name")
    public String dateMatchingLastDayName(
            @KernelFunctionParameter(name = "dayName", description = "Week name day.") String dayName,
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        ZonedDateTime currentDate = now();
        for (int i = 1; i <= 7; i++) {
            currentDate = currentDate.minusDays(1);
            String currentDayName = currentDate.getDayOfWeek().getDisplayName(TextStyle.FULL,
                    parseLocale(locale));

            if (currentDayName.equalsIgnoreCase(dayName)) {
                return DateTimeFormatter.ofPattern(DAY_MONTH_DAY_YEAR)
                        .withLocale(parseLocale(locale))
                        .format(currentDate);
            }
        }
        throw new IllegalArgumentException("dayName is not recognized");
    }

    @DefineKernelFunction(name = "minute", description = "Get the minutes on the current hour")
    public String minute(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        return DateTimeFormatter.ofPattern("mm").withLocale(parseLocale(locale)).format(now());
    }

    /**
     * Get the seconds on the current minute.
     */
    @DefineKernelFunction(name = "second", description = "Get the seconds on the current minute")
    public String second(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        return DateTimeFormatter.ofPattern("ss").withLocale(parseLocale(locale)).format(now());
    }

    /**
     * Get the local time zone offset from UTC.
     *
     * <p>Example: {{time.timeZoneOffset}} => +03:00
     *
     * @return The local time zone offset from UTC.
     */
    @DefineKernelFunction(name = "timeZoneOffset", description = "Get the local time zone offset from UTC")
    public String timeZoneOffset(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        return DateTimeFormatter.ofPattern("XXX").withLocale(parseLocale(locale)).format(now());
    }

    /**
     * Get the local time zone name.
     *
     * <p>Example: {{time.timeZoneName}} => Pacific Time
     *
     * @return The local time zone name.
     */
    @DefineKernelFunction(name = "timeZoneName", description = "Get the local time zone name")
    public String timeZoneName(
            @KernelFunctionParameter(name = "locale", description = "Locale to use when formatting the date", required = false) String locale) {
        ZoneId zoneId = ZoneId.systemDefault();
        return zoneId.getDisplayName(TextStyle.FULL, parseLocale(locale));
    }

    /**
     * Parse the locale string into a Locale object.
     *
     * <p>By default, it parses using the LocaleParser utility class.
     *
     * @param locale string
     * @return a locale object
     */
    protected Locale parseLocale(String locale) {
        return LocaleParser.parseLocale(locale);
    }
}
