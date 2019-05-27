package ru.cft.focusstart.suhodolov.model;

/**
 * Перечисление, в котором хранятся типы сложности игры и данные о каждой сложности
 */
public enum DifficultyType {

    BEGINNER(9, 9, 10),
    INTERMEDIATE(16, 16, 40),
    EXPERT(16, 30, 99);

    private final int rows;
    private final int cols;
    private final int numberOfMines;

    /**
     * Конструктор перечисления, который каждой сложности ставит свои значения для новой игры
     * @param rows количество строк
     * @param cols количество столбцов
     * @param numberOfMines количество мин
     */
    DifficultyType(int rows, int cols, int numberOfMines) {
        this.rows = rows;
        this.cols = cols;
        this.numberOfMines = numberOfMines;
    }

    /**
     * Геттер для rows
     * @return количество строк в перечислении
     */
    public int getRows() {
        return rows;
    }

    /**
     * Геттер для cols
     * @return количество столбцов в перечислении
     */
    public int getCols() {
        return cols;
    }

    /**
     * Геттер для numberOfMines
     * @return количество мин в перечислении
     */
    public int getNumberOfMines() {
        return numberOfMines;
    }}
