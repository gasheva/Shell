package ru.gasheva.consultation;

import javafx.scene.control.ComboBox;
import ru.gasheva.mainform.IRowReorderable;
import ru.gasheva.mainform.TableModel;
import ru.gasheva.mainform.TableRowTransferHandler;
import ru.gasheva.mainform.WrapTableCellRenderer;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.DomainValue;
import ru.gasheva.models.classes.VarType;
import ru.gasheva.models.classes.Variable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ConsultationForm extends JDialog{
    private JPanel contentPane;
    private JButton btnAnswer;
    private JButton buttonCancel;
    private JTable tblConsultation;
    private JButton btnNewConsultation;
    private JButton btnExplanation;
    private TableModel myModel;
    private ConsultationControl control;
    private RuleModel ruleModel;
    private VariableModel variableModel;
    private DomainModel domainModel;
    private WrapTableCellRenderer tableCellRenderer = new WrapTableCellRenderer();

    public ConsultationForm(ConsultationControl control, RuleModel ruleModel, VariableModel variableModel, DomainModel domainModel) {
        this.ruleModel = ruleModel;
        this.variableModel = variableModel;
        this.domainModel = domainModel;
        this.control = control;
    }

    public String askGlobalTarget(){
        return createDialog("Выберите целевую переменную", variableModel.getVariables().stream().filter(x->x.getVarType() == VarType.RESOLVE).map(Variable::getName).toArray(String[]::new));
    }
    public void createView(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnAnswer);
        createControls();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void createControls(){
        myModel = new TableModel(new String[]{"Вопрос", "Ответ"});
        initTable(tblConsultation, myModel);

        btnNewConsultation.addActionListener(e->btnNewConsultationClicked());
        btnExplanation.addActionListener(e->btnExplanationClicked());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    private void initTable(JTable table, TableModel model) {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setModel(model);

        //fonts
        table.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
        table.setForeground(Color.DARK_GRAY);
        table.setFont(new Font("Verdana", Font.PLAIN, 12));

        //sorter
        table.setAutoCreateRowSorter(false);
    }
    public void setTableModel(){
        tblConsultation.setModel(myModel);
        // текст в несколько строк
        tblConsultation.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        tblConsultation.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);

        //column size
        tblConsultation.getColumnModel().getColumn(0).setMaxWidth(100);
        tblConsultation.getColumnModel().getColumn(0).setPreferredWidth(80);
    }
    private void btnNewConsultationClicked(){control.startNewConsultation();}
    private void btnExplanationClicked(){control.explainAnswer();}
    private void onOK() {
        control.ok();
    }
    private void onCancel() {
        control.cancel();
    }
    public void Dispose(){
        dispose();
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
    public String askVarValue(Variable var){
        Domain d = var.getDomain();
        String[] values = new String[d.domainValuesSize()];
        for(int i=0; i<d.domainValuesSize(); i++){
            values[i]=d.getDomainValue(i).getValue();
        }
        return createDialog(var.getQuestion(), values);
    }
    public String createDialog(String labelText, String[] cbItems){
        JComboBox cb = new JComboBox();
        for(int i=0; i<cbItems.length; i++){
            cb.addItem(cbItems[i]);
        }
        cb.setSelectedItem(0);
        JComponent[] inputs = new JComponent[]{
                new JLabel(labelText),
                cb
        };
        String answer=null;
        int result = JOptionPane.showConfirmDialog(this, inputs, "", JOptionPane.PLAIN_MESSAGE);
        if (result==JOptionPane.OK_OPTION){
            answer = cb.getSelectedItem().toString();
        }
        return answer;
    }
}
