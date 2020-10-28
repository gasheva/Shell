package ru.gasheva.addrule.addfact;

import ru.gasheva.addrule.addfact.AddFactControl;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.DomainValue;
import ru.gasheva.models.classes.Fact;
import ru.gasheva.models.classes.VarType;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;

public class CreateFactForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox cbSelectVariable;
    private JButton btnAddVariable;
    private JComboBox cbSelectDomainValue;
    ManagerFactAbstractClass control;
    DomainModel domainModel;
    VariableModel variableModel;

    public CreateFactForm(DomainModel domainModel, VariableModel variableModel, ManagerFactAbstractClass control) {
        this.domainModel = domainModel;
        this.variableModel = variableModel;
        this.control = control;
    }

    public void createView(String type){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        createControls();

        for(int i=0; i<variableModel.size(); i++){
            if (type.equals("concl")){
                if (variableModel.getVariable(i).getVarType()== VarType.RESOLVE)
                    cbSelectVariable.addItem(variableModel.getVariable(i).getName());
            }
            else
                cbSelectVariable.addItem(variableModel.getVariable(i).getName());
        }
        if (cbSelectVariable.getItemCount()>0) {
            cbSelectVariable.setSelectedIndex(0);
            control.variableSelectionChanged();
        }


        setSize(400, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void createView(Fact oldFact, String type){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        createControls();

        for(int i=0; i<variableModel.size(); i++){
            if (type.equals("concl")){
                if (variableModel.getVariable(i).getVarType()== VarType.RESOLVE)
                    cbSelectVariable.addItem(variableModel.getVariable(i).getName());
            }
            else
                cbSelectVariable.addItem(variableModel.getVariable(i).getName());
        }
        cbSelectVariable.setSelectedItem(oldFact.getVariable().getName());
        control.variableSelectionChanged();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void createControls(){
        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        btnAddVariable.addActionListener(e->BtnAddVariableClicked());
        cbSelectVariable.addActionListener(e->CbSelectVariableSelectionChanged());
    }

    private void CbSelectVariableSelectionChanged(){
        control.variableSelectionChanged();
    }
    private void BtnAddVariableClicked(){control.addVariable();}
    private void onOK() {
        control.ok();
    }

    private void onCancel() {
        control.cancel();
    }
    public void Dispose(){dispose();}
    public void showMessage(String msg) {JOptionPane.showMessageDialog(this, msg); }

    public boolean isCbVariableItemSelected() {
        return cbSelectVariable.getSelectedIndex()!=-1;
    }

    public boolean isCbDomainItemSelected() {
        return cbSelectDomainValue.getSelectedIndex()!=-1;
    }

    public void cbVariableAddItem(String name) {
        cbSelectVariable.addItem(name);
        cbSelectVariable.setSelectedItem(name);
    }

    public void fillCbDomainValue(List<String> domainValues){
        cbSelectDomainValue.removeAllItems();
        domainValues.forEach(x->cbSelectDomainValue.addItem(x));
        cbSelectDomainValue.setSelectedIndex(0);
    }

    public String getVariableSelectedName() {
        return (String)cbSelectVariable.getSelectedItem();
    }

    public String getDomainSelectedName() { return (String)cbSelectDomainValue.getSelectedItem();}
}