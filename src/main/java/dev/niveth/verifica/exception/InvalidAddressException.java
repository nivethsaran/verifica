package dev.niveth.verifica.exception;

public class InvalidAddressException extends Exception{

    public InvalidAddressException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }

}
