package org.exercise.travellers.exception;

import java.io.Serial;

public class TravellerDeactivatedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -419010704811846329L;

    public TravellerDeactivatedException(String message) {
        super(message);
    }
}
