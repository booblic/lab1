package com.lab1.calculator;

import com.lab1.constant.ClassOfConstant;

public class Hexadecimal extends Calculator {

    /**
     * Начальная строка
     */
    private String expression;

    /**
     * Результат выражения
     */
    private String result;

    /**
     * Строка для записи промежуточных результатов
     */
    private StringBuilder calculateString = new StringBuilder();

    /**
     * Поля для хранения позиции скобок
     */
    private int closeBracket, openBracket;

    /**
     * Поле для хранения знака выражения: true - плюс, false - минус
     */
    private boolean sign = true;

    /**
     * Поля для хранения позиции начала и конца подвыражения
     */
    private int finishPosition = 0, startPosition = 0;

    /**
     * Конструктор - создание нового объекта
     * @param input - неотформатированная строка, содержащая исходное выражение
     */
    public Hexadecimal(String input) {

        expression = formater(input);

    }

    /**
     * Метод, который вызывается для начала расчета выражения
     * @return result - результат выражения
     */
    public String start() {

        result = calculation(expression).toString();

        if (sign == false) {
            result = "-" + result;
        }
        return result;
    }

    /**
     * Метод, который уберает лишние пробелы из полученной строки
     * @param input - неотформатированная строка, содержащая исходное выражение
     * @return  строка содержащая исходное выражение без пробелов
     */
    public String formater(String input) {
        if (input == null) return null;
        return input.replaceAll(" ", "");
    }

    /**
     * Метод который вычисляет размера массива для хранения позиции знаков в выражении/подвыражении
     * @param input - исходное выражение
     * @return slots - размер массива
     */
    public int arraySizeCalculation(String input) {

        int slots = 0;

        for (int i = 0; i < input.length(); i++) {

            if ((input.charAt(i) == '+') || (input.charAt(i) == '-') ||
                    (input.charAt(i) == '*') || (input.charAt(i) == '/') ||
                    (input.charAt(i) == ')') || (input.charAt(i) == '(') ||
                    (input.charAt(i) == '^')) {

                slots++;
            }
        }
        return slots;
    }

    /**
     * Первый приоритет
     * Метод ищет выражение в скобках и если оно есть, вызывает для него метод
     * @see Hexadecimal#calculation(String)
     * который расчитывает это выражение и возварщает результат
     * Для поиска следующих скобок, метод содержит рекурсию
     */
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

    /**
     * Второй приоритет
     * Если перед выражением стоит знак минус, то метод менят зачение знаковой переменной
     * @see Hexadecimal#sign
     */
    public void minuser() {
        if (calculateString.charAt(0) == '-') {
            sign = false;
            calculation(calculateString.delete(0, 1).toString());
        }
    }

    /**
     * Третий приоритет
     * Вычисление подвыражения, содержащего возведение в степень
     * Если в строке не одно возведение в степень, то метод вызывается рекурсивно
     */
    public void exponent() {

        int[] CharsPosition = new int[arraySizeCalculation(calculateString.toString()) + 2]; // массив для хранения позиции знаков в выражении/подвыражении

        int a, b; // переменные для операндов
        Double c = null; // переменная для результата вычисления двух операндов

        for (int i = 0; i < calculateString.length(); i++) {

            if (calculateString.charAt(i) == '^') {

                CharsPosition[0] = 0;

                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')') ||
                            (calculateString.charAt(j) == '^')) {

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

                a = Integer.parseInt(calculateString.substring(startPosition, i).toString(), 16);
                b = Integer.parseInt(calculateString.substring(i + 1, finishPosition).toString(), 16);

                System.out.println(ClassOfConstant.PARSINGNUMBER + a);
                System.out.println(ClassOfConstant.PARSINGSIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSINGNUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                c = Math.pow(a, b);

                System.out.println(ClassOfConstant.RESUALTSUBEXPRESSION + c.intValue());
                calculateString.insert(startPosition, Integer.toHexString(c.intValue()));
                System.out.println(ClassOfConstant.STRINGWITHRESUALT + calculateString);
                break;
            }
        }

        for (int i = 0; i < calculateString.length(); i++) {
            if (calculateString.charAt(i) == '^') {
                exponent();
            }
        }
    }

    /**
     * Четвертый приоритет
     * Вычисление подвыражения, содержащего деление и/или умножение
     * Если в строке есть еще операции деления и/или умножения, то метод вызывается рекурсивно
     */
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

                a = Integer.parseInt(calculateString.substring(startPosition, i).toString(), 16);
                b = Integer.parseInt(calculateString.substring(i + 1, finishPosition).toString(), 16);

                System.out.println(ClassOfConstant.PARSINGNUMBER + a);
                System.out.println(ClassOfConstant.PARSINGSIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSINGNUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                c = a * b;

                System.out.println(ClassOfConstant.RESUALTSUBEXPRESSION + c);
                calculateString.insert(startPosition, Integer.toHexString(c));
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

                a = Integer.parseInt(calculateString.substring(startPosition, i).toString(), 16);
                b = Integer.parseInt(calculateString.substring(i + 1, finishPosition).toString(), 16);

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
                calculateString.insert(startPosition, Integer.toHexString(c));
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

    /**
     * Пятый приоритет
     * Вычисление подвыражения, содержащего сложение и/или умножение, с учетом знаковой переменной
     * Если в строке есть еще операции сложения и/или умножения, то метод вызывается рекурсивно
     */
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

                a = Integer.parseInt(calculateString.substring(startPosition, i).toString(), 16);
                b = Integer.parseInt(calculateString.substring(i + 1, finishPosition).toString(), 16);

                System.out.println(ClassOfConstant.PARSINGNUMBER + a);
                System.out.println(ClassOfConstant.PARSINGSIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSINGNUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                c = a + b;

                System.out.println(ClassOfConstant.RESUALTSUBEXPRESSION + c);
                calculateString.insert(startPosition, Integer.toHexString(c));
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

                a = Integer.parseInt(calculateString.substring(startPosition, i).toString(), 16);
                b = Integer.parseInt(calculateString.substring(i + 1, finishPosition).toString(), 16);

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
                calculateString.insert(startPosition, Integer.toHexString(c));
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

                a = Integer.parseInt(calculateString.substring(startPosition, i).toString(), 16);
                b = Integer.parseInt(calculateString.substring(i + 1, finishPosition).toString(), 16);

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
                calculateString.insert(startPosition, Integer.toHexString(c));
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

                a = Integer.parseInt(calculateString.substring(startPosition, i).toString(), 16);
                b = Integer.parseInt(calculateString.substring(i + 1, finishPosition).toString(), 16);

                System.out.println(ClassOfConstant.PARSINGNUMBER + a);
                System.out.println(ClassOfConstant.PARSINGSIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSINGNUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                c = a + b;

                System.out.println(ClassOfConstant.RESUALTSUBEXPRESSION + c);
                calculateString.insert(startPosition, Integer.toHexString(c));
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

    /**
     * Запись в строку очередного подвыражения и вызов методов для расчета в порядки приоритета
     * @param input - выражения/подвыражения
     * @return calculateString - результат выражения/подвыражения
     */
    public StringBuilder calculation(String input) {

        calculateString.delete(0, calculateString.length());
        calculateString.append(input);

        // первый приоритет - скобки
        brackets();

        // второй приоритет минус перед выражением
        minuser();

        // третий приоритет возведение в степень
        exponent();

        // четвернтый приоритет умножения/деление
        multdiv();

        // пятый приоритет сложение/вычитание
        addsub();

        return calculateString;
    }
}