package ru.repp.den.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PROCESSING)
public class StillProcessingException extends RuntimeException {

    public StillProcessingException(String message) {
        super(message);
    }
}
