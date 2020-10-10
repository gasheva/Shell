package ru.gasheva.mainform;

public interface ControlInterface {
    void add();
    void edit();
    void remove();
    void redraw();  //перерисовка формы
    void rowReorder(int from, int to);
    void tableSelectionValueChanged();
}
