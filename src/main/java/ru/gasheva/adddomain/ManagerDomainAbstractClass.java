package ru.gasheva.adddomain;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.classes.Domain;

abstract public class ManagerDomainAbstractClass {
    DomainModel domainModel;
    CreateDomainForm view;

    public ManagerDomainAbstractClass(DomainModel domainModel) {
        this.domainModel = domainModel;

        view = new CreateDomainForm(this);
        view.createView();
    }
    abstract protected boolean isDomainValid(Domain domain);

    public void ok() {
        if (view.isTfDomainValueEmpty()){
            view.showMessage("Введите имя домена!");
            return;
        }
        if (view.isDomainValuesEmpty()){
            view.showMessage("Добавьте значения домена!");
            return;
        }
        Domain domain = view.getNewDomain();
        //проверка на уникальность имени
        if(!isDomainValid(domain))
        {
            view.showMessage("Домен с таким именем уже существует!");
            return;
        }

        domainModel.add(domain);
        view.Dispose();
    }

    public void cancel() {
        view.Dispose();
    }

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

    public void deleteDomainValue() {
        if(!view.isDomainValueSelect()) {
            view.showMessage("Выберите значение домена!");
            return;
        }
        view.deleteTblDomainValueSelectedRow();
    }

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
