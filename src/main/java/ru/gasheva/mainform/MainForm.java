package ru.gasheva.mainform;

import javax.activation.ActivationDataFlavor;
import javax.activation.DataHandler;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MainForm extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JButton btnDelete;
    private JButton btnEdit;
    private JButton btnAdd;
    private JTable tblRules;
    private JPanel manageRulesPanel;
    private JSplitPane spRules;
    private JScrollPane scpRule;
    private JPanel tbValues;
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
        tblRules.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //drag & drop
        tblRules.setDragEnabled(true);
        tblRules.setDropMode(DropMode.INSERT_ROWS);
        tblRules.setTransferHandler(new TableRowTransferHandler(tblRules));

        //model
        myModel = new TableModel();
        myModel.addRow(new Object[]{"Rule 1","Rulllllllleer"});
        myModel.addRow(new Object[]{"Rule 2","Rullllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll" +
                "lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllll" +
                "lllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllllleer 2"});
        tblRules.setModel(myModel);

        //column size
        tblRules.getColumnModel().getColumn(0).setMaxWidth(100);
        tblRules.getColumnModel().getColumn(0).setPreferredWidth(80);

        //fonts
        tblRules.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
        tblRules.setForeground(Color.DARK_GRAY);
        tblRules.setFont(new Font("Verdana", Font.PLAIN, 12));

        //cell renderer
        WrapTableCellRenderer tableCellRenderer = new WrapTableCellRenderer();
        tblRules.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        tblRules.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);

        //sorter
        tblRules.setAutoCreateRowSorter(false);

        //background color
        scpRule.getViewport().setBackground(Color.white);

        //tblRules.updateUI();
        System.out.println(tblRules.getRowCount());
    }

    public void createControls(){
        createTable();
        spRules.setDividerLocation(0.7);

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
        tblRules = new JTable(myModel);
    }
}