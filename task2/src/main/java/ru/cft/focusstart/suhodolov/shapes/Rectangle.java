package ru.cft.focusstart.suhodolov.shapes;

/**
 * Класс представляющий прямоугольник, наследуемый от класса Shape
 */
public class Rectangle extends Shape {

    private double length;
    private double width;
    private double diagonal;

    /**
     * Конструктор класса, который инициализирует все поля класса (в том числе и родительского класса)
     *
     * @param sideA 1-ая сторона прямоугольника
     * @param sideB 2-ая второна прямоугольника
     */
    public Rectangle(double sideA, double sideB) {
        this.name = "Прямоугольник";

        this.length = Math.max(sideA, sideB);
        this.width = Math.min(sideA, sideB);
        this.diagonal = calcDiagonal(sideA, sideB);

        this.area = calcArea(sideA, sideB);
        this.perimeter = calcPerimeter(sideA, sideB);
    }

    /**
     * {@inheritDoc}
     * Также возвращает диагональ и стороны прямоугольника
     */
    @Override
    public StringBuilder getInformation() {
        StringBuilder information = super.getInformation();

        information.append(String.format("Длина диагонали: %.2f\n", diagonal));
        information.append(String.format("Длина: %.2f\n", length));
        information.append(String.format("Ширина: %.2f\n", width));

        return information;
    }

    /**
     * Вычисление площади прямоугольника
     *
     * @param sideA 1-ая сторона
     * @param sideB 2-ая сторона
     * @return площадь прямоугольника
     */
    private double calcArea(double sideA, double sideB) {
        return sideA * sideB;
    }

    /**
     * Вычисление периметра прямоугольника
     *
     * @param sideA 1-ая сторона
     * @param sideB 2-ая сторона
     * @return периметр прямоугольника
     */
    private double calcPerimeter(double sideA, double sideB) {
        return 2 * sideA + 2 * sideB;
    }

    /**
     * Вычисление длины диагонали прямоугольника
     *
     * @param sideA 1-ая сторона
     * @param sideB 2-ая сторона
     * @return длина диагонали прямоугольника
     */
    private double calcDiagonal(double sideA, double sideB) {
        return Math.sqrt(Math.pow(sideA, 2) + Math.pow(sideB, 2));
    }
}
