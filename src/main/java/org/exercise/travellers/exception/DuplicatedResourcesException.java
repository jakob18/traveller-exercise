package org.exercise.travellers.exception;

import java.io.Serial;

public class DuplicatedResourcesException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -619020717811886329L;

    public DuplicatedResourcesException(String message) {
        super(message);
    }
}
