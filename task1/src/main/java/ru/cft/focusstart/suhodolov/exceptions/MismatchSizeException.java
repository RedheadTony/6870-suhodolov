package ru.cft.focusstart.suhodolov.exceptions;

/**
 * Исключение, которое будет выбрасываться, когда введеный размер таблицы не будет входить в диапазон значений
 */
public class MismatchSizeException extends Exception {

    private static final String ERROR_MESSAGE = "This value is incorrect: ";

    public MismatchSizeException(int num) {
        super(ERROR_MESSAGE + num);
    }
}
