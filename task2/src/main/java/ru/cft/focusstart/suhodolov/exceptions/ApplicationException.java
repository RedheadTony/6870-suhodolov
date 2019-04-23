package ru.cft.focusstart.suhodolov.exceptions;

/**
 * Исключение, которое будет обрабатываться в методе main
 */
public class ApplicationException extends Exception{

    public ApplicationException(String message) {
        super(message);
    }
}
