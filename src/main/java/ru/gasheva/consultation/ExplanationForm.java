package ru.gasheva.consultation;

import javafx.util.Pair;
import ru.gasheva.mainform.TableModel;
import ru.gasheva.mainform.WrapTableCellRenderer;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.RuleModel;
import ru.gasheva.models.VariableModel;
import ru.gasheva.models.classes.Fact;
import ru.gasheva.models.classes.Rule;
import ru.gasheva.models.classes.Variable;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.basic.BasicTreeUI;
import javax.swing.text.Position;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ExplanationForm extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTree treeRules;
    private JTable tblVariables;
    private JScrollPane spTree;
    private JLabel lblExpandAll;
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
        treeRules.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        fillTree();
        fillTable();

        lblExpandAll.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                expandNodes();
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

    DefaultMutableTreeNode goodNode=null;
    private void getNode(int indexInTree, DefaultMutableTreeNode node, int indexSumm){
        if (goodNode!=null) return;
        for (int i=0; i<node.getChildCount(); i++){
            DefaultMutableTreeNode curNode = (DefaultMutableTreeNode)node.getChildAt(i);
            if (indexSumm + curNode.getChildCount() + 1 >=indexInTree)  {    //где-то здесь нужный узел
                goodNode = (DefaultMutableTreeNode)curNode.getChildAt(indexInTree-indexSumm-1);
            }
            else{
                getNode(indexInTree, curNode, indexSumm+node.getChildCount()+1);
            }
        }
    }
    //заполнение дерева
    private void fillTree() {
        System.out.println("ALL RULES:");
        control.getWorkingMemory().getUsingRules().forEach(x-> System.out.println(x.getValue()+" "+x.getKey().getRuleToString()));
        System.out.println("ALL RULES");

        treeRules.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        List<Pair<Rule, String>> rules = control.getWorkingMemory().getUsingRules();
        //String ruleFormat = "<html>ЦЕЛЬ: "+rules.get(0).getKey().getConclusion(0).getVariable().getName()+"<br>"+ rules.get(0).getKey().getRuleToString().replace("THEN", "<br> THEN")+"</html>";
        String ruleFormat = "<html><b>"+rules.get(rules.size()-1).getKey().getName()+"</b><br>"+ rules.get(rules.size()-1).getKey().getRuleToString().replace("THEN", "<br> THEN")+"</html>";

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(ruleFormat);

        DefaultTreeModel model = (DefaultTreeModel) treeRules.getModel();
        model.setRoot(rootNode);

        for (int i=rules.size()-2;i>=0; i--){
            Rule r = rules.get(i).getKey();
            //DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(r.getName());
            //ruleFormat = "<html>ЦЕЛЬ: "+r.getConclusion(0).getVariable().getName()+"<br>"+ r.getRuleToString().replace("THEN", "<br> THEN")+"</html>";
            ruleFormat = "<html><b>"+r.getName()+"</b><br>"+ r.getRuleToString().replace("THEN", "<br> THEN")+"</html>";
            expandNodes();

            String prefix = "<html><b>"+rules.get(i).getValue();
            TreePath path = treeRules.getNextMatch(prefix, 0, Position.Bias.Forward);
            if (path!=null)System.out.println(path.toString());
            else System.out.println("path is null");
            if (path!=null)((DefaultMutableTreeNode)path.getLastPathComponent()).add(new DefaultMutableTreeNode(ruleFormat));
        }
        treeRules.updateUI();

        //System.out.println(((DefaultMutableTreeNode)path.getLastPathComponent()).getUserObject().toString());


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
//                    Rule r = (Rule) ((DefaultMutableTreeNode)value).getUserObject();
//                    String ruleFormat = "<html>ЦЕЛЬ: "+r.getConclusion(0).getVariable().getName()+"<br>"+ r.getRuleToString().replace("THEN", "<br> THEN")+"</html>";
//                    this.setText(ruleFormat);
                    String r = (String) ((DefaultMutableTreeNode)value).getUserObject();
                    this.setText(r);
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
    }
}
