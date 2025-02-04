package ru.gasheva.adddomain;

import ru.gasheva.mainform.IRowReorderable;
import ru.gasheva.mainform.TableModel;
import ru.gasheva.mainform.TableRowTransferHandler;
import ru.gasheva.mainform.WrapTableCellRenderer;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.classes.Domain;
import ru.gasheva.models.classes.DomainValue;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

public class CreateDomainForm extends JDialog implements IRowReorderable {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField tfDomainName;
    private JTable tblDomainValues;
    private JButton btnDeleteDomainValue;
    private JTextField tfDomainValue;
    private JButton btnAddDomainValue;
    private JButton btnEditDomainValue;
    private JScrollPane scpDomainValues;
    private ManagerDomainAbstractClass control;
    private TableModel myModel;
    private DomainModel domainModel;
    
    public CreateDomainForm(ManagerDomainAbstractClass control, DomainModel domainModel) {
        this.domainModel = domainModel;
        this.control = control;
    }

    public void createView(Domain domain) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        createControls();

        //устанавливаем значения по умолчанию
        tfDomainName.setText(domain.getName());
        for (int i=0; i<domain.domainValuesSize(); i++){
            myModel.addRow(new Object[]{domain.getDomainValue(i).getValue()});
        }
        tblDomainValues.setModel(myModel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void createView() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        createControls();
        tfDomainName.setText("Домен "+domainModel.size());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void createControls(){
        initTable();
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        btnDeleteDomainValue.addActionListener(e -> BtnDeleteDomainValueClicked());
        btnAddDomainValue.addActionListener(e->BtnAddDomainValueClicked());
        btnEditDomainValue.addActionListener(e->BtnEditDomainValueClicked());
        tfDomainValue.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tfDomainValue.setSelectionStart(0);
                tfDomainValue.setSelectionEnd(tfDomainValue.getText().length());
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
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        contentPane.registerKeyboardAction(e -> BtnAddDomainValueClicked(), KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        tfDomainValue.registerKeyboardAction(e -> BtnAddDomainValueClicked(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    //вызывается один раз при создании формы
    private void initTable() {
        
        tblDomainValues.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //drag & drop
        tblDomainValues.setDragEnabled(true);
        tblDomainValues.setDropMode(DropMode.INSERT_ROWS);
        tblDomainValues.setTransferHandler(new TableRowTransferHandler(tblDomainValues, this));

        //model
        myModel = new TableModel(new String[]{"Значение домена"});
        //myModel.addRow(new Object[]{"Domain value 1"});
        tblDomainValues.setModel(myModel);

        //fonts
        tblDomainValues.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
        tblDomainValues.setForeground(Color.DARK_GRAY);
        tblDomainValues.setFont(new Font("Verdana", Font.PLAIN, 12));

        //sorter
        tblDomainValues.setAutoCreateRowSorter(false);

        //background color
        scpDomainValues.getViewport().setBackground(Color.white);
    }

    private void BtnEditDomainValueClicked(){
       control.editDomainValue();
    }
    private void BtnAddDomainValueClicked(){
        control.addDomainValue();
    }
    public void editTblDomainValueSelectedRow(){
        myModel.setValueAt(tfDomainValue.getText().trim(), tblDomainValues.getSelectedRow(), 0);
        tblDomainValues.setModel(myModel);
    }
    public boolean isTfDomainValueEmpty(){return tfDomainValue.getText().trim().isEmpty(); }
    public boolean isTfDomainNameEmpty(){
        return tfDomainName.getText().trim().isEmpty();
    }
    public boolean isDomainValueSelect(){
        return tblDomainValues.getSelectedRow()!=-1;
    }
    private void BtnDeleteDomainValueClicked(){
        control.deleteDomainValue();

    }
    public void deleteTblDomainValueSelectedRow(){
        myModel.removeRow(tblDomainValues.getSelectedRow());
        tblDomainValues.setModel(myModel);
    }

    private void onOK() {
        control.ok();
    }

    private void onCancel() {
        control.cancel();
    }
    public void Dispose(){
        dispose();
    }

    public void showMessage(String msg) {JOptionPane.showMessageDialog(this, msg); }

    //создаем новый домен
    public Domain getNewDomain() {
        Domain newDomain = new Domain(tfDomainName.getText());
        for(int i=0; i<myModel.getRowCount(); i++){
            newDomain.add(new DomainValue((String)myModel.getValueAt(i, 0)));
        }
        return newDomain;
    }
    public boolean isDomainValuesEmpty() {return myModel.getRowCount()==0;}
    public String getSelectedDomainValue(){return (String)tblDomainValues.getValueAt(tblDomainValues.getSelectedRow(), 0);}

    public boolean isDomainValuesUnique(String value) {
        for(int i=0; i< myModel.getRowCount(); i++)
        {
            if (myModel.getValueAt(i, 0).equals(value)) return false;
        }
        return true;
    }

    public String getNewDomainValue() {return tfDomainValue.getText().trim();}

    public void addTblDomainValueNewRow() {myModel.addRow(new Object[]{getNewDomainValue()});}

    @Override
    public void rowReorder(int from, int to, JTable tblDomainValues) {

    }
}
