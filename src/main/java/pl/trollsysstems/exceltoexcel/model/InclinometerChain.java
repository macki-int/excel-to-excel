package pl.trollsysstems.exceltoexcel.model;

import java.util.List;

public class InclinometerChain {
    private String inclinometerName;
    private List<Measure> measureList;

    public String getInclinometerName() {
        return inclinometerName;
    }

    public void setInclinometerName(String inclinometerName) {
        this.inclinometerName = inclinometerName;
    }

    public List<Measure> getMeasureList() {
        return measureList;
    }

    public void setMeasureList(List<Measure> measureList) {
        this.measureList = measureList;
    }
}
