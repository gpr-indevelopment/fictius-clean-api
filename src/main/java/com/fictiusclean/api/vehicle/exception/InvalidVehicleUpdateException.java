package com.fictiusclean.api.vehicle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidVehicleUpdateException extends RuntimeException{

    public InvalidVehicleUpdateException() {
    }

    public InvalidVehicleUpdateException(String message) {
        super(message);
    }

    public InvalidVehicleUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidVehicleUpdateException(Throwable cause) {
        super(cause);
    }

    public InvalidVehicleUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
