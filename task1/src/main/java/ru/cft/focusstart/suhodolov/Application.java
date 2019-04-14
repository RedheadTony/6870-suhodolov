package ru.cft.focusstart.suhodolov;

import ru.cft.focusstart.suhodolov.exceptions.MismatchSizeException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        int size;

        System.out.println("Please write the size of the table in the range from 1 to 32:");

        try {
            size = new Scanner(System.in).nextInt();
            checkSize(size);

            new Table(size).printTable();
        } catch (InputMismatchException ex) {
            System.out.println("This value is incorrect");
        } catch (MismatchSizeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Проверка того, входит ли введеное число в диапазон значений
     *
     * @param size - размер таблицы, который ввел пользователь
     * @throws MismatchSizeException - выбрасывается при размере, не входящем в диапазон значений
     */
    private static void checkSize(int size) throws MismatchSizeException {
        if (size < 1 || size > 32) {
            throw new MismatchSizeException("This value is incorrect: ", size);
        }
    }
}
