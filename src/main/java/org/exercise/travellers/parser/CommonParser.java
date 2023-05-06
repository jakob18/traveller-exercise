package org.exercise.travellers.parser;

import org.exercise.travellers.utils.SqlInjectionRegex;

public abstract class CommonParser {

    protected CommonParser() {
    }

    protected static String sqlInjectionPrevention(String value) {
        if (null != value) {
            return value.replaceAll(SqlInjectionRegex.getRegex(), "");
        }
        return null;
    }
}
