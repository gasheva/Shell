package ru.gasheva.addrule.addfact;

public class ToolTipWrapper implements ToolTipProvider {
    final Object value;
    final String toolTip;

    public ToolTipWrapper(Object value, String toolTip) {
        this.value = value;
        this.toolTip = toolTip;
    }

    @Override
    public String getToolTip() {
        return toolTip;
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
