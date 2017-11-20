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
    private String str = null;

    /**
     * Переменная для хранения выражения
     */
    private String substr = null;

    /**
     * Переменная для хранения системы счисленияа
     */
    private int i = 0;

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
            str = bufferedReader.readLine();
        } catch (IOException exc) {
            System.out.println(exc);
        }

        Scanner scanner = new Scanner(str);

        if (scanner.hasNext()) {
            i = scanner.nextInt();
            System.out.println(ClassOfConstant.READEDNUMBERSYSTEM + i);
            substr = scanner.nextLine().trim();
            System.out.println(ClassOfConstant.READEDEXPRESSION + substr);

        } else {
            System.out.println(ClassOfConstant.EMPTSTRING);
        }

        int openBracketNumber = 0, closeBracketNumber = 0;

        for (int j = 0; j < substr.length(); j++) {
            if (substr.charAt(j) == '(') { openBracketNumber++; }
            if (substr.charAt(j) == ')') { closeBracketNumber++; }
        }

        if(openBracketNumber != closeBracketNumber) {
            System.out.println(ClassOfConstant.NOBRECKETS);
            return;
        }

        switch (i) {
            case 2:
                action(new Binary(substr));
                break;
            case 8:
                action(new Octal(substr));
                break;
            case 10:
                action(new Decimal(substr));
                break;
            case 16:
                action(new Hexadecimal(substr));
                break;
        }
    }
}
