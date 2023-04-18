package com.stockify.stockifyapp.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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

    public static UUID convertStringToUuid(String s) {
        // String s2 = s.replace("-", "");
        // UUID uuid = new UUID(
        //         new BigInteger(s2.substring(0, 16), 16).longValue(),
        //         new BigInteger(s2.substring(16), 16).longValue());
        UUID uuid = UUID.fromString(s);
        return uuid;
    }
}