package pl.trollsysstems.exceltoexcel.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.trollsysstems.exceltoexcel.model.InclinometerChain;
import pl.trollsysstems.exceltoexcel.model.InclinometerImportParam;
import pl.trollsysstems.exceltoexcel.model.Measure;
import pl.trollsysstems.exceltoexcel.service.RandomMeasurementGeneratorImpl;

import java.io.*;
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
    private TextField textFieldDeviation;
    @FXML
    private TextField textFieldMeasurementPerDay;
    @FXML
    private TextField textFieldStartValue;
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
        List<InclinometerChain> inclinometerChainList = new ArrayList<>();

        for (InclinometerImportParam inclinometerImportParam : observableArrayList) {
            InclinometerChain inclinometerChain = new InclinometerChain();

            inclinometerChain.setInclinometerName(inclinometerImportParam.getInclinometerName());
            inclinometerChain.setMeasureList(createMeasurementsForOneInclinometerChain(inclinometerImportParam));

            inclinometerChainList.add(inclinometerChain);
        }


        System.out.println("FINISH!");
        showInfoAboutFinishCreatMeasuremant();
//        observableArrayList.forEach(s -> System.out.println(s.getInclinometerName()));
    }

    private List<Measure> createMeasurementsForOneInclinometerChain(InclinometerImportParam inclinometerImportParam) {
        List<Measure> measureList = new ArrayList<>();
        RandomMeasurementGeneratorImpl randomMeasurementGenerator = new RandomMeasurementGeneratorImpl();

        inclinometerImportParam.getQuantityInclinometerInChain();

        Long i = 0L;
        while (inclinometerImportParam.getStartDate()
                .plusDays(i)
                .isBefore(inclinometerImportParam.getStopDate().plusDays(1))) {

            for (int j = 0; j < Integer.parseInt(textFieldMeasurementPerDay.getText()); j++) {

                for (int k = 0; k < inclinometerImportParam.getQuantityInclinometerInChain(); k++) {
                    Measure measure = new Measure();

                    measure.setMeasureDate(inclinometerImportParam.getStartDate().plusDays(i));
                    measure.setNumberOfMeasure(k);
                    measure.setId(j);
                    measure.setAngelX(randomMeasurementGenerator
                            .generateMeasurement(1.0,
                                    Double.parseDouble(textFieldDeviation.getText())));
                    measure.setAngleY(randomMeasurementGenerator
                            .generateMeasurement(1.0,
                                    Double.parseDouble(textFieldDeviation.getText())));
                    measure.setTemperature(1.0);
                    System.out.println(measure.toString());
                    measureList.add(measure);
                }
            }
            i++;
        }
        return measureList;
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
            fileTableView(inclinometerImportParamList);

            buttonGenerate.setDisable(false);
            textFieldDeviation.setDisable(false);
            textFieldStartValue.setDisable(false);
            textFieldMeasurementPerDay.setDisable(false);
        }
    }


    private void fileTableView(List<InclinometerImportParam> inclinometerImportParamList) {
        observableArrayList = FXCollections.observableArrayList(inclinometerImportParamList);

        tableViewConfig.setItems(observableArrayList);
        columnNameConfig.setCellValueFactory(new PropertyValueFactory<>("inclinometerName"));
        columnQuantityConfig.setCellValueFactory(new PropertyValueFactory<>("quantityInclinometerInChain"));
        columnStartDateConfig.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        columnStopDateConfig.setCellValueFactory(new PropertyValueFactory<>("stopDate"));
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

    private void saveInclinometerMeasurementToExcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("IG");

        Row row = sheet.createRow(2);
        org.apache.poi.ss.usermodel.Cell cell = row.createCell(0);
        cell.setCellValue("John Smith");


        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";



        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }


    private void showInfoAboutFinishCreatMeasuremant(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Generowanie obliczeń");
        alert.setContentText("Chyba przebiegło prawidłowo...");

        alert.showAndWait();
    }
}

