import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Test {

    public static void action(Calculator calc) {
        calc.start();
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.addEntry(new MenuEntry("Ввод выражения с клавиатуры.") {
            @Override
            public void run() {
                String substr = null;
                String line = null;
                int i = 0;
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                try {
                    line = reader.readLine();
                    System.out.println("Введеная строка: " + line);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scanner scanner = new Scanner(line);

                if (scanner.hasNext()) {
                    i = scanner.nextInt();
                    System.out.println(i);
                    substr = scanner.nextLine().trim();
                    System.out.println(substr);

                } else {
                    System.out.println("String is empty!");
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
        menu.addEntry(new MenuEntry("Считывание выражения из файла.") {
            @Override
            public void run() {
                String str = null;
                String substr = null;
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
