package com.stockify.stockifyapp.commons;

public abstract class Checker {
    public static boolean isNumber(Object value) {
        return value instanceof Number;
    }
}
