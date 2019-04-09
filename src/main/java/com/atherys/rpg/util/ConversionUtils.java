package com.atherys.rpg.util;

public final class ConversionUtils {

    public static Integer valueOf(String string, Integer defaultValue) {
        try {
            return Integer.valueOf(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Double valueOf(String string, Double defaultValue) {
        try {
            return Double.valueOf(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Float valueOf(String string, Float defaultValue) {
        try {
            return Float.valueOf(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Long valueOf(String string, Long defaultValue) {
        try {
            return Long.valueOf(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Byte valueOf(String string, Byte defaultValue) {
        try {
            return Byte.valueOf(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Short valueOf(String string, Short defaultValue) {
        try {
            return Short.valueOf(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
