package ru.cft.focusstart.suhodolov.exceptions;

/**
 * Исключение, которое будет выбрасываться, когда введеный размер таблицы не будет входить в диапазон значений
 */
public class MismatchSizeException extends Exception {

    public MismatchSizeException(String message, int num) {
        super(message + num);
    }
}
