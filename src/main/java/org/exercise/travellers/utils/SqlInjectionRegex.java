package org.exercise.travellers.utils;

public class SqlInjectionRegex {

    private SqlInjectionRegex() {
    }

    private static final String SQL_REGEX = "[;\"'()]";

    public static String getRegex() {
        return SQL_REGEX;
    }

}
