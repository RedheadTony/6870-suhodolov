package ru.cft.focusstart.suhodolov.observers;

import ru.cft.focusstart.suhodolov.model.Cell;

import java.util.List;

/**
 * Интерфейс обсервера, который следит за изменением состояния ячеек
 */
public interface CellObserver {

    /**
     * Метод, который будет вызываться, когда модель изменит ячейки
     *
     * @param changedCells список измененных ячеек
     */
    void onCellsChanged(List<Cell> changedCells);

    /**
     * Метод, который будет вызываться, когда модель изменит флаги на какой-то ячейке
     *
     * @param cell            ячейка, с которой манипулирует пользователь
     * @param notFoundedMines количество ненайденных мин
     */
    void onFlagChanged(Cell cell, int notFoundedMines);
}
