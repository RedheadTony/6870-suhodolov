package ru.cft.focusstart.suhodolov.model;

/**
 * Класс, который представляет собой игровую ячейку
 */
public class Cell {

    private final int x;
    private final int y;

    private boolean checked = false;
    private boolean mined = false;
    private boolean flagged = false;

    private int surroundingMines = 0;

    /**
     * Конструктор, который по переданным координатам инициализируеет координаты ячейки
     *
     * @param x координата
     * @param y координата
     */
    public Cell(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Геттер для координаты x
     *
     * @return координата x
     */
    public int getX() {
        return x;
    }

    /**
     * Геттер для координаты y
     *
     * @return координата y
     */
    public int getY() {
        return y;
    }

    /**
     * Метод, который проверяет, была ли проверена (открыта) даннная ячейка
     *
     * @return логическая переменная checked, которая показывает проверена ячейка или нет
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Сеттер, который устанавливает переменную checked у ячейки
     *
     * @param checked логическая переменная, которая показывает проверена ячейка или нет
     */
    public void setChecked(final boolean checked) {
        this.checked = checked;
    }

    /**
     * Метод, который проверяет, заминирована ли даннная ячейка
     *
     * @return логическая переменная mined, которая показывает заминирована ячейка или нет
     */
    public boolean isMined() {
        return mined;
    }

    /**
     * Сеттер, который устанавливает переменную mined у ячейки
     *
     * @param mined логическая переменная, которая показывает заминирована ячейка или нет
     */
    public void setMined(final boolean mined) {
        this.mined = mined;
    }

    /**
     * Метод, который проверяет, поставлен ли на данной ячейке флаг
     *
     * @return логическая переменная flagged, помечена ли данная ячейка флагом или нет
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     * Сеттер, который устанавливает переменную flagged у ячейки
     *
     * @param flagged логическая переменная, помечена ли данная ячейка флагом или нет
     */
    public void setFlagged(final boolean flagged) {
        this.flagged = flagged;
    }

    /**
     * Геттер, который возвращает количество мин по соседству с данной ячейкой
     *
     * @return количество мин по соседству с данной ячейкой
     */
    public int getSurroundingMines() {
        return surroundingMines;
    }

    /**
     * Сеттер, который устанавливает количество мин рядом с данной ячейкой
     *
     * @param surroundingMines количество мин по соседству с данной ячейкой
     */
    public void setSurroundingMines(final int surroundingMines) {
        this.surroundingMines = surroundingMines;
    }
}