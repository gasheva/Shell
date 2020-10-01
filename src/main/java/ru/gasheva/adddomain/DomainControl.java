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
    }

    @Override
    public void remove() {

    }

    @Override
    public void redraw() {
        view.createModel(new String[]{"Имя", "Значения"});
        view.setTableModel();
        view.changePrepPanelText("Имя домена");
        view.changeConclusionPanelText("Значения");
        view.setPrepPanelVisible(true);
        view.setConclusionPanelVisible(true);
    }
}
