package ru.cft.focusstart.suhodolov.observers;

/**
 * Интерфейс обсервера, который следит за изменением таймера
 */
public interface TimerObserver {

    void onTimerChange(int timePassed);
}
