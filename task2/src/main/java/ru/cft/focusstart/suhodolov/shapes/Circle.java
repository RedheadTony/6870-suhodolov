package ru.cft.focusstart.suhodolov.shapes;

/**
 * Класс представляющий круг, наследуемый от класса Shape
 */
public class Circle extends Shape {

    private double radius;
    private double diameter;

    /**
     * Конструктор класса, который инициализирует все поля класса (в том числе и родительского класса)
     *
     * @param radius радиус круга
     */
    public Circle(double radius) {
        this.name = "Круг";

        this.radius = radius;
        this.diameter = calcDiameter(radius);

        this.area = calcArea(radius);
        this.perimeter = calcPerimeter(radius);
    }

    /**
     * {@inheritDoc}
     * Также возвращает радиус и диаметр круга
     */
    @Override
    public StringBuilder getInformation() {
        StringBuilder information = super.getInformation();

        information.append(String.format("Радиус: %.2f\n", radius));
        information.append(String.format("Диаметр: %.2f\n", diameter));

        return information;
    }

    /**
     * Вычисление площади круга
     *
     * @param radius радиус круга
     * @return площадь круга
     */
    private double calcArea(double radius) {
        return Math.PI * Math.pow(radius, 2);
    }

    /**
     * Вычисление периметра круга
     *
     * @param radius радиус круга
     * @return периметр круга
     */
    private double calcPerimeter(double radius) {
        return 2 * Math.PI * radius;
    }

    /**
     * Вычисление диаметра круга
     *
     * @param radius радиус круга
     * @return диаметр круга
     */
    private double calcDiameter(double radius) {
        return 2 * radius;
    }
}
