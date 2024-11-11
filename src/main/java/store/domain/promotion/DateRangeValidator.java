package store.domain.promotion;

import static store.common.constant.ErrorMessages.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import store.domain.promotion.type.ActiveType;

public final class DateRangeValidator {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final String DATE_PATTERN = "\\d{4}-\\d{2}-\\d{2}";

    private DateRangeValidator() {

    }

    public static ActiveType of(String startDate, String endDate, LocalDateTime now) {
        validateNotNull(startDate, endDate);
        validateDateFormat(startDate, endDate);

        LocalDateTime start = parseDateTime(startDate);
        LocalDateTime end = parseDateTime(endDate).with(LocalTime.MAX);

        return isDateInRange(now, start, end);
    }

    private static void validateNotNull(String startDate, String endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException(NULL_OR_EMPTY_ERROR.toString());
        }
    }

    private static void validateDateFormat(String startDate, String endDate) {
        if (!startDate.matches(DATE_PATTERN) || !endDate.matches(DATE_PATTERN)) {
            throw new IllegalArgumentException(PARSING_DATE_ERROR.toString());
        }
    }

    private static LocalDateTime parseDateTime(String date) {
        try {
            return LocalDate.parse(date, DATE_FORMATTER).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(PARSING_DATE_ERROR.toString());
        }
    }

    private static ActiveType isDateInRange(LocalDateTime now, LocalDateTime start, LocalDateTime end) {
        if (now.isAfter(start) && now.isBefore(end)) {
            return ActiveType.ON;
        }
        return ActiveType.OFF;
    }
}
