package ru.gasheva.adddomain;

import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.DomainValue;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class CreateDomainForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tfDomainName;
    private JTable table1;
    private JButton btnDeleteDomain;
    private JTextField tfDomainNewVal;
    private JButton btnAddDomain;
    private JButton btnEditDomain;
    private IDomainControl control;
    private DefaultTableModel myModel;

    
    public CreateDomainForm(IDomainControl control) {
        this.control = control;
    }


    public void createView() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        createControls();

        pack();
        setVisible(true);
    }
    private void createControls(){
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void onOK() {
        // add your code here
        Domain domain = new Domain();
        domain.setName(tfDomainName.getName());

        control.ok();
    }

    private void onCancel() {
        // add your code here if necessary
        control.cancel();
    }
    public void Dispose(){
        dispose();
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    //создаем новый домен
    public Domain getNewDomain() {
        Domain newDomain = new Domain(tfDomainName.getText());
        for(int i=0; i<myModel.getRowCount(); i++){
            newDomain.add(new DomainValue((String)myModel.getValueAt(i, 0)));
        }
        return newDomain;
    }

    public boolean isDomainValuesEmpty() {
        return myModel.getRowCount()==0;
    }
}
