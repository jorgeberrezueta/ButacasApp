package me.jorgeb.exception;

public class InvalidInputDateException extends InvalidInputException {

    public InvalidInputDateException(String date) {
        super("La fecha ingresada no es válida: " + date);
    }

    public InvalidInputDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
