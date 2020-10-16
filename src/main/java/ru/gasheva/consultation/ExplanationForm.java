package ru.gasheva.consultation;

import ru.gasheva.mainform.TableModel;
import ru.gasheva.mainform.WrapTableCellRenderer;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Fact;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.classes.Variable;

import javax.swing.*;
import javax.swing.tree.*;
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
    private void createControls(){
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
//        System.out.println("ALL RULES:");
//         ruleModel.getRules().forEach(x-> System.out.println(x.getRuleToString()));
//        System.out.println("ALL RULES");

        treeRules.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        Rule r = control.getWorkingMemory().getUsingRule(0);
        String ruleFormat = "ЦЕЛЬ: "+r.getConclusion(0).getVariable().getName()+"\n";
        ruleFormat+=r.getRuleToString().replace("THEN", "\n THEN");
        System.out.println(ruleFormat);

        r = control.getWorkingMemory().getUsingRule(1);
        String ruleFormat2 = "<html>ЦЕЛЬ: "+r.getConclusion(0).getVariable().getName()+"<br>"+ r.getRuleToString().replace("THEN", "<br> THEN")+"</html>";
        System.out.println(ruleFormat2);


        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(ruleFormat);
        DefaultMutableTreeNode taskNode = new DefaultMutableTreeNode(ruleFormat2);

        rootNode.add(taskNode);

        DefaultTreeModel model = (DefaultTreeModel) treeRules.getModel();
        model.setRoot(rootNode);


        treeRules.setCellRenderer( new DefaultTreeCellRenderer(){
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                          boolean leaf, int row, boolean hasFocus) {
                if(value != null ){
                    DefaultMutableTreeNode node =  (DefaultMutableTreeNode)value;
                    if(node.isLeaf()){
                        String animal = (String) ((DefaultMutableTreeNode)value).getUserObject();
                        this.setText(animal);
                        setIcon(leafIcon);
                    }else {

                        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                    }
                }
                return this;
            }

        });
    }

}
