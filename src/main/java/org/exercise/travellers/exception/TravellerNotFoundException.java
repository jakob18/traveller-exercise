package org.exercise.travellers.exception;

import java.io.Serial;

public class TravellerNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -819020707811886329L;

    public TravellerNotFoundException(String message) {
        super(message);
    }
}
