package com.lab1.menu;

import com.lab1.calculator.*;
import com.lab1.constant.ClassOfConstant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Класс для первого пункта меню, соответсвующему считыванию выражения из файла
 * @author Кирилл
 * @version 1.0
 */
public class MenuFirst extends MenuEntry {

    /**
     * Переменная для хранения выражения введенного с клавиатуры
     */
    private String line = null;

    /**
     * Переменная для хранения системы счисления
     */
    private String numberSystems = null;

    /**
     * Конструктор вызывающий конструктор базового (абстрактного) класса
     * @param input - заглавие мпункта меню
     */
    public MenuFirst(String input) {
        super(input);
    }

    /**
     * Метод принимающий любой объект класса насленика Calculator и вызывающий соответсвующий метод для начала расчет выражения
     * @param calc - объект класса насленика Calculator
     */
    public void action(Calculator calc) {
        System.out.println(calc.start());
    }

    /**
     * Метод содержащий последовательнсть действий пункта меню
     */
    public void run() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print(ClassOfConstant.ENTRNUMBERSYSTEM);
            numberSystems = reader.readLine();
            System.out.println(ClassOfConstant.ENTEREDNUMBERSYSTEM + numberSystems);
            System.out.print(ClassOfConstant.ENTREXPRESSION);
            line = reader.readLine();
            System.out.println(ClassOfConstant.ENTEREDSTRING + line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int openBracketNumber = 0, closeBracketNumber = 0; // переменные для хранения количества открывающихся и закрывающихся скобок

        for (int j = 0; j < line.length(); j++) {
            if (line.charAt(j) == '(') { openBracketNumber++; }
            if (line.charAt(j) == ')') { closeBracketNumber++; }
        }

        if(openBracketNumber != closeBracketNumber) {
            System.out.println(ClassOfConstant.NOBRECKETS);
            return;
        }

        switch (numberSystems) {
            case "2":
                action(new Binary(line));
                break;
            case "8":
                action(new Octal(line));
                break;
            case "10":
                action(new Decimal(line));
                break;
            case "16":
                action(new Hexadecimal(line));
                break;
        }

    }
}