import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MenuFirst extends MenuEntry {

    String line = null; // переменная для хранения выражения
    String numberSystems = null; // переменная для хранения системы счисления

    public MenuFirst(String input) {
        super(input);
    }

    public void action(Calculator calc) {
        System.out.println(calc.start());
    }

    public void run() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print(ClassOfConstant.ENTRYNUMBERSYSTEM);
            numberSystems = reader.readLine();
            System.out.println("Введеная системв счисления: " + numberSystems);
            System.out.print(ClassOfConstant.ENTRYEXPRESSION);
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
            System.out.println(ClassOfConstant.NOBRECKETS);
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
}