package org.exercise.travellers.utils;

public class SqlInjectionRegex {

    private SqlInjectionRegex() {
    }

    private static final String SQL_REGEX = "[;\"'()*=]|(\\b(ALTER|CREATE|DELETE|DROP|INSERT|SELECT|UPDATE)\\b)";

    public static String getRegex() {
        return SQL_REGEX;
    }

}
