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
    }

    /**
     * Выводит таблицу
     */
    public void printTable() {
        if (sepLine == null) {
            setSeparatingLine();
        }

        for (int i = 1; i <= size; i++) {
            System.out.println(getNumberLine(i));
            System.out.println(sepLine);
        }
    }

    /**
     * Составляет разделяющую строку
     */
    private void setSeparatingLine() {
        if (cellLength == 0) {
            setCellLength(size * size);
        }

        sepLine = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sepLine.append("-".repeat(cellLength));
            if (i != size - 1) {
                sepLine.append("+");
            }
        }
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
     * Составляет строку с числами и возвращает ее
     *
     * @param row - номер строки таблицы
     * @return строка с числами
     */
    private StringBuilder getNumberLine(int row) {
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
        return numLine;
    }
}