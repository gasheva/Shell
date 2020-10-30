package ru.gasheva.consultation;

import javafx.util.Pair;
import ru.gasheva.mainform.TableModel;
import ru.gasheva.mainform.WrapTableCellRenderer;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.WorkingMemory;
import ru.gasheva.models.classes.Fact;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.classes.VarType;
import ru.gasheva.models.classes.Variable;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.Position;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExplanationForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTree treeRules;
    private JTable tblVariables;
    private JScrollPane spTree;
    private JLabel lblExpandAll;
    private JTextArea tfExplanation;
    private JSplitPane spHorizontal;
    private JScrollPane tblScroll;
    private TableModel myModel;
    private ConsultationControl control;
    private RuleModel ruleModel;
    private VariableModel variableModel;
    private DomainModel domainModel;
    private WrapTableCellRenderer tableCellRenderer = new WrapTableCellRenderer();
    private Set<Integer> coloredRows = new HashSet();
    private int coloredRowTarget;
    private boolean isCollapsed = true;

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

        setSize(600, 600);
        spHorizontal.setDividerLocation(0.8);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private void createControls(){
        myModel = new TableModel(new String[]{"Имя", "Значение"});
        initTable(tblVariables, myModel);
        treeRules.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        fillTree();
        fillTable();
        tblScroll.getViewport().setBackground(Color.white);
        tfExplanation.setEditable(false);
        tfExplanation.setLineWrap(true);
        tfExplanation.setWrapStyleWord(true);

        treeRules.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                if (treeRules.getSelectionCount()>0){
                    TreePath path = treeRules.getSelectionPath();
                    if (path==null) return;

                    Rule selectedRule = (Rule)((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
                    tfExplanation.setText(selectedRule.getExplanation());

                    coloredRows.clear();
                    for(int j=0; j<tblVariables.getRowCount(); j++){
                        for(int i=0; i<selectedRule.conditionsSize(); i++)
                            if (selectedRule.getCondition(i).getVariable().getName().equals(tblVariables.getValueAt(j, 0))){
                                coloredRows.add(j);
                                break;
                            }
                        if (tblVariables.getValueAt(j, 0).equals(selectedRule.getConclusion(0).getVariable().getName()))
                            coloredRowTarget = j;
                    }

                    tblVariables.updateUI();
                }
            }
        });

        lblExpandAll.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (isCollapsed) {
                    isCollapsed=false;
                    lblExpandAll.setText("<html><u>(свернуть все)</u></html>");
                    expandNodes();
                }
                else{
                    isCollapsed=true;
                    lblExpandAll.setText("<html><u>(раскрыть все)</u></html>");
                    collapseNodes();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
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
    private void fillTree1() {
        System.out.println("ALL RULES:");
        control.getWorkingMemory().getUsingRules().forEach(x-> System.out.println(x.getValue()+" "+x.getKey().getRuleToString()));
        System.out.println("ALL RULES");

        treeRules.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        List<Pair<Rule, String>> rules = control.getWorkingMemory().getUsingRules();
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rules.get(rules.size()-1).getKey());

        DefaultTreeModel model = (DefaultTreeModel) treeRules.getModel();
        model.setRoot(rootNode);

        for (int i=rules.size()-2;i>=0; i--){
            Rule r = rules.get(i).getKey();
            expandNodes();
            String tmp = rules.get(i).getValue();

            String prefix = control.getWorkingMemory().getRule(rules.get(i).getValue()).toString();
            TreePath path = treeRules.getNextMatch(prefix, 0, Position.Bias.Forward);
            if (path!=null)System.out.println(path.toString());
            else System.out.println("path is null");
            if (path!=null)((DefaultMutableTreeNode)path.getLastPathComponent()).add(new DefaultMutableTreeNode(r));
            treeRules.updateUI();
        }
        treeRules.updateUI();

        treeRules.setCellRenderer( new DefaultTreeCellRenderer(){
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                          boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                if(value != null ){
                    DefaultMutableTreeNode node =  (DefaultMutableTreeNode)value;
                    if(node.isLeaf()){
                        setIcon(leafIcon);
                    }
                    Rule r = (Rule) ((DefaultMutableTreeNode)value).getUserObject();
                    String ruleFormat = "<html><b>"+r.getName()+"</b><br>"+ r.getRuleToString().replace("THEN", "<br> THEN")+"</html>";
                    this.setText(ruleFormat);
//                    String r = (String) ((DefaultMutableTreeNode)value).getUserObject();
//                    this.setText(r);
                }
                return this;
            }

        });
        collapseNodes();
    }

    private void fillTree() {
        System.out.println("ALL RULES:");
        control.getWorkingMemory().getUsingRules().forEach(x-> System.out.println(x.getValue()+" "+x.getKey().getRuleToString()));
        System.out.println("ALL RULES");

        treeRules.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        List<Pair<Rule, String>> rules = control.getWorkingMemory().getUsingRules();
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rules.get(rules.size()-1).getKey());

        DefaultTreeModel model = (DefaultTreeModel) treeRules.getModel();
        model.setRoot(rootNode);
        for (int i=rules.size()-1;i>=0; i--){
            expandNodes();
            Rule r = rules.get(i).getKey();

            DefaultMutableTreeNode curNode=null;
            // ищем подправила
            for(int j=r.conditionsSize()-1; j>=0; j--){
                Variable v = r.getCondition(j).getVariable();
                if (v.getVarType()== VarType.RESOLVE) {
                    for (int k = i - 1; k >= 0; k--) {
                        if (rules.get(k).getKey().getConclusion(0).getVariable().getName().equals(v.getName())) {
                            if(curNode==null) {
                                // ищем узел с текущим правилом
                                String prefix = r.toString();
                                TreePath path = treeRules.getNextMatch(prefix, 0, Position.Bias.Forward);
                                if (path != null) System.out.println(path.toString());
                                else System.out.println("path is null");
                                curNode = ((DefaultMutableTreeNode) path.getLastPathComponent());
                            }
                            // добавляем к текущему узлу дочерний
                            curNode.add(new DefaultMutableTreeNode(rules.get(k).getKey()));
                            treeRules.updateUI();
                            break;  //переходим к следующей переменной
                        }
                    }
                }
            }
        }
        treeRules.updateUI();

        treeRules.setCellRenderer( new DefaultTreeCellRenderer(){
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
                                                          boolean leaf, int row, boolean hasFocus) {
                super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
                if(value != null ){
                    DefaultMutableTreeNode node =  (DefaultMutableTreeNode)value;
                    if(node.isLeaf()){
                        setIcon(leafIcon);
                    }
                    Rule r = (Rule) ((DefaultMutableTreeNode)value).getUserObject();
                    String ruleFormat = "<html><b>"+r.getName()+"</b><br>"+ r.getRuleToString().replace("THEN", "<br> THEN")+"</html>";
                    this.setText(ruleFormat);
//                    String r = (String) ((DefaultMutableTreeNode)value).getUserObject();
//                    this.setText(r);
                }
                return this;
            }

        });
        collapseNodes();
    }
    private void expandNodes(){
        for(int i=0; i<treeRules.getRowCount(); i++){
            treeRules.expandRow(i);
        }
    }
    private void collapseNodes(){
        for(int i=0; i<treeRules.getRowCount(); i++){
            treeRules.collapseRow(i);
        }
    }

    private void createUIComponents() {
        lblExpandAll = new JLabel("<html><u>(раскрыть все)</u></html>");
        lblExpandAll.setForeground(Color.blue);
        lblExpandAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        tblVariables = new JTable( myModel )
        {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
            {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row))
                    if (!coloredRows.isEmpty()) {
                        c.setBackground(coloredRows.contains(row) ? Color.PINK : getBackground());
                        if (row == coloredRowTarget)
                            c.setBackground(Color.yellow);
                    }

                return c;
            }
        };
    }
}
