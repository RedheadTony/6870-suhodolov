package ru.cft.focusstart.suhodolov.observers;

import ru.cft.focusstart.suhodolov.model.Board;

/**
 * Интерфейс обсервера, который следит за состоянием игры
 */
public interface GameStateObserver {

    /**
     * Метод, который будет вызываться, когда начнется новая игра
     *
     * @param rows          количество строк в новой игре
     * @param cols          количество столбцов в новой игре
     * @param numberOfMines количество мин в новой игре
     */
    void onNewGame(int rows, int cols, int numberOfMines);

    /**
     * Метод, который будет вызываться, когда пользователь выиграет
     */
    void onWin();

    /**
     * Метод, который будет вызываться, когда пользователь проиграет
     *
     * @param board поле игры
     */
    void onLose(Board board);
}
