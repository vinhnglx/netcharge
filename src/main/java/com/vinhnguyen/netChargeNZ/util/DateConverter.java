package com.vinhnguyen.netChargeNZ.util;

import com.cobber.fta.dates.DateTimeParser;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

@Component
public class DateConverter {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");


    public String convertDateTimeToString(LocalDateTime datetime) {
        return datetime != null ? datetime.format(FORMATTER) : null;
    }

    public LocalDateTime convertStringToDateTime(String datetime) {
        final DateTimeParser dtp = new DateTimeParser()
                .withDateResolutionMode(DateTimeParser.DateResolutionMode.MonthFirst)
                .withLocale(Locale.ENGLISH);

        String stringPattern = dtp.determineFormatString(datetime);

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern(stringPattern)
                .toFormatter();

        try {
            String trimmedDatetime = datetime.trim();
            TemporalAccessor parsed = formatter.parseBest(trimmedDatetime, LocalDateTime::from, LocalDate::from);

            if (parsed instanceof LocalDateTime) {
                return (LocalDateTime) parsed;
            } else if (parsed instanceof LocalDate localDate) {
                return localDate.atStartOfDay();
            }
            throw new Exception();
        } catch (Exception e) {
            throw new IllegalArgumentException("Unsupported or invalid date/time format: " + datetime);
        }
    }
}
