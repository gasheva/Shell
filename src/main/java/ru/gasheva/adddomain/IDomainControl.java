package ru.gasheva.adddomain;

public interface IDomainControl {
    void ok();
    void cancel();
    void addDomainValue();
    void deleteDomainValue();
    void editDomainValue();     //для переноса значения
}
