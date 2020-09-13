package ru.gasheva.mainform;

import javax.swing.table.DefaultTableModel;
import java.util.LinkedList;
import java.util.List;

public class TableModel extends DefaultTableModel implements Reorderable{

    public TableModel() {
        addColumn("Правило");
        addColumn("Описание");
    }

    @Override
    public void reorder(int fromIndex, int toIndex) {
        List<Object> fromRowValues = new LinkedList<Object>();
        for (int i=0; i<getColumnCount(); i++){
            fromRowValues.add(getValueAt(fromIndex, i));
        }
        insertRow(toIndex, fromRowValues.toArray());
        if (fromIndex<toIndex)removeRow(fromIndex);
        else removeRow(fromIndex+1);
    }

    private boolean validRuleName(String ruleName){
        for (int i = 0; i<getRowCount(); i++){
            if (getValueAt(i,0)==ruleName) return false;
        }
        return true;
    }
    //вставка с проверкой уникальности имени правила
    public boolean specialInsert(int row, Object[] rowData) {
        if (validRuleName(rowData[0].toString())) insertRow(row, rowData);
        else return false;
        return true;
    }
    //добавление с проверкой уникальности имени правила
    public boolean specialAdd(int row, Object[] rowData) {
        if (validRuleName(rowData[0].toString())) addRow(rowData);
        else return false;
        return true;
    }

    @Override
    public boolean isCellEditable(int arg0, int arg1) {
        return false;
    }
    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 1:
                return String.class;
            default:
                return String.class;
        }
    }
}
