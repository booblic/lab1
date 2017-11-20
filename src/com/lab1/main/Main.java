package com.lab1.main;

import com.lab1.menu.Menu;

/**
 * Класс с методом main для запуска программы
 * @author Кирилл
 * @version 1.0
 */
public class Main {

    /**
     * Метод с которого начиается выполнение программы
     * @param args - агрумент из командной строки
     */
    public static void main(String[] args) {

        Menu menu = new Menu();

        menu.run();
    }
}
