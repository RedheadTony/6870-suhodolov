package ru.cft.focusstart.suhodolov;

/**
 * Класс, который представляет собой игровое поле со всеми ячейками
 */
public class Board {

    private final int rows;
    private final int cols;
    private final Cell[][] cells;

    /**
     * Конструктор, который через входные параметры создает игровое поле
     *
     * @param rows          количество строк
     * @param cols          количество колонок
     * @param numberOfMines количество мин
     */
    public Board(final int rows, final int cols, final int numberOfMines) {
        this.rows = rows;
        this.cols = cols;
        this.cells = getEmptyCells();

        setMines(numberOfMines);
        setNeighboursNumber(rows, cols);
    }

    /**
     * Метод, который инициализирует массив пустых ячеек и возвращает его
     *
     * @return двумерный массив ячеек cells
     */
    private Cell[][] getEmptyCells() {
        Cell[][] cells = new Cell[cols][rows];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
        return cells;
    }

    /**
     * Метод, который расставляет мины на случайных ячеек
     *
     * @param numberOfMines количество мин, которое нужно расставить
     */
    private void setMines(final int numberOfMines) {
        int x, y;
        int currentMines = 0;

        while (currentMines != numberOfMines) {
            x = (int) Math.floor(Math.random() * cols);
            y = (int) Math.floor(Math.random() * rows);

            if (!cells[x][y].isMined()) {
                cells[x][y].setMined(true);
                currentMines++;
            }
        }
    }

    /**
     * Метод, который ждя каждой ячейки вычисляет количество мин по соседству
     *
     * @param rows количество строк
     * @param cols количество колонок
     */
    private void setNeighboursNumber(final int rows, final int cols) {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                cells[x][y].setSurroundingMines(calcNeighbours(cells[x][y]));
            }
        }
    }

    /**
     * Метод, который вычисляет вычисляет для переданной ячейки количество мин по соседству
     *
     * @param cell ячейка
     * @return количество мин по соседству
     */
    private int calcNeighbours(final Cell cell) {
        int neighbours = 0;

        for (int x = makeValidCoordinate(cell.getX() - 1, cols);
             x <= makeValidCoordinate(cell.getX() + 1, cols); x++) {
            for (int y = makeValidCoordinate(cell.getY() - 1, rows);
                 y <= makeValidCoordinate(cell.getY() + 1, rows); y++) {
                if (x != cell.getX() || y != cell.getY()) {
                    if (cells[x][y].isMined()) {
                        neighbours++;
                    }
                }
            }
        }
        return neighbours;
    }

    /**
     * Статический вспомогательный метод, который переданную координату делает валидной для поля
     *
     * @param num    координата
     * @param maxNum максимальное число, которое эта координата не может привышать
     * @return валидная координата
     */
    public static int makeValidCoordinate(int num, final int maxNum) {
        if (num < 0) {
            num = 0;
        } else if (num > maxNum - 1) {
            num = maxNum - 1;
        }
        return num;
    }

    /**
     * Геттер для ячейки, который возвращает ее по переданным координатам
     *
     * @param x координата
     * @param y координата
     * @return ячейка из массива cells
     */
    public Cell getCell(final int x, final int y) {
        return cells[x][y];
    }

    /**
     * Геттер для двумерного массива ячеек
     *
     * @return массив ячеек cells
     */
    public Cell[][] getCells() {
        return cells;
    }
}