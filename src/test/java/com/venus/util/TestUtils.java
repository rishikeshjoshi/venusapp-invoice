package com.venus.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by hrishikeshjoshi on 6/17/16.
 */
public class TestUtils {

    public static Date getDateWithoutTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        Date dateWithoutTime = calendar.getTime();
        return dateWithoutTime;
    }
}
