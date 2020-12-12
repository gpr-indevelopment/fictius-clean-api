package com.fictiusclean.api.vehicle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidVehicleCreationException extends RuntimeException{

    public InvalidVehicleCreationException() {
    }

    public InvalidVehicleCreationException(String message) {
        super(message);
    }

    public InvalidVehicleCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVehicleCreationException(Throwable cause) {
        super(cause);
    }

    public InvalidVehicleCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
