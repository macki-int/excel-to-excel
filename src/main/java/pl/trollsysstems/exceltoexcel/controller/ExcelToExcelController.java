package pl.trollsysstems.exceltoexcel.controller;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.trollsysstems.exceltoexcel.model.InclinometerImportParam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ExcelToExcelController {
    @FXML
    private Button buttonExit;
    @FXML
    private Button buttonGenerate;
    @FXML
    private Label labelPath;
    @FXML
    private TableView<InclinometerImportParam> tableViewConfig;
    @FXML
    private TableColumn<InclinometerImportParam, String> columnNameConfig;
    @FXML
    private TableColumn<InclinometerImportParam, Integer> columnQuantityConfig;
    @FXML
    private TableColumn<InclinometerImportParam, LocalDate> columnStartDateConfig;
    @FXML
    private TableColumn<InclinometerImportParam, LocalDate> columnStopDateConfig;
    private ObservableList<InclinometerImportParam> observableArrayList;

    @FXML
    private void onButtonExitClick() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    private void onButtonGenerateClick() {
        System.out.println("Generate");
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

            List<InclinometerImportParam> inclinometerImportParamList = readConfigureFile(file);

            observableArrayList = FXCollections.observableArrayList(inclinometerImportParamList);

            tableViewConfig.setItems(observableArrayList);
            columnNameConfig.setCellValueFactory(new PropertyValueFactory<>("inclinometerName"));
            columnQuantityConfig.setCellValueFactory(new PropertyValueFactory<>("quantityInclinometerInChain"));
            columnStartDateConfig.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            columnStopDateConfig.setCellValueFactory(new PropertyValueFactory<>("stopDate"));

            buttonGenerate.setDisable(false);
        }
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Wszystkie pliki", "*.*"),
                new FileChooser.ExtensionFilter("XLSX", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS", "*.xls")
        );
    }

    private List<InclinometerImportParam> readConfigureFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fis);

        Sheet sheet = workbook.getSheetAt(0);

        List<InclinometerImportParam> inclinometerImportParamList = new ArrayList<>();
        for (Row row : sheet) {
            inclinometerImportParamList.add(getValueFromCells(row));
        }
        workbook.close();
        return inclinometerImportParamList;
    }

    private InclinometerImportParam getValueFromCells(Row row) {
        InclinometerImportParam inclinometerImportParam = new InclinometerImportParam();

        inclinometerImportParam.setInclinometerName(row.getCell(0).getStringCellValue());
        inclinometerImportParam.setQuantityInclinometerInChain((int) row.getCell(1).getNumericCellValue());
        inclinometerImportParam.setStartDate(row.getCell(2).getLocalDateTimeCellValue().toLocalDate());
        inclinometerImportParam.setStopDate(row.getCell(4).getLocalDateTimeCellValue().toLocalDate());

        return inclinometerImportParam;
    }

}

