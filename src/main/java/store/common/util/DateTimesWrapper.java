package store.common.util;

import java.time.LocalDateTime;

import camp.nextstep.edu.missionutils.DateTimes;

public final class DateTimesWrapper {
    private DateTimesWrapper() {
    }

    public static LocalDateTime now() {
        return DateTimes.now();
    }
}
