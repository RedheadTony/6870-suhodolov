package ru.cft.focusstart.suhodolov.observers;

import ru.cft.focusstart.suhodolov.model.DifficultyType;

/**
 * Интерфейс обсервера, который следит за изменением таблицы победителей
 */
public interface LeaderBoardObserver {

    /**
     * Метод, который будет вызываться, когда модель решит записать новый результат в таблицу рекордов
     *
     * @param difficulty сложность, на которой пользователь получил этот результат
     * @param timePassed результат, то время за которое пользователь прошел игру
     */
    void onLeadersListChange(DifficultyType difficulty, int timePassed);
}
