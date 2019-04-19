package ru.cft.focusstart.suhodolov.shapes;

/**
 * Класс представляющий треугольник, наследуемый от класса Shape
 */
public class Triangle extends Shape {

    private double sideA;
    private double sideB;
    private double sideC;

    private double oppositeAngleA;
    private double oppositeAngleB;
    private double oppositeAngleC;

    /**
     * Конструктор класса, который инициализирует все поля класса (в том числе и родительского класса)
     *
     * @param sideA 1-ая сторона
     * @param sideB 2-ая сторона
     * @param sideC 3-я сторона
     */
    public Triangle(double sideA, double sideB, double sideC) {
        this.name = "Треугольник";

        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;

        this.oppositeAngleA = calcOppositeAngle(sideA, sideB, sideC);
        this.oppositeAngleB = calcOppositeAngle(sideB, sideA, sideC);
        this.oppositeAngleC = calcOppositeAngle(sideC, sideA, sideB);

        this.area = calcArea(sideA, sideB, sideC);
        this.perimeter = calcPerimeter(sideA, sideB, sideC);
    }

    /**
     * {@inheritDoc}
     * Также возвращает длины сторон треугольника и их противоположные углы в градусах
     */
    @Override
    public StringBuilder getInformation() {
        StringBuilder information = super.getInformation();

        information.append(String.format("Длина стороны A и ее противолежащий угол: %.2f и %.2f\n",
                sideA, oppositeAngleA));
        information.append(String.format("Длина стороны B и ее противолежащий угол: %.2f и %.2f\n",
                sideB, oppositeAngleB));
        information.append(String.format("Длина стороны C и ее противолежащий угол: %.2f и %.2f\n",
                sideC, oppositeAngleC));

        return information;
    }

    /**
     * Вычисление площади треугольника через формулу Герона
     *
     * @param sideA 1-ая сторона
     * @param sideB 2-ая сторона
     * @param sideC 3-я сторона
     * @return площадь треугольника
     */
    private double calcArea(double sideA, double sideB, double sideC) {
        double semiperimeter = 0.5 * (sideA + sideB + sideC);

        return Math.sqrt(semiperimeter * (semiperimeter - sideA)
                * (semiperimeter - sideB) * (semiperimeter - sideC));
    }

    /**
     * Вычисление периметра треугольника
     *
     * @param sideA 1-ая сторона
     * @param sideB 2-ая сторона
     * @param sideC 3-я сторона
     * @return периметр треугольника
     */
    private double calcPerimeter(double sideA, double sideB, double sideC) {
        return sideA + sideB + sideC;
    }

    /**
     * Вычисление угла через теорему косинусов
     *
     * @param oppositeSide противолежащая сторона
     * @param anotherSideA 2-ая сторона
     * @param anotherSideB 3-я сторона
     * @return угол в градусах
     */
    private double calcOppositeAngle(double oppositeSide, double anotherSideA, double anotherSideB) {
        return Math.toDegrees(Math.acos((Math.pow(anotherSideA, 2) + Math.pow(anotherSideB, 2) - Math.pow(oppositeSide, 2))
                / (2 * anotherSideA * anotherSideB)));
    }
}
