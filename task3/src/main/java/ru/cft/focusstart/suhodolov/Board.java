package ru.cft.focusstart.suhodolov;

public class Board {

    private int rows;
    private int cols;
    private int numberOfMines;
    private Cell[][] cells;

    public Board(int rows, int cols, int numberOfMines) {
        this.rows = rows;
        this.cols = cols;
        this.numberOfMines = numberOfMines;
        this.cells = getEmptyCells();

        setMines();
        setNeighboursNumber();
    }

    private Cell[][] getEmptyCells() {
        Cell[][] cells = new Cell[rows][cols];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
        return cells;
    }

    private void setMines() {
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

    private void setNeighboursNumber() {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                cells[x][y].setSurroundingMines(calcNeighbours(cells[x][y]));
            }
        }
    }

    private int calcNeighbours(Cell cell) {
        int neighbours = 0;

        for (int i = makeValidCoordinate(cell.getX() - 1, cols);
             i <= makeValidCoordinate(cell.getX() + 1, cols); i++) {
            for (int j = makeValidCoordinate(cell.getY() - 1, rows);
                 j <= makeValidCoordinate(cell.getY() + 1, rows); j++) {
                if (i != cell.getX() || j != cell.getY()) {
                    if (cells[i][j].isMined()) {
                        neighbours++;
                    }
                }
            }
        }
        return neighbours;
    }

    /**
     * Метод, который проверяет на виладность координату x или y, и делает ее валидной если нужно
     * Это вспомонательный метод для метода calcNeighbours
     *
     * @param num    координата x или y
     * @param maxNum количество строк или столбцов, в зависимости от координаты
     * @return валидную координату
     */
    public int makeValidCoordinate(int num, int maxNum) {
        if (num < 0) {
            num = 0;
        } else if (num > maxNum - 1) {
            num = maxNum - 1;
        }
        return num;
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public Cell[][] getCells() {
        return cells;
    }
}
