package ru.gasheva.addrule;

import ru.gasheva.mainform.IRowReorderable;
import ru.gasheva.mainform.TableModel;
import ru.gasheva.mainform.TableRowTransferHandler;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Rule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateRuleForm extends JDialog implements IRowReorderable {
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
    private JScrollPane scpConditions;
    private JScrollPane scpConclusion;
    private ManagerRuleAbstractClass control;
    private VariableModel variableModel;
    private  DomainModel domainModel;
    private RuleModel ruleModel;
    private TableModel myModelConclusions;
    private TableModel myModelConditions;

    public CreateRuleForm(VariableModel variableModel, DomainModel domainModel, RuleModel ruleModel, ManagerRuleAbstractClass control) {
        this.control = control;
        this.variableModel = variableModel;
        this.domainModel = domainModel;
        this.ruleModel = ruleModel;
    }
    public void createView(){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        createControls();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void createView(Rule oldRule){
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        createControls();
        tfName.setText(oldRule.getName());
        for(int i=0; i<oldRule.conditionsSize();i++){
            myModelConditions.addRow(new Object[]{oldRule.getCondition(i)});
        }
        for(int i=0; i<oldRule.conclusionsSize();i++){
            myModelConclusions.addRow(new Object[]{oldRule.getConclusion(i)});
        }

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void createControls(){
        //background color
        scpConclusion.getViewport().setBackground(Color.white);
        scpConditions.getViewport().setBackground(Color.white);

        myModelConclusions = new TableModel(new String[]{"Предпосылка"});
        myModelConditions = new TableModel(new String[]{"Заключение"});
        initTable(tblPreposition, myModelConditions);
        initTable(tblConclusion, myModelConclusions);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        btnAddConcl.addActionListener(e->btnAddConclClicked());
        btnAddPrep.addActionListener(e->btnAddPrepClicked());
        btnEditConcl.addActionListener(e->btnEditConclClicked());
        btnEditPrep.addActionListener(e->btnEditPrepClicked());
        btnDeleteConcl.addActionListener(e->btnDeleteConclClicked());
        btnDeletePrep.addActionListener(e->btnDeletePrepClicked());
    }
    //вызывается один раз при создании формы
    private void initTable(JTable table, TableModel model) {
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //drag & drop
        table.setDragEnabled(true);
        table.setDropMode(DropMode.INSERT_ROWS);
        table.setTransferHandler(new TableRowTransferHandler(table, this));

        table.setModel(model);

        //fonts
        table.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
        table.setForeground(Color.DARK_GRAY);
        table.setFont(new Font("Verdana", Font.PLAIN, 12));

        //sorter
        table.setAutoCreateRowSorter(false);
    }
    private void btnEditPrepClicked(){control.editCondition();}
    private void btnEditConclClicked(){control.editConclusion();}
    private void btnAddConclClicked(){control.addConclusion();}
    private void btnAddPrepClicked(){control.addCondition();}
    private void btnDeleteConclClicked(){control.removeConclusion();}
    private void btnDeletePrepClicked(){control.removeCondition();}

    private void onOK() {
        control.ok();
    }

    private void onCancel() {
        control.cancel();
    }
    public void Dispose(){
        dispose();
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public String getRuleName() {
        return tfName.getText().trim();

    }
    public void showMessage(String msg) {JOptionPane.showMessageDialog(this, msg); }

    public boolean isNameEmpty() {return tfName.getText().trim().isEmpty();}
    public boolean hasConclusions() {return myModelConclusions.getRowCount()>0;}
    public boolean hasConditions() {return myModelConditions.getRowCount()>0;}


    @Override
    public void rowReorder(int from, int to, JTable table) {
        if (table==tblConclusion) control.rowReorderConclusions(from, to);
        if (table==tblPreposition) control.rowReorderConditions(from, to);
    }

    public void addCondition(String condition) {
        myModelConditions.addRow(new Object[]{condition});
    }
    public void addConclusion(String conclusion) {
        myModelConclusions.addRow(new Object[]{conclusion});    //TODO: забавно, но здесь я не назначаю модель для таблицы
    }

    public int getConditionRowIndex() {
        return tblPreposition.getSelectedRow();
    }
    public int getConclusionRowIndex() {
        return tblConclusion.getSelectedRow();
    }

    public boolean isTblConditionSelectRow() {
        return tblPreposition.getSelectedRowCount()>0;
    }
    public boolean isTblConclusionSelectRow() {
        return tblConclusion.getSelectedRowCount()>0;
    }

    public void setTblConditionRow(int id, String[] strings) {
        myModelConditions.setValueAt(strings[0], id, 0);
    }
    public void setTblConclusionRow(int id, String[] strings) {
        myModelConclusions.setValueAt(strings[0], id, 0);
    }

    public void removeRowFromConditions(int id) {
        myModelConditions.removeRow(id);
    }

    public void removeRowFromConclusions(int id) {
        myModelConclusions.removeRow(id);
    }
}
