package org.exercise.travellers.exception;

import java.io.Serial;

public class InvalidEmailException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -819020704711886329L;

    public InvalidEmailException(String message) {
        super(message);
    }
}
