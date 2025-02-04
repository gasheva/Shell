package ru.gasheva.addvariable;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.VarType;
import ru.gasheva.models.classes.Variable;

import javax.swing.*;
import java.awt.event.*;

public class CreateVarForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tfVarName;
    private JLabel lbl;
    private JComboBox cbDomen;
    private JRadioButton rbAsk;
    private JRadioButton rbResolve;
    private JRadioButton rbResolveAsk;
    private JTextArea tfQuestion;
    private JLabel lblQ;
    private JButton btnAddDomain;
    private JPanel questionPanel;
    private  ButtonGroup G;
    private ManagerVariableAbstractClass control;
    private DomainModel domainModel;
    private VariableModel variableModel;

    public CreateVarForm(ManagerVariableAbstractClass control, VariableModel variableModel, DomainModel domainModel){
        this.control = control;
        this.domainModel = domainModel;
        this.variableModel = variableModel;
    }
    public void createView(Variable variable) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        createControls();

        //устанавливаем значения по умолчанию
        tfVarName.setText(variable.getName());
        for(int i=0; i<domainModel.size(); i++){
            cbDomen.addItem(domainModel.getDomain(i).getName());
        }
        cbDomen.setSelectedItem(variable.getDomain().getName());
        switch (variable.getVarType()){
            case ASK: rbAsk.setSelected(true);break;
            case RESOLVE: rbResolve.setSelected(true);break;
            case ASK_RESOLVE: rbResolveAsk.setSelected(true);break;
        }
        control.varTypeChanged();
        tfQuestion.setText(variable.getQuestion());

        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public void createView() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        createControls();
        tfVarName.setText("Переменная " + variableModel.size());
        for(int i=0; i<domainModel.size(); i++){
            cbDomen.addItem(domainModel.getDomain(i).getName());
        }
        if (cbDomen.getItemCount()>0)cbDomen.setSelectedIndex(0);
        tfQuestion.setText(tfVarName.getText()+"?");

        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public void createControls(){
        G = new ButtonGroup();
        G.add(rbAsk);
        G.add(rbResolve);
        G.add(rbResolveAsk);
        rbAsk.setSelected(true);
        rbResolveAsk.setSelected(false);
        rbResolve.setSelected(false);


        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
        btnAddDomain.addActionListener(e->BtnAddDomainClicked());
        rbAsk.addActionListener(e->rbButtonSelectionChanged());
        rbResolve.addActionListener(e->rbButtonSelectionChanged());
        rbResolveAsk.addActionListener(e->rbButtonSelectionChanged());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        contentPane.registerKeyboardAction(e -> BtnAddDomainClicked(), KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


    }
    private void rbButtonSelectionChanged(){control.varTypeChanged();}
    private void BtnAddDomainClicked(){control.addDomain();}
    private void onOK() {control.ok();}
    private void onCancel() {control.cancel();}
    public void Dispose(){dispose();}
    public void showMessage(String msg) {JOptionPane.showMessageDialog(this, msg); }

    public boolean isTfVariableNameEmpty() { return tfVarName.getText().trim().isEmpty();}
    public boolean isCbDomainItemSelected(){return cbDomen.getSelectedIndex()!=-1;}
    public String getTfQuestion(){return tfQuestion.getText().trim();}

    public VarType getSelectedType(){
        if (rbAsk.isSelected()) return VarType.ASK;
        if (rbResolve.isSelected()) return VarType.RESOLVE;
        if (rbResolveAsk.isSelected()) return VarType.ASK_RESOLVE;
        return null;
    }
    public Variable getNewVariable() {
        Variable newVariable = new Variable(tfVarName.getText().trim());
        newVariable.setDomain(domainModel.getDomain((String)cbDomen.getSelectedItem()));
        newVariable.setVarType(rbAsk.isSelected()? VarType.ASK:rbResolve.isSelected()?VarType.RESOLVE:VarType.ASK_RESOLVE);
        newVariable.setQuestion(questionPanel.isVisible()?tfQuestion.getText().trim():"");
        return newVariable;
    }

    public void cbDomainsAddItem(String name) {
        cbDomen.addItem(name);
        cbDomen.setSelectedItem(name);
    }
    public void isQuestionPanelVisible(boolean isVisible){
        questionPanel.setVisible(isVisible);
    }
}
