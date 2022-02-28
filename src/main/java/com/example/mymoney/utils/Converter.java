package com.example.mymoney.utils;

public class Converter {
    public static float percentInInt(String percent) {
        return percent == null ? Float.MIN_VALUE : Float.valueOf(percent.trim().replaceAll("%", ""));
    }
}
