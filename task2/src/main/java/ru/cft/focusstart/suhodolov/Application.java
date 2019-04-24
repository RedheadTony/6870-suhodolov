package ru.cft.focusstart.suhodolov;

import ru.cft.focusstart.suhodolov.exceptions.ApplicationException;
import ru.cft.focusstart.suhodolov.shapes.*;

import java.io.*;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2) {
            System.out.println("Invalid arguments");
            return;
        }

        try {
            ShapeFileParser parser = new ShapeFileParser(args[0]);
            Shape shape = createShape(parser.getShapeType(), parser.getShapeParams());

            if (args.length == 2) {
                writeShapeToFile(shape, args[1]);
            } else {
                writeShapeToConsole(shape);
            }
        } catch (ApplicationException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Метод, который создает объект фигуры с помощью считанных параметров и возвращает его
     *
     * @param shapeType тип фигуры
     * @param params    числовые параметры
     * @return объект типа Shape
     * @throws ApplicationException исключение, которое выбрасывается при проблемах с приложением
     */
    private static Shape createShape(String shapeType, List<Double> params) throws ApplicationException {
        if (shapeType.equals(ShapeType.CIRCLE.name()) && params.size() == 1) {
            return new Circle(params.get(0));
        } else if (shapeType.equals(ShapeType.RECTANGLE.name()) && params.size() == 2) {
            return new Rectangle(params.get(0), params.get(1));
        } else if (shapeType.equals(ShapeType.TRIANGLE.name()) && params.size() == 3) {
            return new Triangle(params.get(0), params.get(1), params.get(2));
        } else {
            throw new ApplicationException("Invalid information in input file");
        }
    }

    /**
     * Метод, который записывает информацию о фигуре в выходной файл
     *
     * @param shape фигура
     * @param file  имя файла
     * @throws ApplicationException исключение, которое выбрасывается при проблемах с приложением
     */
    private static void writeShapeToFile(Shape shape, String file) throws ApplicationException {
        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write(shape.getInformation().toString());
        } catch (IOException e) {
            throw new ApplicationException("Problem with writing information to output file", e);
        }
    }

    /**
     * Метод, который записывает информацию о фигуре в консоль
     *
     * @param shape фигура
     */
    private static void writeShapeToConsole(Shape shape) {
        System.out.println(shape.getInformation());
    }
}
