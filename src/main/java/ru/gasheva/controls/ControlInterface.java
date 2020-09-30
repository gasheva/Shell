package ru.gasheva.controls;

public interface ControlInterface {
    void add(int rowIndex);
    void edit(String id, String[] values);
    void remove(String id);
    void redraw();  //перерисовка формы
}
