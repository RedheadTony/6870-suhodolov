package ru.cft.focusstart.suhodolov;

public class Board {

    private final int rows;
    private final int cols;
    private final int numberOfMines;
    private final Cell[][] cells;

    public Board(final int rows, final int cols, final int numberOfMines) {
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

    private int calcNeighbours(final Cell cell) {
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

    public int makeValidCoordinate(int num, final int maxNum) {
        if (num < 0) {
            num = 0;
        } else if (num > maxNum - 1) {
            num = maxNum - 1;
        }
        return num;
    }

    public Cell getCell(final int x, final int y) {
        return cells[x][y];
    }

    public Cell[][] getCells() {
        return cells;
    }
}
