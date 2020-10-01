package ru.gasheva.adddomain;

import ru.gasheva.models.classes.Domain;

public interface IDomainControl {
    void ok();
    void cancel();
    void addDomainValue();
    void deleteDomainValue();
    void transferDomainValue();     //для переноса значения
}
