package com.lab1.calculator;

/**
 * Класс для вычисления выражения записанного в двоичной системе счисления
 * @author Кирилл
 * @version 1.0
 */
public class Binary extends Calculator {

    /**
     * Конструктор - создание нового объекта
     * @param input - неотформатированная строка, содержащая исходное выражение
     */
    public Binary(String input) {
         super(input);;
    }

    /**
     * Метод парсит строку с числом
     * @param input - строка, содержащая число
     * @return x - число
     */
    public double parser(String input) {
        int x;

        x = Integer.parseInt(input, 2);

        return x;
    }

    /**
     * Метод для перевода числа в строку
     * @param i - число
     * @return - строка
     */
    public String numberToString(Double i) {
        return Integer.toBinaryString(i.intValue());
    }
}