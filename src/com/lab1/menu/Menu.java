package com.lab1.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.lab1.constant.*;

/**
 * Класс для работы с меню
 * @author Кирилл
 * @version 1.0
 */
public class Menu {

    /**
     * Коллекция для хранения пунктов меню
     */
    private List<MenuEntry> entries = new ArrayList<>();

    /**
     * Флаг для выхода из меню
     */
    private boolean isExit = false;

    /**
     * Конструктор создающий пункты меню
     */
    public Menu() {

        entries.add(new MenuEntry("Выход.") {
            public void run() {
                isExit = true;
            }
        });

        addEntry(new MenuFirst("Ввод выражения с клавиатуры."));

        addEntry(new MenuSecond("Считывание выражения из файла."));
    }

    /**
     * Метод отображающий меню в консоле, считывающий номер пункта меню с клавиатуры и запускающий выполнение соответствующего пункта
     */
    public void run() {

        while (!isExit) {

            printMenu();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            try {
                String line = reader.readLine();
                int choice = Integer.parseInt(line);
                MenuEntry entry = entries.get(choice - 1);
                entry.run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод для добавления пункта меню в коллекцию
     * @return объект класса производный от абстактного класса MenuEntry, соответствующий пункту меню
     */
    public Menu addEntry(MenuEntry entry) {

        int index = entries.size() - 1;
        entries.add(index, entry);
        return this;
    }

    /**
     * Метод извлекает из коллекции объекты соответствующие пунктам меню и выводит их заглавие на консоль
     */
    private void printMenu() {

        StringBuffer buffer = new StringBuffer();
        buffer.append("\nКалькулятор:\n");

        for (int i = 0; i < entries.size(); i++) {

            MenuEntry entry = entries.get(i);
            String entryFormatted = String.format(ClassOfConstant.MENU_PATTERN, (i + 1), entry.getTitle());
            buffer.append(entryFormatted);
        }
        System.out.print(buffer.toString());
    }
}
