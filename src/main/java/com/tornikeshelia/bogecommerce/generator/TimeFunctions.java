package com.tornikeshelia.bogecommerce.generator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeFunctions {

    public static Date getStartOfDay(Date today) {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();

    }

    public static Date getEndOfDay(Date today) {

        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();

    }

}
