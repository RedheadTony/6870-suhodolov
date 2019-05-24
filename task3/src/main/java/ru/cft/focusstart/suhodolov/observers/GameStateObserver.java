package ru.cft.focusstart.suhodolov.observers;

import ru.cft.focusstart.suhodolov.model.Board;
import ru.cft.focusstart.suhodolov.model.DifficultyType;

/**
 * Интерфейс обсервера, который следит за состоянием игры
 */
public interface GameStateObserver {

    /**
     * Метод, который будет вызываться, когда начнется новая игра
     *
     * @param difficulty сложность игры
     */
    void onNewGame(DifficultyType difficulty);

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
