package ru.gasheva.adddomain;

import ru.gasheva.controls.ControlInterface;
import ru.gasheva.mainform.MainForm;
import ru.gasheva.models.DomainModel;

public class DomainControl implements ControlInterface {
    MainForm view;
    DomainModel domainModel;
    IDomainControl addDomain;
    IDomainControl editDomain;

    public DomainControl(DomainModel domainModel, MainForm view) {
        this.view = view;
        this.domainModel = domainModel;
    }

    @Override
    public void add(int rowIndex) {
        addDomain = new AddDomainControl (domainModel);
    }

    @Override
    public void edit(String id, String[] values) {
        //editDomain = new EditDomainControl (domainModel);
    }

    @Override
    public void remove(String id) {

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
