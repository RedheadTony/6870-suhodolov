package ru.cft.focusstart.suhodolov;

import ru.cft.focusstart.suhodolov.shapes.*;

import java.io.*;
import java.util.ArrayList;

public class Application {

    private static BufferedReader reader;
    private static FileWriter writer;
    private static Shape shape;

    public static void main(String[] args) {
        parseArgs(args);
        if (reader != null) {
            readInputFile();
        }
        if (shape != null) {
            writeInfo();
        }
    }

    /**
     * Метод, который парсит аргументы командной строки
     *
     * @param args строковый массив, содержащий аргументы
     */
    private static void parseArgs(String[] args) {
        try {
            reader = new BufferedReader(new FileReader(args[0]));
            if (args.length >= 2) {
                writer = new FileWriter(args[1], false);
            }
        } catch (IOException ex) {
            System.out.println("Problem with opening input/output files");
        }
    }

    /**
     * Метод, который парсит параметры фигуры из строки
     *
     * @param strParams строка, содержащая параметры
     * @return ArrayList, содержащий параметры в формате Double
     * @throws NumberFormatException выбрасывается, когда в строке встречаются не числовые параметры
     */
    private static ArrayList<Double> parseShapeParams(String strParams) throws NumberFormatException {
        ArrayList<Double> params = new ArrayList<>();
        String[] strParamsArr = strParams.split("[ ]");
        double num;

        for (String s : strParamsArr) {
            num = Double.parseDouble(s);
            if (num > 0) {
                params.add(num);
            } else {
                throw new NumberFormatException();
            }
        }

        return params;
    }

    /**
     * Метод, который считывает информацию из входного файла и инициализирует объект фигуры с полученными параметрами
     */
    private static void readInputFile() {
        try {
            String shapeType = reader.readLine();
            String strParams = reader.readLine();
            ArrayList<Double> params = parseShapeParams(strParams);

            if (shapeType.equals(ShapeType.CIRCLE.name()) && params.size() == 1) {
                shape = new Circle(params.get(0));
            } else if (shapeType.equals(ShapeType.RECTANGLE.name()) && params.size() == 2) {
                shape = new Rectangle(params.get(0), params.get(1));
            } else if (shapeType.equals(ShapeType.TRIANGLE.name()) && params.size() == 3) {
                shape = new Triangle(params.get(0), params.get(1), params.get(2));
            } else {
                System.out.println("Invalid information in input file");
            }
        } catch (IOException ex) {
            System.out.println("Problem with reading information from input file");
        } catch (NumberFormatException ex) {
            System.out.println("Invalid parameters format");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                System.out.println("Problem with closing the input file");
            }
        }
    }

    /**
     * Метод, который записывает информацию об объекте либо в выходной файл, либо в консоль
     */
    private static void writeInfo() {
        try {
            if (writer != null) {
                writer.write(shape.getInformation().toString());
            } else {
                System.out.println(shape.getInformation());
            }
        } catch (IOException ex) {
            System.out.println("Problem with writing information to output file");
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Problem with closing the output file");
            }
        }
    }
}
