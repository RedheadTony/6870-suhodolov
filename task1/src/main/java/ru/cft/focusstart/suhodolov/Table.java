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
        int cellLength = getCellLength(size * size);
        this.cellLength = cellLength;
        this.sepLine = getSeparatingLine(size, cellLength);
    }

    /**
     * Выводит таблицу
     */
    public void printTable() {
        for (int i = 1; i <= size; i++) {
            System.out.println(getNumberLine(i));
            System.out.println(sepLine);
        }
    }

    /**
     * Составляет разделяющую строку
     *
     * @param size - размер таблицы
     * @param cellLength - размер ячейки
     * @return разделяющую строку
     */
    private StringBuilder getSeparatingLine(int size, int cellLength) {
        StringBuilder separatingLine = new StringBuilder();

        for (int i = 0; i < size; i++) {
            separatingLine.append("-".repeat(cellLength));
            if (i != size - 1) {
                separatingLine.append("+");
            }
        }
        return separatingLine;
    }

    /**
     * По наибольшему числу в таблице вычисляет размер ячеек
     *
     * @param maxNumber - максимальное число в таблице
     * @return размер ячеек
     */
    private int getCellLength(int maxNumber) {
        return String.valueOf(maxNumber).length();
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