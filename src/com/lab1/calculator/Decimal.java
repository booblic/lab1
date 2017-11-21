package com.lab1.calculator;

/**
 * Класс для вычисления выражения записанного в десятичной системе счисления
 * @author Кирилл
 * @version 1.0
 */
public class Decimal extends Calculator {

    /**
     * Конструктор - создание нового объекта
     * @param input - неотформатированная строка, содержащая исходное выражение
     */
    public Decimal(String input) {
        super(input);
    }

    /**
     * Метод парсит строку с числом
     * @param input - строка, содержащая число
     * @return x - число
     */
    public double parser(String input) {
        double x;

        x = Double.parseDouble(input);

        return x;
    }

    /**
     * Метод для перевода числа в строку
     * @param d - число
     * @return - строка
     */
    public String numberToString(Double d) {
        return d.toString();
    }
}