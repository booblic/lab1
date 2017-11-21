package com.lab1.menu;

import com.lab1.calculator.Binary;
import com.lab1.calculator.Decimal;
import com.lab1.calculator.Hexadecimal;
import com.lab1.calculator.Octal;

public class Singleton {
    private static Binary binary;
    private static Decimal decimal;
    private static Octal octal;
    private static Hexadecimal hexadecimal;

    private Singleton () {}

    public static Binary getBinary(String input) {
        if (binary == null) {
            binary = new Binary(input);
        } else {
            binary.setExpression(input);
        }
        return binary;
    }

    public static Decimal getDecimal(String input) {
        if (decimal == null) {
            decimal = new Decimal(input);
        } else {
            decimal.setExpression(input);
        }
        return decimal;
    }

    public static Octal getOctal(String input) {
        if (octal == null) {
            octal = new Octal(input);
        } else {
            octal.setExpression(input);
        }
        return octal;
    }

    public static Hexadecimal getHexadecimal(String input) {
        if (hexadecimal == null) {
            hexadecimal = new Hexadecimal(input);
        } else {
            hexadecimal.setExpression(input);
        }
        return hexadecimal;
    }
}
