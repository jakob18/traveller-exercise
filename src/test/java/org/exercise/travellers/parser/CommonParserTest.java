package org.exercise.travellers.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


class CommonParserTest {

    @ParameterizedTest
    @CsvSource({
            "abc, abc",
            "123, 123",
            "Bruno; DROP TABLE travellers, Bruno  TABLE travellers",
            "Jacob' or 1=1;, Jacob or 11",
            "null, null"
    })
    void sqlInjectionPrevention(String input, String expectedOutput) {
        String actualOutput = CommonParser.sqlInjectionPrevention(input);
        assertEquals(expectedOutput, actualOutput);
    }
}

