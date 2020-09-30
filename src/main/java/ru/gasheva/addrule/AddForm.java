package ru.gasheva.addrule;

import ru.gasheva.controls.ControlInterface;
import ru.gasheva.controls.RuleControl;
import ru.gasheva.models.ModelInterface;

import javax.swing.*;
import java.awt.event.*;

public class AddForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tfName;
    private JTextArea textArea1;
    private JTable tblPreposition;
    private JTable tblConclusion;
    private JButton btnAddPrep;
    private JButton btnEditPrep;
    private JButton btnDeletePrep;
    private JButton btnAddConcl;
    private JButton btnDeleteConcl;
    private JButton btnEditConcl;
    private ModelInterface model;
    private AddRuleControl control;

    public AddForm(AddRuleControl control, ModelInterface model) {
        this.control = control;
        this.model = model;
    }
    public void createView(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        pack();
        setVisible(true);
    }
    private void createContols(){
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


    }

    private void onOK() {
        // add your code here
        //((RuleControl)control).updateModel(new String[]{});
    }

    private void onCancel() {
        // add your code here if necessary
        control.cancel();
    }
    public void Dispose(){
        dispose();
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
