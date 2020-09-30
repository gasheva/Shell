package ru.gasheva.consultation;

import javax.swing.*;

public class ExplanationForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTree treeRules;
    private JTable tblVariables;

    public ExplanationForm() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

    public static void main(String[] args) {
        ExplanationForm dialog = new ExplanationForm();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
