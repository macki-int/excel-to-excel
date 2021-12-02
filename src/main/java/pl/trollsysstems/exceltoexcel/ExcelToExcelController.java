package pl.trollsysstems.exceltoexcel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ExcelToExcelController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}