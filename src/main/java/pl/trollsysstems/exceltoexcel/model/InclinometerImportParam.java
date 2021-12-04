package pl.trollsysstems.exceltoexcel.model;

import java.time.LocalDate;

public class InclinometerImportParam {
    private String inclinometerName;
    private int quantityInclinometerInChain;
    private LocalDate startDate;
    private LocalDate stopDate;

    public String getInclinometerName() {
        return inclinometerName;
    }

    public void setInclinometerName(String inclinometerName) {
        this.inclinometerName = inclinometerName;
    }

    public int getQuantityInclinometerInChain() {
        return quantityInclinometerInChain;
    }

    public void setQuantityInclinometerInChain(int quantityInclinometerInChain) {
        this.quantityInclinometerInChain = quantityInclinometerInChain;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStopDate() {
        return stopDate;
    }

    public void setStopDate(LocalDate stopDate) {
        this.stopDate = stopDate;
    }
}
