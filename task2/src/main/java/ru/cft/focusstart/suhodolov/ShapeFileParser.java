package ru.cft.focusstart.suhodolov;

import ru.cft.focusstart.suhodolov.exceptions.ApplicationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShapeFileParser {

    private String shapeType;
    private List<Double> shapeParams;

    /**
     * Конструктор, который считывает информацию из входного файла
     */
    public ShapeFileParser(String file) throws ApplicationException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            shapeType = reader.readLine();
            String strParams = reader.readLine();
            shapeParams = parseShapeParams(strParams);
        } catch (IOException e) {
            throw new ApplicationException("Problem with reading information from input file", e);
        } catch (NumberFormatException e) {
            throw new ApplicationException("Invalid parameters format", e);
        }
    }

    /**
     * Метод, который парсит параметры фигуры из строки
     *
     * @param strParams строка, содержащая параметры
     * @return List, содержащий параметры в формате Double
     */
    private List<Double> parseShapeParams(String strParams) {
        List<Double> params = new ArrayList<>();
        String[] strParamsArr = strParams.split("[ ]");
        double num;

        for (String s : strParamsArr) {
            num = Double.parseDouble(s);
            params.add(num);
        }
        return params;
    }

    /**
     * Метод, который возвращает тип фигуры
     *
     * @return тип фигуры
     */
    public String getShapeType() {
        return shapeType;
    }

    /**
     * Метод, который возвращает параметры фигуры
     *
     * @return параметры фигуры
     */
    public List<Double> getShapeParams() {
        return shapeParams;
    }
}
