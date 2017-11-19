package com.lab1.calculator;

import com.lab1.constant.ClassOfConstant;

public class Octal extends Calculator {

    private String expression; // исходная строка
    private String result;

    private StringBuilder calculateString = new StringBuilder(); // строка для вычисления выражения
    private int closeBracket, openBracket; // переменные для хранения позиции скобок
    private boolean sign = true; // переменная для хранения знака
    private int finishPosition = 0, startPosition = 0; // переменные для хранения позиции начала и конца подвыражения

    public Octal(String input) {

        expression = formater(input);

    }

    public String start() {

        result = calculation(expression).toString();

        if (sign == false) {
            result = "-" + result;
        }
        return result;
    }

    // уберает лишние пробелы
    public String formater(String input) {
        if (input == null) return null;
        return input.replaceAll(" ", "");
    }

    // вычисляет размер массива для хранения позиции знаков в выражении/подвыражении
    public int arraySizeCalculation(String input) {

        int slots = 0;

        for (int i = 0; i < input.length(); i++) {

            if ((input.charAt(i) == '+') || (input.charAt(i) == '-') ||
                    (input.charAt(i) == '*') || (input.charAt(i) == '/') ||
                    (input.charAt(i) == ')') || (input.charAt(i) == '(')) {

                slots++;
            }
        }
        return slots;
    }

    public void brackets() {

        for (int i = 0; i < calculateString.length(); i++) {

            if (calculateString.charAt(i) == '(') {

                openBracket = i;

            }
            if (calculateString.charAt(i) == ')') {

                closeBracket = i;

                String newСalculateString;
                newСalculateString = calculateString.substring(openBracket + 1, closeBracket);
                System.out.println(ClassOfConstant.BRECKETSEXPRESSION + newСalculateString);

                calculateString.delete(openBracket, closeBracket + 1);
                System.out.println(ClassOfConstant.AFTERDELETEDBRECKETSEXPRESSION + calculateString);

                StringBuilder mainString = new StringBuilder(calculateString);
                System.out.println(ClassOfConstant.NEWSTRINGWITHOUTBRECKETSEXPRESSION + mainString);

                calculation(newСalculateString);

                mainString.insert(openBracket, calculateString.toString());
                System.out.println(ClassOfConstant.NEWSTRINGWITHRESUALTBRECKETSEXPRESSION + mainString);

                calculateString.delete(0, calculateString.length());
                calculateString.insert(0, mainString.toString());

                System.out.println(ClassOfConstant.STRINGWITHRESUALTBRECKETSEXPRESSION + calculateString);

                brackets();
            }
        }
    }

    public void minuser() {
        if (calculateString.charAt(0) == '-') {
            sign = false;
            calculation(calculateString.delete(0, 1).toString());
        }
    }

    public void multdiv() {

        int[] CharsPosition = new int[arraySizeCalculation(calculateString.toString()) + 2]; // массив для хранения позиции знаков в выражении/подвыражении

        int a, b; // переменные для операндов
        Integer c = null; // переменная для результата вычисления двух операндов

        for (int i = 0; i < calculateString.length(); i++) {

            if (calculateString.charAt(i) == '*') {

                CharsPosition[0] = 0;

                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')')) {

                        CharsPosition[k] = j + 1;
                        k++;
                    }

                    CharsPosition[CharsPosition.length - 1] = calculateString.length() + 1;

                }

                for (int k = 0; k < CharsPosition.length; k++) {

                    if (CharsPosition[k] == i + 1) {
                        startPosition = CharsPosition[k - 1];
                        finishPosition = CharsPosition[k + 1] - 1;
                    }
                }

                a = Integer.parseInt(calculateString.substring(startPosition, i).toString(), 8);
                b = Integer.parseInt(calculateString.substring(i + 1, finishPosition).toString(), 8);

                System.out.println(ClassOfConstant.PARSINGNUMBER + a);
                System.out.println(ClassOfConstant.PARSINGSIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSINGNUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                c = a * b;

                System.out.println(ClassOfConstant.RESUALTSUBEXPRESSION + c);
                calculateString.insert(startPosition, Integer.toOctalString(c));
                System.out.println(ClassOfConstant.STRINGWITHRESUALT + calculateString);
                break;
            }

