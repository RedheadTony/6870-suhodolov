package ru.cft.focusstart.suhodolov.observers;

import ru.cft.focusstart.suhodolov.model.DifficultyType;

/**
 * Интерфейс обсервера, который следит за изменением таблицы победителей
 */
public interface LeaderBoardObserver {

    void onLeadersListChange(DifficultyType difficulty, int timePassed);
}
