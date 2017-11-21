package com.lab1.calculator;

import com.lab1.constant.ClassOfConstant;

/**
 * @author Кирилл
 * @version 1.0
 * Класс для вычисления выражения записанного в десятичной системе счисления
 */
public class Decimal extends Calculator {

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
    public Decimal(String input) {

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
     * @see Decimal#calculation(String)
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
                System.out.println(ClassOfConstant.BRECKETS_EXPRESSION + newСalculateString);

                calculateString.delete(openBracket, closeBracket + 1);
                System.out.println(ClassOfConstant.AFTER_DELETED_BRECKETS_EXPRESSION + calculateString);

                StringBuilder mainString = new StringBuilder(calculateString);
                System.out.println(ClassOfConstant.NEW_STRING_WITHOUT_BRECKETS_EXPRESSION + mainString);

                calculation(newСalculateString);

                mainString.insert(openBracket, calculateString.toString());
                System.out.println(ClassOfConstant.NEW_STRING_WITH_RESUALT_BRECKETS_EXPRESSION + mainString);

                calculateString.delete(0, calculateString.length());
                calculateString.insert(0, mainString.toString());

                System.out.println(ClassOfConstant.STRING_WITH_RESUALT_BRECKETS_EXPRESSION + calculateString);

                brackets();
            }
        }
    }

    /**
     * Второй приоритет
     * Если перед выражением стоит знак минус, то метод менят зачение знаковой переменной
     * @see Decimal#sign
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

        int[] charsPosition = new int[arraySizeCalculation(calculateString.toString()) + 2]; // массив для хранения позиции знаков в выражении/подвыражении

        double a, b; // переменные для операндов
        Double c = null; // переменная для результата вычисления двух операндов

        for (int i = 0; i < calculateString.length(); i++) {

            if (calculateString.charAt(i) == '^') {

                charsPosition[0] = 0;

                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')') ||
                            (calculateString.charAt(j) == '^')) {

                        charsPosition[k] = j + 1;
                        k++;
                    }

                    charsPosition[charsPosition.length - 1] = calculateString.length() + 1;

                }

                for (int k = 0; k < charsPosition.length; k++) {

                    if (charsPosition[k] == i + 1) {
                        startPosition = charsPosition[k - 1];
                        finishPosition = charsPosition[k + 1] - 1;
                    }
                }

                a = Double.parseDouble(calculateString.substring(startPosition, i));
                b = Double.parseDouble(calculateString.substring(i + 1, finishPosition));

                System.out.println(ClassOfConstant.PARSING_NUMBER + a);
                System.out.println(ClassOfConstant.PARSING_SIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSING_NUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                if (!sign) {
                    c = Math.pow(a, -b);
                } else {
                    c = Math.pow(a, b);
                }

                System.out.println(ClassOfConstant.RESUALT_SUBEXPRESSION + c);
                calculateString.insert(startPosition, c.toString());
                System.out.println(ClassOfConstant.STRING_WITH_RESUALT + calculateString);
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
    public void multDiv() {

        int[] charsPosition = new int[arraySizeCalculation(calculateString.toString()) + 2]; // массив для хранения позиции знаков в выражении/подвыражении

        double a, b; // переменные для операндов
        Double c = null; // переменная для результата вычисления двух операндов

        // перебираем строку посимвольно
        for (int i = 0; i < calculateString.length(); i++) {

            // если встретили знак умножения
            if (calculateString.charAt(i) == '*') {

                // первый жлемент массива обозанчаеи начало строки, поэтому он всегода равен 0
                charsPosition[0] = 0;

                // перебираем строку для нахождения позиций символов, k - номер символа в строке
                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    // если находим символ
                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')')) {

                        charsPosition[k] = j + 1; // запоминаем его порядковый номер и место в строке на котором он находится
                        k++;
                    }

                    charsPosition[charsPosition.length - 1] = calculateString.length() + 1; // если встретили число, то последнему элементу массива присваиваем размер строки +1. Единицу прибавляем, чтобы решить проблему, возникающую в случае, если после подвыражения не последнее

                }

                // перебираем полученный массив
                for (int k = 0; k < charsPosition.length; k++) {

                    // если нашли символ соответствующий умножению
                    if (charsPosition[k] == i + 1) {
                        startPosition = charsPosition[k - 1]; // то подвыражение начинается от предыдущего символа
                        finishPosition = charsPosition[k + 1] - 1; // до следующего символа - 1, так как строку мы перебирали с 0
                    }
                }

                a = Double.parseDouble(calculateString.substring(startPosition, i));
                b = Double.parseDouble(calculateString.substring(i + 1, finishPosition));

                System.out.println(ClassOfConstant.PARSING_NUMBER + a);
                System.out.println(ClassOfConstant.PARSING_SIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSING_NUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                c = a * b;

                System.out.println(ClassOfConstant.RESUALT_SUBEXPRESSION + c);
                calculateString.insert(startPosition, c.toString());
                System.out.println(ClassOfConstant.STRING_WITH_RESUALT + calculateString);
                break;
            }

            if (calculateString.charAt(i) == '/') {


                charsPosition[0] = 0;

                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')')) {

                        charsPosition[k] = j + 1;
                        k++;
                    }

                    charsPosition[charsPosition.length - 1] = calculateString.length() + 1;

                }
                for (int k = 0; k < charsPosition.length; k++) {

                    if (charsPosition[k] == i + 1) {
                        startPosition = charsPosition[k - 1];
                        finishPosition = charsPosition[k + 1] - 1;
                    }
                }

                a = Double.parseDouble(calculateString.substring(startPosition, i));
                b = Double.parseDouble(calculateString.substring(i + 1, finishPosition));

                System.out.println(ClassOfConstant.PARSING_NUMBER + a);
                System.out.println(ClassOfConstant.PARSING_SIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSING_NUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                if (b == 0) {
                    System.out.println(ClassOfConstant.DIVIZION_BY_ZERO);
                    return;
                }

                c = a / b;

                System.out.println(ClassOfConstant.RESUALT_SUBEXPRESSION + c);
                calculateString.insert(startPosition, c.toString());
                System.out.println(ClassOfConstant.STRING_WITH_RESUALT + calculateString);
                break;
            }
        }

        for (int i = 0; i < calculateString.length(); i++) {
            if ((calculateString.charAt(i) == '*') || (calculateString.charAt(i) == '/')) {
                multDiv();
            }
        }
    }

    /**
     * Пятый приоритет
     * Вычисление подвыражения, содержащего сложение и/или умножение, с учетом знаковой переменной
     * Если в строке есть еще операции сложения и/или умножения, то метод вызывается рекурсивно
     */
    public void addSub() {

        int[] charsPosition = new int[arraySizeCalculation(calculateString.toString()) + 2]; // массив для хранения позиции знаков в выражении/подвыражении

        double a, b; // переменные для операндов
        Double c = null; // переменная для результата вычисления двух операндов

        for (int i = 0; i < calculateString.length(); i++) {

            if ((calculateString.charAt(i) == '+') && sign) {

                charsPosition[0] = 0;

                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')')) {

                        charsPosition[k] = j + 1;
                        k++;
                    }

                    charsPosition[charsPosition.length - 1] = calculateString.length() + 1;

                }
                for (int k = 0; k < charsPosition.length; k++) {

                    if (charsPosition[k] == i + 1) {
                        startPosition = charsPosition[k - 1];
                        finishPosition = charsPosition[k + 1] - 1;
                    }
                }

                a = Double.parseDouble(calculateString.substring(startPosition, i));
                b = Double.parseDouble(calculateString.substring(i + 1, finishPosition));

                System.out.println(ClassOfConstant.PARSING_NUMBER + a);
                System.out.println(ClassOfConstant.PARSING_SIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSING_NUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                c = a + b;

                System.out.println(ClassOfConstant.RESUALT_SUBEXPRESSION + c);
                calculateString.insert(startPosition, c.toString());
                System.out.println(ClassOfConstant.STRING_WITH_RESUALT + calculateString);
                break;

            } else if ((calculateString.charAt(i) == '+') && !sign) {

                charsPosition[0] = 0;

                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')')) {

                        charsPosition[k] = j + 1;
                        k++;
                    }

                    charsPosition[charsPosition.length - 1] = calculateString.length() + 1;

                }
                for (int k = 0; k < charsPosition.length; k++) {

                    if (charsPosition[k] == i + 1) {
                        startPosition = charsPosition[k - 1];
                        finishPosition = charsPosition[k + 1] - 1;
                    }
                }

                a = Double.parseDouble(calculateString.substring(startPosition, i));
                b = Double.parseDouble(calculateString.substring(i + 1, finishPosition));

                System.out.println(ClassOfConstant.PARSING_NUMBER + a);
                System.out.println(ClassOfConstant.PARSING_SIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSING_NUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                if (a <= b) {
                    c = b - a;
                } else {
                    c = a - b;
                    sign = true;
                }

                System.out.println(ClassOfConstant.RESUALT_SUBEXPRESSION + c);
                calculateString.insert(startPosition, c.toString());
                System.out.println(ClassOfConstant.STRING_WITH_RESUALT + calculateString);
                break;
            }

            if ((calculateString.charAt(i) == '-') && sign) {

                charsPosition[0] = 0;

                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')')) {

                        charsPosition[k] = j + 1;
                        k++;
                    }

                    charsPosition[charsPosition.length - 1] = calculateString.length() + 1;

                }
                for (int k = 0; k < charsPosition.length; k++) {

                    if (charsPosition[k] == i + 1) {
                        startPosition = charsPosition[k - 1];
                        finishPosition = charsPosition[k + 1] - 1;
                    }
                }


                a = Double.parseDouble(calculateString.substring(startPosition, i));
                b = Double.parseDouble(calculateString.substring(i + 1, finishPosition));

                System.out.println(ClassOfConstant.PARSING_NUMBER + a);
                System.out.println(ClassOfConstant.PARSING_SIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSING_NUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                if (a >= b) {
                    c = a - b;
                } else if (b > a) {
                    c = b - a;
                    sign = false;
                }

                System.out.println(ClassOfConstant.RESUALT_SUBEXPRESSION + c);
                calculateString.insert(startPosition, c.toString());
                System.out.println(ClassOfConstant.STRING_WITH_RESUALT + calculateString);
                break;

            } else if ((calculateString.charAt(i) == '-') && !sign) {

                charsPosition[0] = 0;

                for (int j = 0, k = 1; j < calculateString.length() - 1; j++) {

                    if ((calculateString.charAt(j) == '+') || (calculateString.charAt(j) == '-') ||
                            (calculateString.charAt(j) == '*') || (calculateString.charAt(j) == '/') ||
                            (calculateString.charAt(j) == '(') || (calculateString.charAt(j) == ')')) {

                        charsPosition[k] = j + 1;
                        k++;
                    }

                    charsPosition[charsPosition.length - 1] = calculateString.length() + 1;

                }
                for (int k = 0; k < charsPosition.length; k++) {

                    if (charsPosition[k] == i + 1) {
                        startPosition = charsPosition[k - 1];
                        finishPosition = charsPosition[k + 1] - 1;
                    }
                }

                a = Double.parseDouble(calculateString.substring(startPosition, i));
                b = Double.parseDouble(calculateString.substring(i + 1, finishPosition));

                System.out.println(ClassOfConstant.PARSING_NUMBER + a);
                System.out.println(ClassOfConstant.PARSING_SIGN + calculateString.charAt(i));
                System.out.println(ClassOfConstant.PARSING_NUMBER + b);

                calculateString.delete(startPosition, finishPosition);

                c = a + b;

                System.out.println(ClassOfConstant.RESUALT_SUBEXPRESSION + c);
                calculateString.insert(startPosition, c.toString());
                System.out.println(ClassOfConstant.STRING_WITH_RESUALT + calculateString);
                break;
            }
        }

        for (int i = 0; i < calculateString.length(); i++) {
            if ((calculateString.charAt(i) == '+') || (calculateString.charAt(i) == '-')) {
                addSub();
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

        try {
            // первый приоритет - скобки
            brackets();

            // второй приоритет минус перед выражением
            minuser();

            // третий приоритет возведение в степень
            exponent();

            // четвернтый приоритет умножения/деление
            multDiv();

            // пятый приоритет сложение/вычитание
            addSub();
        } catch (NumberFormatException exp) {
            return new StringBuilder(ClassOfConstant.WRONG_EXPRESSION);
        }

        return calculateString;
    }
}