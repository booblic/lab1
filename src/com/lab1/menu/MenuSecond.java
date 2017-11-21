package com.lab1.menu;

import com.lab1.calculator.*;
import com.lab1.constant.ClassOfConstant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Класс для второго пункта меню, соответсвующего вводу выражения с клавиатуры
 * @author Кирилл
 * @version 1.0
 */
public class MenuSecond extends MenuEntry {

    /**
     * Переменная для хранения строки считанной из файла
     */
    private String input = null;

    /**
     * Переменная для хранения выражения
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
    public MenuSecond(String input) {
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

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("file.txt"))) {
            input = bufferedReader.readLine();
        } catch (IOException exc) {
            System.out.println(exc);
        }

        Scanner scanner = new Scanner(input);

        if (scanner.hasNext()) {
            numberSystems = scanner.nextInt();

            if ((numberSystems != 2) && (numberSystems != 8) && (numberSystems != 10) && (numberSystems != 16)) {
                System.out.println(ClassOfConstant.WRONG_SYSREM_NUMBER);
                return;
            }

            System.out.println(ClassOfConstant.READED_SYSREM_NUMBER + numberSystems);
            line = scanner.nextLine().trim();
            System.out.println(ClassOfConstant.READED_EXPRESSION + line);

        } else {
            System.out.println(ClassOfConstant.EMPTY_STRING);
        }

        int openBracketNumber = 0, closeBracketNumber = 0;

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
                action(new Binary(line));
                break;
            case 8:
                action(new Octal(line));
                break;
            case 10:
                action(new Decimal(line));
                break;
            case 16:
                action(new Hexadecimal(line));
                break;
        }
    }
}
