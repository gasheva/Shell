package ru.gasheva.adddomain;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.classes.Domain;

public class AddDomainControl implements IDomainControl {
    DomainModel domainModel;
    CreateDomainForm view;

    public AddDomainControl(DomainModel domainModel) {
        this.domainModel = domainModel;

        view = new CreateDomainForm(this);
    }
    public boolean checkDomainValid(Domain domain){
        domainModel.findDomain(domain);
        return true;
    }

    @Override
    public void ok() {
        if (view.isDomainValuesEmpty()){
            view.showMessage("Добавьте значения домена!");
            return;
        }
        Domain domain = view.getNewDomain();
        //проверка на уникальность имени
        if(!checkDomainValid(domain))
        {
            view.showMessage("Домен с таким именем уже существует!");
            return;
        }

        domainModel.add(domain);
        view.Dispose();
    }

    @Override
    public void cancel() {
        view.Dispose();
    }

    @Override
    public void addDomainValue() {

    }

    @Override
    public void deleteDomainValue() {

    }

    @Override
    public void transferDomainValue() {

    }
}
