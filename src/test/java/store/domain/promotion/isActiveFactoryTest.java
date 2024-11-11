package store.domain.promotion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static store.common.constant.ErrorMessages.*;

class ActiveTypeCheckerTest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final LocalDateTime NOW = LocalDateTime.of(2024, 1, 1, 12, 0);

    @Test
    @DisplayName("시작일과 종료일이 null이면 예외가 발생한다")
    void throwExceptionWhenDateIsNull() {
        IllegalArgumentException startDateException = assertThrows(IllegalArgumentException.class,
            () -> ActiveTypeChecker.of(null, "2024-01-01", NOW));
        assertEquals(NULL_OR_EMPTY_ERROR.toString(), startDateException.getMessage());

        IllegalArgumentException endDateException = assertThrows(IllegalArgumentException.class,
            () -> ActiveTypeChecker.of("2024-01-01", null, NOW));
        assertEquals(NULL_OR_EMPTY_ERROR.toString(), endDateException.getMessage());
    }

    @Test
    @DisplayName("날짜 형식이 맞지 않으면 예외가 발생한다")
    void throwExceptionWhenInvalidDateFormat() {
        IllegalArgumentException startDateFormatException = assertThrows(IllegalArgumentException.class,
            () -> ActiveTypeChecker.of("2024/01/01", "2024-01-01", NOW));
        assertEquals(PARSING_DATE_ERROR.toString(), startDateFormatException.getMessage());

        IllegalArgumentException endDateFormatException = assertThrows(IllegalArgumentException.class,
            () -> ActiveTypeChecker.of("2024-01-01", "2024.01.01", NOW));
        assertEquals(PARSING_DATE_ERROR.toString(), endDateFormatException.getMessage());
    }

    @Test
    @DisplayName("현재 날짜가 시작일과 종료일 사이에 있으면 활성화 상태이다")
    void returnTrueWhenCurrentDateIsBetweenStartAndEndDate() {
        ActiveType ActiveTypeCheckerTest = ActiveTypeChecker.of(
            NOW.minusDays(1).format(DATE_FORMATTER),
            NOW.plusDays(1).format(DATE_FORMATTER), NOW
        );

        assertEquals(ActiveTypeCheckerTest, ActiveType.ON);
    }

    @Test
    @DisplayName("현재 날짜가 시작일 이전이면 비활성화 상태이다")
    void returnFalseWhenCurrentDateIsBeforeStartDate() {
        ActiveType ActiveTypeCheckerTest = ActiveTypeChecker.of(
            NOW.plusDays(1).format(DATE_FORMATTER),
            NOW.plusDays(2).format(DATE_FORMATTER), NOW
        );

        assertEquals(ActiveTypeCheckerTest, ActiveType.OFF);
    }

    @Test
    @DisplayName("현재 날짜가 종료일 이후면 비활성화 상태이다")
    void returnFalseWhenCurrentDateIsAfterEndDate() {
        ActiveType ActiveTypeCheckerTest = ActiveTypeChecker.of(
            NOW.minusDays(2).format(DATE_FORMATTER),
            NOW.minusDays(1).format(DATE_FORMATTER), NOW
        );

        assertEquals(ActiveTypeCheckerTest, ActiveType.OFF);
    }

    @Test
    @DisplayName("현재 날짜가 시작일과 같으면 활성화 상태이다")
    void returnFalseWhenCurrentDateEqualsStartDate() {
        ActiveType ActiveTypeCheckerTest = ActiveTypeChecker.of(
            NOW.format(DATE_FORMATTER),
            NOW.plusDays(1).format(DATE_FORMATTER), NOW
        );

        assertEquals(ActiveTypeCheckerTest, ActiveType.ON);
    }

    @Test
    @DisplayName("현재 날짜가 종료일과 같으면 활성화 상태이다")
    void returnTrueWhenCurrentDateEqualsEndDate() {
        ActiveType ActiveTypeCheckerTest = ActiveTypeChecker.of(
            NOW.minusDays(1).format(DATE_FORMATTER),
            NOW.format(DATE_FORMATTER), NOW
        );

        assertEquals(ActiveTypeCheckerTest, ActiveType.ON);
    }
}