package ru.cft.focusstart.suhodolov.observers;

import ru.cft.focusstart.suhodolov.model.Board;

/**
 * Интерфейс обсервера, который следит за состоянием игры
 */
public interface GameStateObserver {

    void onNewGame(int rows, int cols, int numberOfMines);

    void onWin();

    void onLose(Board board);
}
