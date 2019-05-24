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

    private Board board;

    private DifficultyType difficulty = DifficultyType.BEGINNER;

    private final Timer timer;

    private int timePassed;


    private boolean playing = false;
    private int rows = 9;
    private int cols = 9;
    private int numberOfMines = 10;
    private int notFoundedMines = numberOfMines;

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

        timePassed = 0;

        gameStateObserver.onNewGame(difficulty);

        board = new Board(difficulty);

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

        if (board.getCell(x, y).isFlagged()) {
            return;
        }

        if (board.getCell(x, y).isChecked()) {
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
        Cell cell = board.getCell(x, y);

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
        Cell cell = board.getCell(x, y);
        int surroundingFlags = 0;
        for (int i = Board.makeValidCoordinate(cell.getX() - 1, cols);
             i <= Board.makeValidCoordinate(cell.getX() + 1, cols); i++) {
            for (int j = Board.makeValidCoordinate(cell.getY() - 1, rows);
                 j <= Board.makeValidCoordinate(cell.getY() + 1, rows); j++) {
                if (board.getCell(i, j).isFlagged()) {
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
        Cell cell = board.getCell(x, y);

        if (cell.isChecked() || cell.isFlagged()) {
            return;
        }

        cell.setChecked(true);
        changedCells.add(cell);

        cellObserver.onCellsChanged(changedCells);

        if (cell.isMined()) {
            losingGame();
            return;
        } else if (cell.getSurroundingMines() == 0) {
            openCellsAroundZero(cell);
        }

        if (checkWinGame()) {
            winningGame();
        }
    }

    /**
     * Метод, который открывает ячейки вокруг открытой пустой переданной ячейки
     *
     * @param openedCell открытая пустая ячейка
     */
    private void openCellsAroundZero(final Cell openedCell) {
        for (int x = Board.makeValidCoordinate(openedCell.getX() - 1, cols);
             x <= Board.makeValidCoordinate(openedCell.getX() + 1, cols); x++) {
            for (int y = Board.makeValidCoordinate(openedCell.getY() - 1, rows);
                 y <= Board.makeValidCoordinate(openedCell.getY() + 1, rows); y++) {
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
        for (Cell[] cells : board.getCells())
            for (Cell cell : cells) {
                if (!cell.isChecked()) {
                    uncheckedCells++;
                }
            }
        return uncheckedCells ==difficulty.getNumberOfMines();
    }

    /**
     * Метод, который вызывается, когда игра выиграна
     * останавливает таймер и игру
     * уведомляет обсерверов о выигрыше
     */
    private void winningGame() {
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
    private void losingGame() {
        timer.stop();
        playing = false;

        gameStateObserver.onLose(board);
    }
}