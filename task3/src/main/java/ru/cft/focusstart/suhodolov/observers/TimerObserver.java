package ru.cft.focusstart.suhodolov.observers;

/**
 * Интерфейс обсервера, который следит за изменением таймера
 */
public interface TimerObserver {

    /**
     * Метод, который будет вызываться, когда таймер будет меняться
     *
     * @param timePassed число, которое показывает сколько времени с начала игры прошло
     */
    void onTimerChange(int timePassed);
}
