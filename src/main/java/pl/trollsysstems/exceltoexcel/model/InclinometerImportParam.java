package pl.trollsysstems.exceltoexcel.model;

import javafx.beans.property.*;

import java.time.LocalDate;

public class InclinometerImportParam {
    private StringProperty inclinometerName;
    private IntegerProperty quantityInclinometerInChain;
    private ObjectProperty<LocalDate> startDate;
    private ObjectProperty<LocalDate> stopDate;

    public String getInclinometerName() {
        return inclinometerNameProperty().get();
    }

    public void setInclinometerName(String inclinometerName) {
        inclinometerNameProperty().set(inclinometerName);
    }

    public StringProperty inclinometerNameProperty() {
        if (inclinometerName == null) inclinometerName = new SimpleStringProperty(this, "inclinometerName");
        return inclinometerName;
    }

    public Integer getQuantityInclinometerInChain() {
        return quantityInclinometerInChainProperty().get();
    }

    public void setQuantityInclinometerInChain(Integer quantityInclinometerInChain) {
        quantityInclinometerInChainProperty().set(quantityInclinometerInChain);
    }

    public IntegerProperty quantityInclinometerInChainProperty() {
        if (quantityInclinometerInChain == null) quantityInclinometerInChain = new SimpleIntegerProperty(this, "quantityInclinometerInChain");
        return quantityInclinometerInChain;
    }


    public LocalDate getStartDate() {
        return startDate.get();
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = new SimpleObjectProperty<>(startDate);
    }

    public LocalDate getStopDate() {
        return stopDate.get();
    }

    public void setStopDate(LocalDate stopDate) {
        this.stopDate = new SimpleObjectProperty<>(stopDate);
    }
}
