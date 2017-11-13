class Hexadecimal extends Calculator {

    private String expression; // исходная строка
    private StringBuilder result;

    private StringBuilder calculateString = new StringBuilder(); // строка для вычисления выражения
    private int closeBracket, openBracket; // переменные для хранения позиции скобок
    private boolean sign = true; // переменная для хранения знака
    private int finishPosition = 0, startPosition = 0; // переменные для хранения позиции начала и конца подвыражения

    public Hexadecimal(String input) {

        expression = formater(input);

    }

    public void start() {

        result = calculation(expression);

        if (sign == true) {
            String res = Integer.toString(Integer.parseInt(result.toString()), 16);
            System.out.println("Result of term " + expression + " is: " + res);
        } else {
            result.insert(0, "-");
            String res = Integer.toString(Integer.parseInt(result.toString()), 16);
            System.out.println("Result of term " + expression + " is: " + res);
        }
    }

    // уберает лишние пробелы
    private String formater(String input) {
        if (input == null) return null;
        return input.replaceAll(" ", "");
    }

    // вычисляет размер массива для хранения позиции знаков в выражении/подвыражении
    private int arraySizeCalculation(String input) {

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

    private void brackets() {

        for (int i = 0; i < calculateString.length(); i++) {

            if (calculateString.charAt(i) == '(') {

                openBracket = i;

            }
            if (calculateString.charAt(i) == ')') {

                closeBracket = i;

                String newСalculateString;
                newСalculateString = calculateString.substring(openBracket + 1, closeBracket);
                System.out.println("Выражение в скобках: " + newСalculateString);

                calculateString.delete(openBracket, closeBracket + 1);
                System.out.println("После удаления выражения в скобках из основной строки: " + calculateString);

                StringBuilder mainString = new StringBuilder(calculateString);
                System.out.println("Новая строка без выражения в скобках: " + mainString);

                calculation(newСalculateString);

                mainString.insert(openBracket, calculateString.toString());
                System.out.println("Новая строка с результатом выражения в скобоках: " + mainString);

                calculateString.delete(0, calculateString.length());
                calculateString.insert(0, mainString.toString());

                System.out.println("Исходная строка с результатом выражения в скобоках: " + calculateString);

                //calculation(calculateString.toString());
                brackets();
            }
        }
    }

    private void minuser() {
        if (calculateString.charAt(0) == '-') {
            sign = false;
            calculation(calculateString.delete(0, 1).toString());
        }
    }

    private void multdiv() {

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

                calculateString.delete(startPosition, finishPosition);

                c = a * b;

                calculateString.insert(startPosition, c.toString());
                System.out.println("Итог: " + calculateString);
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

                calculateString.delete(startPosition, finishPosition);

                if (b == 0) {
                    System.out.println("Деление на нуль!");
                    return;
                }

                c = a / b;

                System.out.println("Результат: " + c);
                calculateString.insert(startPosition, c.toString());
                System.out.println("Итог: " + calculateString);
                break;
            }
        }

        for (int i = 0; i < calculateString.length(); i++) {
            if ((calculateString.charAt(i) == '*') || (calculateString.charAt(i) == '/')) {
                //calculation(calculateString.toString());
                multdiv();
            }
        }
    }

    private void addsub() {

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

                calculateString.delete(startPosition, finishPosition);

                c = a + b;

                System.out.println("Результат: " + c);
                calculateString.insert(startPosition, c.toString());
                System.out.println("Итог: " + calculateString);
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

                calculateString.delete(startPosition, finishPosition);

                if (a <= b) {
                    c = b - a;
                    sign = true;
                } else {
                    c = a - b;
                }

                System.out.println("Результат: " + c);
                calculateString.insert(startPosition, c.toString());
                System.out.println("Итог: " + calculateString);
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

                calculateString.delete(startPosition, finishPosition);

                if (a >= b) {
                    c = a - b;
                } else if (b > a) {
                    c = b - a;
                    sign = false;
                }

                System.out.println("Результат: " + c);
                calculateString.insert(startPosition, c.toString());
                System.out.println("Итог: " + calculateString);
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

                calculateString.delete(startPosition, finishPosition);

                c = a + b;

                System.out.println("Результат: " + c);
                calculateString.insert(startPosition, c.toString());
                System.out.println("Итог: " + calculateString);
                break;
            }
        }

        for (int i = 0; i < calculateString.length(); i++) {
            if ((calculateString.charAt(i) == '+') || (calculateString.charAt(i) == '-')) {
                //calculation(calculateString.toString());
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