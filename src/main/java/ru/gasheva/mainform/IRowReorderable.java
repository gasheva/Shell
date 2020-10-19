package ru.gasheva.mainform;

import javax.swing.*;

public interface IRowReorderable {
    void rowReorder(int from, int to, JTable table);
}
