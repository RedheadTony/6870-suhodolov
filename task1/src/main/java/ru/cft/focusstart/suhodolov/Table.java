package ru.cft.focusstart.suhodolov;

/**
 * Класс для вывода таблицы умножения
 */
class Table {

    private int size;
    private int cellLength;
    private StringBuilder sepLine;

    /**
     * Конструктор получает на вход размер и вычисляет размер ячеек и формат разделяющей строки
     *
     * @param size - размер таблицы
     */
    public Table(int size) {
        this.size = size;
        setCellLength(size * size);
        setSeparatingLine();
    }

    /**
     * По наибольшему числу в таблице вычисляет размер ячеек
     *
     * @param maxNumber - максимальное число в таблице
     */
    private void setCellLength(int maxNumber) {
        cellLength = String.valueOf(maxNumber).length();
    }

    /**
     * Вычисляет разделяющую строку
     */
    private void setSeparatingLine() {
        sepLine = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sepLine.append("-".repeat(cellLength));
            if (i != size - 1) {
                sepLine.append("+");
            }
        }
    }

    /**
     * Выводит таблицу
     */
    public void printTable() {
        for (int i = 1; i <= size; i++) {
            printNumberLine(i);
            System.out.println(sepLine);
        }
    }

    /**
     * Выводит строку с числами
     *
     * @param row - номер строки
     */
    private void printNumberLine(int row) {
        StringBuilder numLine = new StringBuilder();
        int num;
        int numLength;

        for (int j = 1; j <= size; j++) {
            num = row * j;
            numLength = String.valueOf(num).length();

            numLine.append(" ".repeat(cellLength - numLength));
            numLine.append(num);
            if (j != size) {
                numLine.append("|");
            }
        }
        System.out.println(numLine);
    }
}