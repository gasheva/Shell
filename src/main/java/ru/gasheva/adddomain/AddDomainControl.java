package ru.gasheva.adddomain;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.classes.Domain;

public class AddDomainControl implements IDomainControl {
    DomainModel domainModel;
    CreateDomainForm view;

    public AddDomainControl(DomainModel domainModel) {
        this.domainModel = domainModel;

        view = new CreateDomainForm(this);
        view.createView();
    }
    public boolean checkDomainValid(Domain domain){
        return !domainModel.findDomain(domain);
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
        if(view.isTfDomainValueEmpty()){
            view.showMessage("Введите новое значение!");
            return;
        }
        if (!view.isDomainValuesUnique(view.getNewDomainValue())){
            view.showMessage("У домена уже имеется это значение!");
            return;
        }
        view.addTblDomainValueNewRow();

    }

    @Override
    public void deleteDomainValue() {
        if(!view.isDomainValueSelect()) {
            view.showMessage("Выберите значение домена!");
            return;
        }
        view.deleteTblDomainValueSelectedRow();
    }

    @Override
    public void editDomainValue() {
        if(!view.isDomainValueSelect()) {
            view.showMessage("Выберите значение домена!");
            return;
        }
        if(view.isTfDomainValueEmpty()){
            view.showMessage("Введите новое значение!");
            return;
        }
        if (!view.isDomainValuesUnique(view.getNewDomainValue())){
            view.showMessage("У домена уже имеется это значение!");
            return;
        }

        view.editTblDomainValueSelectedRow();
    }
}
