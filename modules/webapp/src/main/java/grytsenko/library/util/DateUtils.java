package grytsenko.library.util;

import java.util.Date;

import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 * Utilities for dates.
 */
public final class DateUtils {

    /**
     * Returns the current date and time.
     */
    public static Date now() {
        return new Date();
    }

    /**
     * Computes the number of days after the given day to now.
     */
    public static int daysAfter(Date when) {
        LocalDate before = LocalDate.fromDateFields(when);
        LocalDate now = new LocalDate();

        if (before.isAfter(now)) {
            throw new IllegalArgumentException("Date is in future.");
        }

        return Days.daysBetween(before, now).getDays();
    }

    private DateUtils() {
    }

}
