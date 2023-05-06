package org.exercise.travellers.parser;

import org.exercise.travellers.utils.SqlInjectionRegex;

public abstract class CommonParser {

    protected CommonParser() {
    }

    protected static String sqlInjectionPrevention(String value) {
        return value.replaceAll(SqlInjectionRegex.getRegex(), "");
    }
}
