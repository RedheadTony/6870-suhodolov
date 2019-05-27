package ru.cft.focusstart.suhodolov.model;

import ru.cft.focusstart.suhodolov.observers.CellObserver;
import ru.cft.focusstart.suhodolov.observers.GameStateObserver;
import ru.cft.focusstart.suhodolov.observers.LeaderBoardObserver;
import ru.cft.focusstart.suhodolov.observers.TimerObserver;


import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;

/**
 * Основной класс модели
 */
public class Game {

    private final CellObserver cellObserver;
    private final GameStateObserver gameStateObserver;
    private final LeaderBoardObserver leaderBoardObserver;

    private Cell[][] board;

    private DifficultyType difficulty = DifficultyType.BEGINNER;

    private final Timer timer;
    private int timePassed;

    private boolean playing = false;
    private int rows = 9;
    private int cols = 9;
    private int notFoundedMines = 10;

    private List<Cell> changedCells = new ArrayList<>();

    /**
     * Конструктор, который инициализирует Observers, таймер и запускает новую игру
     */
    public Game(final CellObserver cellObserver, final GameStateObserver gameStateObserver,
                final TimerObserver timerObserver, final LeaderBoardObserver leaderBoardObserver) {

        this.cellObserver = cellObserver;
        this.gameStateObserver = gameStateObserver;
        this.leaderBoardObserver = leaderBoardObserver;

        timer = new Timer(1000, e -> {
            timePassed++;
            timerObserver.onTimerChange(timePassed);
        });

        newGame(difficulty);
    }

    /**
     * Метод, который по входныму параметру начинает новую игру нужной сложности и сообщает об этом обсерверу
     *
     * @param difficulty сложность новой игры
     */
    private void newGame(final DifficultyType difficulty) {
        changedCells = new ArrayList<>();

        rows = difficulty.getRows();
        cols = difficulty.getCols();
        notFoundedMines = difficulty.getNumberOfMines();

        board = getEmptyCells();
        setMines(notFoundedMines);
        setNeighboursNumber(rows, cols);

        timePassed = 0;

        gameStateObserver.onNewGame(difficulty);

        playing = true;
    }

