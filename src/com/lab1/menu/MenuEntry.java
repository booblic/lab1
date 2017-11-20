package com.lab1.menu;

/**
 * Абстрактынй класс - узел для иерархии классов представляющие пункты пеню
 * @author Кирилл
 * @version 1.0
 */
public abstract class MenuEntry {

    /**
     * Поле содержащее заглавие пункта меню
     */
    private String title;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param title - заглавие пункта меню
     */
    public MenuEntry(String title) {
        this.title = title;
    }

    /**
     * Метод возвращающий название пункта меню
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Абстрактный метод для запуска пункта меню
     */
    public abstract void run();
}
