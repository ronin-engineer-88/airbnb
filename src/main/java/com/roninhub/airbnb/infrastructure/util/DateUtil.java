package com.roninhub.airbnb.infrastructure.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    protected static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseISO(String date) {
        try {
            return ISO_DATE_FORMAT.parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}