    /**
     * Метод, который меняет сложность игры и начинает ее с этой сложностью
     *
     * @param difficulty сложность игры
     */
    public void setDifficulty(DifficultyType difficulty) {
        this.difficulty = difficulty;
        newGame(difficulty);
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
     * Метод, который расставляет мины на случайных ячейках
     *
     * @param numberOfMines количество мин, которое нужно расставить
     */
    private void setMines(final int numberOfMines) {
        int x, y;
        int currentMines = 0;

        while (currentMines != numberOfMines) {
            x = (int) Math.floor(Math.random() * cols);
            y = (int) Math.floor(Math.random() * rows);

            if (!board[x][y].isMined()) {
                board[x][y].setMined(true);
                currentMines++;
            }
        }
    }

    /**
     * Метод, который для каждой ячейки вычисляет количество мин по соседству
     *
     * @param rows количество строк
     * @param cols количество колонок
     */
    private void setNeighboursNumber(final int rows, final int cols) {
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                board[x][y].setSurroundingMines(calcNeighbours(board[x][y]));
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
                    if (board[x][y].isMined()) {
                        neighbours++;
                    }
                }
            }
        }
        return neighbours;
    }

    /**
     * Вспомогательный метод, который переданную координату делает валидной для поля
     *
     * @param num    координата
     * @param maxNum максимальное число, которое эта координата не может привышать
     * @return валидная координата
     */
    private int makeValidCoordinate(int num, final int maxNum) {
        if (num < 0) {
            num = 0;
        } else if (num > maxNum - 1) {
            num = maxNum - 1;
        }
        return num;
    }

    /**
     * Метод, который реагирует на действия пользователя по изменению ячейки
     *
     * @param x координата ячейки
     * @param y координата ячейки
     */
    public void changeCell(final int x, final int y) {
        if (!playing) {
            return;
        }
        if (!timer.isRunning()) {
            timer.start();
        }

        if (board[x][y].isFlagged()) {
            return;
        }

        if (board[x][y].isChecked()) {
            openCellAroundFlags(x, y);
        } else {
            openCell(x, y);
        }
    }

    /**
     * Метод, который реагирует на действия пользователя по изменению флага ячейки и уведомляет обсервера
     *
     * @param x координата ячейки
     * @param y координата ячейки
     */
    public void changeFlag(final int x, final int y) {
        if (!playing) {
            return;
        }
        if (!timer.isRunning()) {
            timer.start();
        }
        Cell cell = board[x][y];

        if (cell.isChecked()) {
            return;
        }
        if (!cell.isFlagged()) {
            cell.setFlagged(true);
            notFoundedMines--;
        } else {
            cell.setFlagged(false);
            notFoundedMines++;
        }
        cellObserver.onFlagChanged(cell, notFoundedMines);
    }

    /**
     * Метод, который по переданным координатам открывает вокруг ячейки,
     * у которой рядом находится такое же количество мин, сколько и флагов, другие закрытые ячейки
     *
     * @param x координата
     * @param y координата
     */
    private void openCellAroundFlags(final int x, final int y) {
        Cell cell = board[x][y];
        int surroundingFlags = 0;
        for (int i = makeValidCoordinate(cell.getX() - 1, cols);
             i <= makeValidCoordinate(cell.getX() + 1, cols); i++) {
            for (int j = makeValidCoordinate(cell.getY() - 1, rows);
                 j <= makeValidCoordinate(cell.getY() + 1, rows); j++) {
                if (board[i][j].isFlagged()) {
                    surroundingFlags++;
                }
            }
        }
        if (cell.getSurroundingMines() == surroundingFlags) {
            openCellsAroundZero(cell);
        }
    }

    /**
     * Метод, который по переданным координатам открывает ячейку и уведомляет обсервера о изменении
     *
     * @param x координата
     * @param y координата
     */
    private void openCell(final int x, final int y) {
        Cell cell = board[x][y];

        if (cell.isChecked() || cell.isFlagged()) {
            return;
        }

        cell.setChecked(true);
        changedCells.add(cell);

        cellObserver.onCellsChanged(changedCells);

        if (cell.isMined()) {
            loseGame();
            return;
        } else if (cell.getSurroundingMines() == 0) {
            openCellsAroundZero(cell);
        }

        if (checkWinGame()) {
            winGame();
        }
    }

    /**
     * Метод, который открывает ячейки вокруг открытой пустой переданной ячейки
     *
     * @param openedCell открытая пустая ячейка
     */
    private void openCellsAroundZero(final Cell openedCell) {
        for (int x = makeValidCoordinate(openedCell.getX() - 1, cols);
             x <= makeValidCoordinate(openedCell.getX() + 1, cols); x++) {
            for (int y = makeValidCoordinate(openedCell.getY() - 1, rows);
                 y <= makeValidCoordinate(openedCell.getY() + 1, rows); y++) {
                openCell(x, y);
            }
        }
    }

    /**
     * Метод, который проверяет, выиграл ли пользователь игру
     *
     * @return true, если пользователь выиграл, false, если нет
     */
    private boolean checkWinGame() {
        int uncheckedCells = 0;
        for (Cell[] cells : board)
            for (Cell cell : cells) {
                if (!cell.isChecked()) {
                    uncheckedCells++;
                }
            }
        return uncheckedCells == difficulty.getNumberOfMines();
    }

    /**
     * Метод, который вызывается, когда игра выиграна
     * останавливает таймер и игру
     * уведомляет обсерверов о выигрыше
     */
    private void winGame() {
        timer.stop();
        playing = false;

        leaderBoardObserver.onLeadersListChange(difficulty, timePassed);
        gameStateObserver.onWin();
    }

    /**
     * Метод, который вызывается, когда игра проиграна
     * останавливает таймер и игру
     * уведомляет обсервера о проигрыше
     */
    private void loseGame() {
        timer.stop();
        playing = false;

        gameStateObserver.onLose(board);
    }
}