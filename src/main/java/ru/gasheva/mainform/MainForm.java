package ru.gasheva.mainform;

import ru.gasheva.models.ModelInterface;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;

public class MainForm extends JFrame implements IRowReorderable{
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
    private JLabel lblPrep;
    private JLabel lblConcl;
    private JPanel explanationPanel;
    private JTextArea tfExplanation;
    private JSeparator sepRules;
    private JMenuBar mbMain;
    private JMenu fileMenu;
    private JMenu consultMenu;
    private JMenuItem miNew;
    private JMenuItem miOpen;
    private JMenuItem miExit;
    private JMenuItem miSave;
    private JMenuItem miBeginCons;
    private JMenuItem miLoadAutosave;
    private TableModel myModel;
    private MainControl control;
    private WrapTableCellRenderer tableCellRenderer = new WrapTableCellRenderer();

    public MainForm(MainControl control) {
        this.control = control;
    }
    public void createView(){
        setContentPane(mainPanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        createJMenuBar();
        setVisible(true);
    }

    //вызывается один раз при создании формы
    private void initTable() {
        tblInfo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //drag & drop
        tblInfo.setDragEnabled(true);
        tblInfo.setDropMode(DropMode.INSERT_ROWS);
        tblInfo.setTransferHandler(new TableRowTransferHandler(tblInfo, this));

        //model
        myModel = new TableModel(new String[]{"Правило","Описание"});
        setTableModel();

        //fonts
        tblInfo.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
        tblInfo.setForeground(Color.DARK_GRAY);
        tblInfo.setFont(new Font("Verdana", Font.PLAIN, 12));



        //sorter
        tblInfo.setAutoCreateRowSorter(false);

        //background color
        scpRule.getViewport().setBackground(Color.white);
    }

    public void createControls(){
        tabbedPane.add("Переменные", tabbedPane.getTabComponentAt(0));
        tabbedPane.add("Домены", tabbedPane.getTabComponentAt(0));
        initTable();
        tfTop.setEditable(false);
        tfBottom.setEditable(false);
        tfExplanation.setEditable(false);

        tfExplanation.setLineWrap(true);
        tfExplanation.setWrapStyleWord(true);
        //spRules.setLocation(new Point(1, getHeight() - getHeight()/8)); //TODO
        spRules.setDividerLocation(0.7);

        btnAdd.addActionListener(e -> BtnAddClicked());
        btnEdit.addActionListener(e->BtnEditClicked());
        btnDelete.addActionListener(e->BtnDeleteClicked());
        tabbedPane.addChangeListener(e -> tabbedPaneChanged());
        tblInfo.getSelectionModel().addListSelectionListener(e -> TableSelectionValueChanged());
        tblInfo.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount()==2)
                    tblInfo.clearSelection();
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
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        miOpen.addActionListener(e->miOpenClicked());
        miSave.addActionListener(e->miSaveClicked());
        miNew.addActionListener(e->miNewClicked());
        miBeginCons.addActionListener(e->miBeginConsClicked());
        miLoadAutosave.addActionListener(e->miLoadAutosaveClicked());
        mainPanel.registerKeyboardAction(e -> BtnAddClicked(), KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }
    private void miLoadAutosaveClicked(){control.loadData(control.getPathToAutosafe());}
    private void onExit(){control.exit();}
    private void miNewClicked(){control.newES();}
    private void miBeginConsClicked(){control.beginConsultation();}
    private void miSaveClicked(){control.saveInFile();}
    private void miOpenClicked(){control.loadData(getFileToOpen());}
    private void TableSelectionValueChanged(){
        control.tableSelectionValueChanged();
    }
    private void BtnAddClicked(){
        control.add();
    }
    private void BtnEditClicked(){
        control.edit();
    }
    private void BtnDeleteClicked(){
        control.remove();
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
        miLoadAutosave = new JMenuItem("Загрузить автосохранение");
        miExit = new JMenuItem("Выход");
        miExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(miNew);
        fileMenu.add(miOpen);
        fileMenu.add(miSave);
        fileMenu.add(miLoadAutosave);
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
        myModel = new TableModel(new String[]{"Правило","Описание"});
        tblInfo = new JTable(myModel);
    }

    public void createModel(String[] columns){
        myModel = new TableModel(columns);
    }
    public void setTableModel(){
        tblInfo.setModel(myModel);
        // текст в несколько строк
        tblInfo.getColumnModel().getColumn(1).setCellRenderer(tableCellRenderer);
        tblInfo.getColumnModel().getColumn(0).setCellRenderer(tableCellRenderer);

        //column size
        tblInfo.getColumnModel().getColumn(0).setMaxWidth(100);
        tblInfo.getColumnModel().getColumn(0).setPreferredWidth(80);

    }
    public void changePrepPanelText(String title){
        lblPrep.setText(title);
    }
    public void changeConclusionPanelText(String title){
        lblConcl.setText(title);
    }
    public void setPrepPanelVisible(boolean isVisible){
        prepPanel.setVisible(isVisible);
    }
    public void setConclusionPanelVisible(boolean isVisible){
        conclusionPanel.setVisible(isVisible);
    }
    public void Dispose(){
        dispose();
        System.exit(0);
    }

    public String getSelectedRowFirstColumnValue() {
        return (String)tblInfo.getValueAt(tblInfo.getSelectedRow(), 0);
    }

    public boolean isTblInfoSelectRow() {
        return tblInfo.getSelectedRow()!=-1;
    }
    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public int getSelectedRowIndex() {
        return tblInfo.getSelectedRow();
    }

    @Override
    public void rowReorder(int from, int to, JTable table) {
        control.rowReorder(from, to);
    }

    public String getRowFirstColumnValue(int to) {
        return (String)myModel.getValueAt(to, 0);
    }

    public void fillTable(ModelInterface model) {
        for(int i =0; i<model.size(); i++){
            myModel.addRow(model.getValuesForTable(i));
        }

    }
    public void ChangeRowInTable(int selectedRowIndex, String[] strings) {
        for(int i=0; i<myModel.getColumnCount(); i++){
            myModel.setValueAt(strings[i], selectedRowIndex, i);
        }
        setTableModel();
    }
    public void InsertInTable(int selectedRowIndex, String[] strings) {
        myModel.insertRow(selectedRowIndex, strings);
        setTableModel();
    }

    public void AddInTable(String[] strings) {
        myModel.addRow(strings);
        setTableModel();
    }

    public String[] getRowValues(int selectedRowIndex) {
        if (selectedRowIndex==-1) return null;
        String[] values = new String[myModel.getColumnCount()];
        for(int i=0; i<myModel.getColumnCount(); i++)
            values[i] = (String)myModel.getValueAt(selectedRowIndex, i);
        return values;
    }


    public void setTfTopText(String value) {
        tfTop.setText(value);
    }

    public void setTfBottomText(String value) {
        tfBottom.setText(value);
    }

    public void removeRow(int selectedRowIndex) {
        for(int i=0; i<myModel.getRowCount(); i++)
            System.out.println("val = "+myModel.getValueAt(i, 0));
        myModel.removeRow(selectedRowIndex);
        setTableModel();
    }

    public String getFileToOpen() {
        JFileChooser c = new JFileChooser("C:\\Users\\DocGashe\\IdeaProjects\\Shell-master");
        FileFilter filter = new FileNameExtensionFilter(
                ".json", "json");
        c.removeChoosableFileFilter(c.getFileFilter());
        c.addChoosableFileFilter(filter);
        int rVal = c.showOpenDialog(this);
        if (rVal!=JFileChooser.APPROVE_OPTION) return null;

        return c.getSelectedFile().getAbsolutePath();
    }

    public String getFileToWrite() {
        JFileChooser c = new JFileChooser("C:\\Users\\DocGashe\\IdeaProjects\\Shell-master");
        FileFilter filter = new FileNameExtensionFilter(
                ".json", "json");
        c.removeChoosableFileFilter(c.getFileFilter());
        c.addChoosableFileFilter(filter);
        if (c.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
            return c.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    public void setExplanationText(String s) {
        tfExplanation.setText(s);
    }

    public void setExplanationPanelVisible(boolean b) {
        explanationPanel.setVisible(b);
    }

    public boolean doesLoadAutosave(){
        JComponent[] inputs = new JComponent[]{
                new JLabel("Загрузить автосохранение?"),
        };
        String answer=null;
        int result = JOptionPane.showConfirmDialog(this, inputs, "", JOptionPane.PLAIN_MESSAGE);
        if (result==JOptionPane.OK_OPTION){
            return true;
        }
        return false;
    }
}