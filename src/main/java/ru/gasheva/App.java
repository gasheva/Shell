package ru.gasheva;

import ru.gasheva.mainform.MainForm;

public class App {
    public static void main(String[] args) {
        MainForm mf = new MainForm();
        mf.createView();
        mf.createControls();
    }
}
