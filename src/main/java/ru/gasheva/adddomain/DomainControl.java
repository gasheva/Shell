package ru.gasheva.adddomain;

import ru.gasheva.controls.ControlInterface;
import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.DomainModel;
import ru.gasheva.models.classes.Domain;

public class DomainControl implements ControlInterface {
    MainForm view;
    DomainModel domainModel;
    ManagerDomainAbstractClass addDomain;
    ManagerDomainAbstractClass editDomain;

    public DomainControl(DomainModel domainModel, MainForm view) {
        this.view = view;
        this.domainModel = domainModel;
    }

    @Override
    public void add() {
        addDomain = new AddDomainControl (domainModel);
        Domain newDomain = addDomain.getResult();
        if (newDomain==null) return;
        String[] domainString = new String[2];
        domainString[0] = newDomain.getName();
        domainString[1] = newDomain.getDomainValuesInString();
        //добавление в конец
        if (view.getSelectedRowIndex()==-1){
            domainModel.add(newDomain);
            view.AddInTable(domainString);
        }
        //вставка
        else {
            domainModel.add(view.getSelectedRowIndex(), newDomain);
            view.InsertInTable(view.getSelectedRowIndex(), domainString);
        }
    }

    @Override
    public void edit() {
        if(!view.isTblInfoSelectRow()){
            view.showMessage("Выберите домен!");
            return;
        }
        String domainName = view.getSelectedRowFirstColumnValue();
        //Domain domain = domainModel.find(domainName); //TODO по идее так, но в данном случае это не нужно
        editDomain = new EditDomainControl (domainModel, new Domain(domainName));
        Domain newDomain = editDomain.getResult();
        if (newDomain==null) return;
        //обновляем модель
        domainModel.setDomain(domainModel.getDomainIndex(domainName), newDomain);

        //обновляем вьюшку
        String[] domainString = new String[2];
        domainString[0] = newDomain.getName();
        domainString[1] = newDomain.getDomainValuesInString();
        int selectedRowIndex = view.getSelectedRowIndex();
        view.ChangeRowInTable(selectedRowIndex, domainString);
    }


    @Override
    public void remove() {

    }

    @Override
    public void redraw() {
        view.createModel(new String[]{"Имя", "Значения"});
        view.fillTable(domainModel);
        view.setTableModel();
        view.changePrepPanelText("Имя домена");
        view.changeConclusionPanelText("Значения");
        view.setPrepPanelVisible(true);
        view.setConclusionPanelVisible(true);

    }

    @Override
    public void rowReorder(int from, int to) {
        String id = view.getRowFirstColumnValue(to);
        domainModel.reorder(from, to, id);
    }
}