            if (calculateString.charAt(i) == '/') {


                CharsPosition[0] = 0;

                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')')) {

                        CharsPosition[k] = j + 1;
                        k++;
                    }

                    CharsPosition[CharsPosition.length - 1] = calculateString.length() + 1;

                }
                for (int k = 0; k < CharsPosition.length; k++) {

                    if (CharsPosition[k] == i + 1) {
                        startPosition = CharsPosition[k - 1];
                        finishPosition = CharsPosition[k + 1] - 1;
                    }
                }

                a = Integer.parseInt(calculateString.substring(startPosition, i).toString(), 8);
                b = Integer.parseInt(calculateString.substring(i + 1, finishPosition).toString(), 8);

                System.out.println(ClassOfConstant.PARSINGNUMBER + a);
                System.out.println(ClassOfConstant.PARSINGSIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSINGNUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                if (b == 0) {
                    System.out.println(ClassOfConstant.DIVIZIONBYZERO);
                    return;
                }

                c = a / b;

                System.out.println(ClassOfConstant.RESUALTSUBEXPRESSION + c);
                calculateString.insert(startPosition, Integer.toOctalString(c));
                System.out.println(ClassOfConstant.STRINGWITHRESUALT + calculateString);
                break;
            }
        }

        for (int i = 0; i < calculateString.length(); i++) {
            if ((calculateString.charAt(i) == '*') || (calculateString.charAt(i) == '/')) {
                multdiv();
            }
        }
    }

    public void addsub() {

        int[] CharsPosition = new int[arraySizeCalculation(calculateString.toString()) + 2]; // массив для хранения позиции знаков в выражении/подвыражении

        int a, b; // переменные для операндов
        Integer c = null; // переменная для результата вычисления двух операндов

        for (int i = 0; i < calculateString.length(); i++) {

            if ((calculateString.charAt(i) == '+') && (sign == true)) {

                CharsPosition[0] = 0;

                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')')) {

                        CharsPosition[k] = j + 1;
                        k++;
                    }

                    CharsPosition[CharsPosition.length - 1] = calculateString.length() + 1;

                }
                for (int k = 0; k < CharsPosition.length; k++) {

                    if (CharsPosition[k] == i + 1) {
                        startPosition = CharsPosition[k - 1];
                        finishPosition = CharsPosition[k + 1] - 1;
                    }
                }

                a = Integer.parseInt(calculateString.substring(startPosition, i).toString(), 8);
                b = Integer.parseInt(calculateString.substring(i + 1, finishPosition).toString(), 8);

                System.out.println(ClassOfConstant.PARSINGNUMBER + a);
                System.out.println(ClassOfConstant.PARSINGSIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSINGNUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                c = a + b;

                System.out.println(ClassOfConstant.RESUALTSUBEXPRESSION + c);
                calculateString.insert(startPosition, Integer.toOctalString(c));
                System.out.println(ClassOfConstant.STRINGWITHRESUALT + calculateString);
                break;

            } else if ((calculateString.charAt(i) == '+') && (sign == false)) {

                CharsPosition[0] = 0;

                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')')) {

                        CharsPosition[k] = j + 1;
                        k++;
                    }

                    CharsPosition[CharsPosition.length - 1] = calculateString.length() + 1;

                }
                for (int k = 0; k < CharsPosition.length; k++) {

                    if (CharsPosition[k] == i + 1) {
                        startPosition = CharsPosition[k - 1];
                        finishPosition = CharsPosition[k + 1] - 1;
                    }
                }

                a = Integer.parseInt(calculateString.substring(startPosition, i).toString(), 8);
                b = Integer.parseInt(calculateString.substring(i + 1, finishPosition).toString(), 8);

                System.out.println(ClassOfConstant.PARSINGNUMBER + a);
                System.out.println(ClassOfConstant.PARSINGSIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSINGNUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                if (a <= b) {
                    c = b - a;
                } else {
                    c = a - b;
                    sign = true;
                }

                System.out.println(ClassOfConstant.RESUALTSUBEXPRESSION + c);
                calculateString.insert(startPosition, Integer.toOctalString(c));
                System.out.println(ClassOfConstant.STRINGWITHRESUALT + calculateString);
                break;
            }

            if ((calculateString.charAt(i) == '-') && (sign == true)) {

                CharsPosition[0] = 0;

                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')')) {

                        CharsPosition[k] = j + 1;
                        k++;
                    }

                    CharsPosition[CharsPosition.length - 1] = calculateString.length() + 1;

                }
                for (int k = 0; k < CharsPosition.length; k++) {

                    if (CharsPosition[k] == i + 1) {
                        startPosition = CharsPosition[k - 1];
                        finishPosition = CharsPosition[k + 1] - 1;
                    }
                }

                a = Integer.parseInt(calculateString.substring(startPosition, i).toString(), 8);
                b = Integer.parseInt(calculateString.substring(i + 1, finishPosition).toString(), 8);

                System.out.println(ClassOfConstant.PARSINGNUMBER + a);
                System.out.println(ClassOfConstant.PARSINGSIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSINGNUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                if (a >= b) {
                    c = a - b;
                } else if (b > a) {
                    c = b - a;
                    sign = false;
                }

                System.out.println(ClassOfConstant.RESUALTSUBEXPRESSION + c);
                calculateString.insert(startPosition, Integer.toOctalString(c));
                System.out.println(ClassOfConstant.STRINGWITHRESUALT + calculateString);
                break;

            } else if ((calculateString.charAt(i) == '-') && (sign == false)) {

                CharsPosition[0] = 0;

                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')')) {

                        CharsPosition[k] = j + 1;
                        k++;
                    }

                    CharsPosition[CharsPosition.length - 1] = calculateString.length() + 1;

                }
                for (int k = 0; k < CharsPosition.length; k++) {

                    if (CharsPosition[k] == i + 1) {
                        startPosition = CharsPosition[k - 1];
                        finishPosition = CharsPosition[k + 1] - 1;
                    }
                }

                a = Integer.parseInt(calculateString.substring(startPosition, i).toString(), 8);
                b = Integer.parseInt(calculateString.substring(i + 1, finishPosition).toString(), 8);

                System.out.println(ClassOfConstant.PARSINGNUMBER + a);
                System.out.println(ClassOfConstant.PARSINGSIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSINGNUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                c = a + b;

                System.out.println(ClassOfConstant.RESUALTSUBEXPRESSION + c);
                calculateString.insert(startPosition, Integer.toOctalString(c));
                System.out.println(ClassOfConstant.STRINGWITHRESUALT + calculateString);
                break;
            }
        }

        for (int i = 0; i < calculateString.length(); i++) {
            if ((calculateString.charAt(i) == '+') || (calculateString.charAt(i) == '-')) {
                addsub();
            }
        }
    }

    // вычисление выражения
    public StringBuilder calculation(String input) {

        calculateString.delete(0, calculateString.length());
        calculateString.append(input);

        // первый приоритет - скобки
        brackets();

        // второй приоритет минус перед выражением
        minuser();

        // третий приоритет умножения/деление
        multdiv();

        // четвертый приоритет сложение/вычитание
        addsub();

        return calculateString;
    }
}