package pl.trollsysstems.exceltoexcel.service;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class NumericTextField extends TextField {

    public NumericTextField() {
        super();
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
}
