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
     * Переменная для хранения системы счисленияа
     */
    private int numberSystems = 0;

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
            System.out.print(ClassOfConstant.ENTR_SYSREM_NUMBER);

            try {
                numberSystems =  Integer.parseInt(reader.readLine());
            } catch (NumberFormatException exp) {
                System.out.println(ClassOfConstant.WRONG_SYSREM_NUMBER);
                return;
            }

            if ((numberSystems != 2) && (numberSystems != 8) && (numberSystems != 10) && (numberSystems != 16)) {
                System.out.println(ClassOfConstant.WRONG_SYSREM_NUMBER);
                return;
            }
            System.out.println(ClassOfConstant.ENTERED_SYSREM_NUMBER + numberSystems);
            System.out.print(ClassOfConstant.ENTR_EXPRESSION);
            line = reader.readLine();
            System.out.println(ClassOfConstant.ENTERED_STRING + line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int openBracketNumber = 0, closeBracketNumber = 0; // переменные для хранения количества открывающихся и закрывающихся скобок

        for (int j = 0; j < line.length(); j++) {
            if (line.charAt(j) == '(') { openBracketNumber++; }
            if (line.charAt(j) == ')') { closeBracketNumber++; }
        }

        if(openBracketNumber != closeBracketNumber) {
            System.out.println(ClassOfConstant.NO_BRECKETS);
            return;
        }

        switch (numberSystems) {
            case 2:
                action(Singleton.getBinary(line));
                break;
            case 8:
                action(Singleton.getOctal(line));
                break;
            case 10:
                action(Singleton.getDecimal(line));
                break;
            case 16:
                action(Singleton.getHexadecimal(line));
                break;
        }

    }
}
