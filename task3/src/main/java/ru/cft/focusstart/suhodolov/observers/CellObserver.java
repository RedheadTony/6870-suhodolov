package ru.cft.focusstart.suhodolov.observers;

import ru.cft.focusstart.suhodolov.model.Cell;

import java.util.List;

/**
 * Интерфейс обсервера, который следит за изменением состояния ячеек
 */
public interface CellObserver {

    void onCellsChanged(List<Cell> changedCells);

    void onFlagChanged(Cell cell, int notFoundedMines);
}
