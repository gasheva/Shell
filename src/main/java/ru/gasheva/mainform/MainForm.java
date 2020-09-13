package ru.gasheva.mainform;

import ru.gasheva.controls.ControlInterface;

import javax.swing.*;
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
    private ControlInterface control;

    public MainForm() {
    }
    public void createView(){
        setContentPane(mainPanel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        createJMenuBar();
        createTable();
        setVisible(true);
    }

    private void createTable() {
        tblInfo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //drag & drop
        tblInfo.setDragEnabled(true);
        tblInfo.setDropMode(DropMode.INSERT_ROWS);
        tblInfo.setTransferHandler(new TableRowTransferHandler(tblInfo));

        //model
        myModel = new TableModel();
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
        createTable();
        spRules.setDividerLocation(0.7);

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

            }
        });

    }
    private void tabbedPaneChanged(){
        //TODO: enum
        switch (tabbedPane.getSelectedIndex()){
            case 0: control.changeContol(0); break;
            case 1: control.changeContol(1); break;
            case 2: control.changeContol(2); break;
        }
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
        myModel = new TableModel();
        tblInfo = new JTable(myModel);
    }
}