package ru.gasheva.mainform;

import ru.gasheva.controls.MainControl;
import ru.gasheva.models.ModelInterface;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainForm extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JButton btnDelete;
    private JButton btnEdit;
    private JButton btnAdd;
    private JTable tblInfo;
    private JPanel manageRulesPanel;
    private JSplitPane spRules;
    private JScrollPane scpRule;
    private JTextArea tfTop;
    private JTextArea tfBottom;
    private JPanel prepPanel;
    private JPanel conclusionPanel;
    private JSeparator sepRules;
    private JMenuBar mbMain;
    private JMenu fileMenu;
    private JMenu consultMenu;
    private JMenuItem miNew;
    private JMenuItem miOpen;
    private JMenuItem miExit;
    private JMenuItem miSave;
    private JMenuItem miBeginCons;
    private TableModel myModel;
    private MainControl control;

    public MainForm(MainControl control) {
        this.control = control;
    }
    public void createView(){
        setContentPane(mainPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        createJMenuBar();
        //initTable();
        setVisible(true);
    }

    //вызывается один раз при создании формы
    private void initTable() {
        tblInfo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //drag & drop
        tblInfo.setDragEnabled(true);
        tblInfo.setDropMode(DropMode.INSERT_ROWS);
        tblInfo.setTransferHandler(new TableRowTransferHandler(tblInfo));

        //model
        myModel = new TableModel(new String[]{"Правило","Описание"});
        myModel.addRow(new Object[]{"Rule 1","Rulllllllleer"});
        myModel.addRow(new Object[]{"Rule 2","Rullllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll" +
                "lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll" +
                "lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllleer 2"});
        tblInfo.setModel(myModel);

        //column size
        tblInfo.getColumnModel().getColumn(0).setMaxWidth(100);
        tblInfo.getColumnModel().getColumn(0).setPreferredWidth(80);

        //fonts
        tblInfo.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
        tblInfo.setForeground(Color.DARK_GRAY);
        tblInfo.setFont(new Font("Verdana", Font.PLAIN, 12));

        //cell renderer
        WrapTableCellRenderer tableCellRenderer = new WrapTableCellRenderer();
        tblInfo.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        tblInfo.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);

        //sorter
        tblInfo.setAutoCreateRowSorter(false);

        //background color
        scpRule.getViewport().setBackground(Color.white);

        //tblInfo.updateUI();
        System.out.println(tblInfo.getRowCount());
    }

    public void createControls(){
        tabbedPane.add("Переменные", tabbedPane.getTabComponentAt(0));
        tabbedPane.add("Домены", tabbedPane.getTabComponentAt(0));
        initTable();
        spRules.setDividerLocation(0.7);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BtnAddClicked();
            }
        });
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                tabbedPaneChanged();
            }
        });

    }
    private void BtnAddClicked(){
        control.add(tblInfo.getSelectedRow());
    }
    private void tabbedPaneChanged(){
        control.changeControl(tabbedPane.getSelectedIndex());
    }
    private void createJMenuBar(){
        mbMain = new JMenuBar();
        fileMenu = new JMenu("Файл");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        miNew = new JMenuItem("Новый");
        miSave = new JMenuItem("Сохранить");
        miOpen = new JMenuItem("Открыть файл");
        miExit = new JMenuItem("Выход");
        miExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(miNew);
        fileMenu.add(miOpen);
        fileMenu.add(miSave);
        fileMenu.add(miExit);

        consultMenu = new JMenu("Консультация");
        consultMenu.setMnemonic(KeyEvent.VK_C);
        miBeginCons = new JMenuItem("Консультация");
        consultMenu.add(miBeginCons);

        mbMain.add(fileMenu);
        mbMain.add(consultMenu);
        setJMenuBar(mbMain);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        myModel = new TableModel(new String[]{"Правило","Описание"});
        tblInfo = new JTable(myModel);
    }

    public void createModel(String[] columns){
        myModel = new TableModel(columns);
    }
    public void setTableModel(){
        tblInfo.setModel(myModel);
    }
    public void changePrepPanelText(String title){
        Border border = prepPanel.getBorder();
        prepPanel.setBorder(BorderFactory.createTitledBorder(border, title));
    }
    public void changeConclusionPanelText(String title){
        Border border = conclusionPanel.getBorder();
        conclusionPanel.setBorder(BorderFactory.createTitledBorder(border, title));
    }
    public void setPrepPanelVisible(boolean isVisible){
        prepPanel.setVisible(isVisible);
    }
    public void setConclusionPanelVisible(boolean isVisible){
        conclusionPanel.setVisible(isVisible);
    }
    public void Dispose(){
        dispose();
    }
}