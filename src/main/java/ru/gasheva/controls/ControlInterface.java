package ru.gasheva.controls;

public interface ControlInterface {
    void add();
    void edit();
    void remove();
    void redraw();  //перерисовка формы
    void rowReorder(int from, int to);
    void tableSelectionValueChanged();
}
