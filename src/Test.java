import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Test {

    public static void action(Calculator calc) {
        calc.start();
    }

    public static void main(String[] args) {
        String str = null;
        String substr = null;
        int i = 0;

        // пока что данные считываются из файла file.txt в следующей форме: система счисления "пробел" выражение
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
}
