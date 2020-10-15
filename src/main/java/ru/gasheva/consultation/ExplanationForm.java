package ru.gasheva.consultation;

import ru.gasheva.mainform.TableModel;
import ru.gasheva.mainform.WrapTableCellRenderer;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Fact;
import ru.gasheva.models.classes.Variable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExplanationForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTree treeRules;
    private JTable tblVariables;
    private TableModel myModel;
    private ConsultationControl control;
    private RuleModel ruleModel;
    private VariableModel variableModel;
    private DomainModel domainModel;
    private WrapTableCellRenderer tableCellRenderer = new WrapTableCellRenderer();

    public ExplanationForm(ConsultationControl control, RuleModel ruleModel, VariableModel variableModel, DomainModel domainModel) {
        this.ruleModel = ruleModel;
        this.variableModel = variableModel;
        this.domainModel = domainModel;
        this.control = control;
    }

    public void createView(){
        setContentPane(contentPane);
        setModal(true);
        createControls();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void createControls(){
        myModel = new TableModel(new String[]{"Имя", "Значение"});
        initTable(tblVariables, myModel);
        fillTree();
        fillTable();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
    private void fillTable(){
        java.util.List<Fact> facts = control.getWorkingMemory().getAllVariables();
        facts.forEach(x->myModel.addRow(new Object[]{x.getVariable().getName(), x.getDomainValue().getValue()}));
        setTableModel();
    }
    public void setTableModel(){
        tblVariables.setModel(myModel);
        // текст в несколько строк
        tblVariables.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        tblVariables.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);

        //column size
        tblVariables.getColumnModel().getColumn(0).setMaxWidth(100);
        tblVariables.getColumnModel().getColumn(0).setPreferredWidth(80);
    }

    //заполнение дерева
    private void fillTree() {
        ruleModel.getRules().forEach(x-> System.out.println(x.getRuleToString()));
//        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Правила");
//        DefaultMutableTreeNode taskNode = new DefaultMutableTreeNode(tasksDao.GetTask(i).getName());
//        rootNode.add(taskNode);
//        taskNode.add(new DefaultMutableTreeNode(subtask.getName());
//
//        DefaultTreeModel model = (DefaultTreeModel) treeRules.getModel();
//        model.setRoot(rootNode);
    }

}
