package pl.trollsysstems.exceltoexcel.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelToExcelController {
    //    @FXML
//    private Button buttonExit;
//    @FXML
//    private Button buttonOpenConfigDialog;
    @FXML
    private Label labelPath;

    @FXML
    private void onButtonExitClick() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void onButtonOpenConfigDialogClick() throws IOException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Otwórz plik konfiguracyjny");
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            labelPath.setWrapText(true);
            labelPath.setText(file.toString());

            readConfigureFile(file);
        }
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Wszystkie pliki", "*.*"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS", "*.xls")
        );
    }

    private void readConfigureFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);

    }
}

