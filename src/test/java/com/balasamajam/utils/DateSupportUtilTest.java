package com.balasamajam.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

public class DateSupportUtilTest
{
    @Test
    public void getEndDateTest()
    {
        Date date = DateSupportUtil.getEndDate(LocalDate.now());
        System.out.println(date);
    }
}
