package ru.cft.focusstart.suhodolov.shapes;

/**
 * Абстрактный класс фигуры, содержит поля имени, площади и периметра
 */
public abstract class Shape {

    protected String name;
    protected double area;
    protected double perimeter;

    /**
     * Получение информации о фигуре
     *
     * @return строка, содержащая информацию о фигуре
     */
    public StringBuilder getInformation() {
        StringBuilder information = new StringBuilder();

        information.append(String.format("Тип фигуры: %s\n", name));
        information.append(String.format("Площадь: %.2f\n", area));
        information.append(String.format("Периметр: %.2f\n", perimeter));

        return information;
    }
}
