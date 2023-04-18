package com.stockify.stockifyapp.common;

public abstract class Checker {
    public static boolean isNumber(Object value) {
        return value instanceof Number;
    }
}
