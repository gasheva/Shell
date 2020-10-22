package ru.gasheva.models;

import ru.gasheva.models.classes.Variable;

import java.util.LinkedList;
import java.util.List;

public class VariableModel implements ModelInterface{
    List<Variable> variables = new LinkedList<>();

    //region Getter-Setter

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public void add(Variable var) {
        variables.add(var);
    }
    public Variable getVariable(int index){ return variables.get(index);}
    public void add(int index, Variable variable){
        variables.add(index, variable);
    }
    public void remove(Variable variable){
        variables.remove(variable);
    }
    public void remove(String name){variables.remove(getVariable(name));}
    public void insertValue(int index, Variable variable) {variables.add(index, variable);}
    public Variable getVariable(String name){return variables.stream().filter(x->x.getName().equals(name)).findAny().get();}

    @Override
    public int size(){return variables.size();}

    @Override
    public String[] getValuesForTable(int index) {
        Variable v = getVariable(index);
        return new String[]{v.getName(), v.getVarType().toString(), v.getDomain().getName()};
    }
    //endregion

    //кол-во доменов с передаваемым именем
    public int variableCount(Variable variable){
        return (int)variables.stream().filter(x->x.equals(variable)).count();
    }

    public void setVariable(int index, Variable newVariable) {
        variables.set(index, newVariable);
    }

    public int getVariableIndex(String name) {
        for(int i=0; i<variables.size();i++){
            if (variables.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }

    public void reorder(int from, int to, String id) {
    }

    public boolean isVariableExisting(Variable variable) {
        return variables.stream().anyMatch(x->x.equals(variable));
    }

    public void clear() {
        variables.clear();
    }
}
