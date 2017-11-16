import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Test {

    public static void action(Calculator calc) {
        System.out.println(calc.start());
    }

    public static void main(String[] args) {

        Menu menu = new Menu();

        // добавление пункта меню для ввода выражения с клавиатуры
        menu.addEntry(new MenuEntry("Ввод выражения с клавиатуры.") {
            @Override
            public void run() {

                String line = null; // переменная для хранения выражения
                String numberSystems = null; // переменная для хранения системы счисления

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

                try {
                    System.out.print("Введите систему счисления (2, 8, 10 или 16): ");
                    numberSystems = reader.readLine();
                    System.out.println("Введеная системв счисления: " + numberSystems);
                    System.out.print("Введите выражение: ");
                    line = reader.readLine();
                    System.out.println("Введеная строка: " + line);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int openBracketNumber = 0, closeBracketNumber = 0; // переменные для хранения количества открывающихся и закрывающихся скобок

                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == '(') { openBracketNumber++; }
                    if (line.charAt(j) == ')') { closeBracketNumber++; }
                }

                if(openBracketNumber != closeBracketNumber) {
                    System.out.println("Не хватает скобки!");
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
        });

        // добавление пункта меню для считывания выражения из файла (имя файла передается в качестве параметра командной строки Run->Edit Configuration->Program arguments)
        menu.addEntry(new MenuEntry("Считывание выражения из файла.") {
            @Override
            public void run() {

                String str = null; // переменная для хранения выражения
                String substr = null; // переменная для хранения системы счисления
                int i = 0;

                try(BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]))) {
                    str = bufferedReader.readLine();
                } catch (IOException exc) {
                    System.out.print(exc);
                }

                Scanner scanner = new Scanner(str);

                if (scanner.hasNext()) {
                    i = scanner.nextInt();
                    System.out.println(i);
                    substr = scanner.nextLine().trim();
                    System.out.println(substr);

                } else {
                    System.out.println("String is empty!");
                }

                int openBracketNumber = 0, closeBracketNumber = 0;

                for (int j = 0; j < substr.length(); j++) {
                    if (substr.charAt(j) == '(') { openBracketNumber++; }
                    if (substr.charAt(j) == ')') { closeBracketNumber++; }
                }

                if(openBracketNumber != closeBracketNumber) {
                    System.out.println("Не хватает скобки!");
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
        });
        menu.run();
    }
}
