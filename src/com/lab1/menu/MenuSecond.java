package com.lab1.menu;

import com.lab1.calculator.*;
import com.lab1.constant.ClassOfConstant;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class MenuSecond extends MenuEntry {

    String str = null; // переменная для хранения выражения
    String substr = null; // переменная для хранения системы счисления
    int i = 0;

    public MenuSecond(String input) {
        super(input);
    }

    public void action(Calculator calc) {
        System.out.println(calc.start());
    }

    public void run() {

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("file.txt"))) {
            str = bufferedReader.readLine();
        } catch (IOException exc) {
            System.out.println(exc);
        }

        Scanner scanner = new Scanner(str);

        if (scanner.hasNext()) {
            i = scanner.nextInt();
            System.out.println(i);
            substr = scanner.nextLine().trim();
            System.out.println(substr);

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
