package com.balasamajam.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateSupportUtil
{

    public static LocalDate getStartDateLocalDate(LocalDate theDate)
    {
        int dayOfWeek = theDate.getDayOfWeek().getValue();
        return LocalDate.from(theDate.minusDays(dayOfWeek - 1).atStartOfDay(ZoneId.systemDefault()));
    }

    public static LocalDate getEndDateLocalDate(LocalDate theDate)
    {
        int dayOfWeek = theDate.getDayOfWeek().getValue();
        // 1 is added to get the next dat midnight time. Better solution to get a time 11 59.
        return LocalDate.from(theDate.plusDays((7 - dayOfWeek) + 1).atStartOfDay(ZoneId.systemDefault()));
    }

    public static Date getStartDate(LocalDate theDate)
    {
        int dayOfWeek = theDate.getDayOfWeek().getValue();
        return Date.from(theDate.minusDays(dayOfWeek - 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Date getEndDate(LocalDate theDate)
    {
        int dayOfWeek = theDate.getDayOfWeek().getValue();
        // 1 is added to get the next dat midnight time. Better solution to get a time 11 59.
        return Date.from(theDate.plusDays((7 - dayOfWeek) + 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
