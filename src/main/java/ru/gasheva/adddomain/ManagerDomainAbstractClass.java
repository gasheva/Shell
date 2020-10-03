package ru.gasheva.adddomain;

import ru.gasheva.models.DomainModel;
import ru.gasheva.models.classes.Domain;

abstract public class ManagerDomainAbstractClass {
    DomainModel domainModel;
    CreateDomainForm view;
    Domain newDomain = null;

    public ManagerDomainAbstractClass(DomainModel domainModel) {
        this.domainModel = domainModel;
        view = new CreateDomainForm(this);

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
        newDomain = view.getNewDomain();
        //проверка на уникальность имени
        if(!isDomainValid(newDomain))
        {
            view.showMessage("Домен с таким именем уже существует!");
            newDomain = null;
            return;
        }
        // domainModel.add(newDomain);
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
    public Domain getResult(){
        return newDomain;
    }
}
