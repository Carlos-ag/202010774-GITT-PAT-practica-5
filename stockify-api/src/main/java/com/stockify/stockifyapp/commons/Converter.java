package com.stockify.stockifyapp.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Converter {

    public static Date convertObjectToDate(Object object) throws ParseException {
        String dateString = (String) object;
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        return date;
    }

    public static double convertObjectToDouble(Object object) {
        double value = 0;
        if (object instanceof Integer) {
            value = (Integer) object;
        } else if (object instanceof Double) {
            value = (Double) object;
        } else if (object instanceof Float) {
            value = (Float) object;
        } else if (object instanceof Long) {
            value = (Long) object;
        } else if (object instanceof Short) {
            value = (Short) object;
        } else if (object instanceof Byte) {
            value = (Byte) object;
        }
        return value;
    }
}