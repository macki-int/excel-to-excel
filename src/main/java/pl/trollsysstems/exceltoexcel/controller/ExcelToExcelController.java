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
    private TextField textFieldMaxAdd;
    @FXML
    private TextField textFieldStartValue;
    @FXML
    private TextArea textResult;
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
    private void onButtonGenerateClick() throws IOException {
        List<InclinometerChain> inclinometerChainList = new ArrayList<>();

        for (InclinometerImportParam inclinometerImportParam : observableArrayList) {
            InclinometerChain inclinometerChain = new InclinometerChain();

            inclinometerChain.setInclinometerName(inclinometerImportParam.getInclinometerName());
            inclinometerChain.setMeasureList(createMeasurementsForOneInclinometerChain(inclinometerImportParam));

            inclinometerChainList.add(inclinometerChain);
        }

        saveInclinometerMeasurementToExcel(inclinometerChainList);
//        for (InclinometerChain inclinometerChain : inclinometerChainList) {
//            inclinometerChain.getMeasureList().forEach(s -> System.out.println(s.toString()));
//        }
        System.out.println("FINISH!");

        showInfoAboutFinishCreatMeasuremant();
//        observableArrayList.forEach(s -> System.out.println(s.getInclinometerName()));
    }

    private List<Measure> createMeasurementsForOneInclinometerChain(InclinometerImportParam inclinometerImportParam) {
        List<Measure> measureList = new ArrayList<>();

        Double angelX = getRandomMeasurment(Double.parseDouble(textFieldStartValue.getText()));
        Double angelY = getRandomMeasurment(Double.parseDouble(textFieldStartValue.getText()));

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

                    Double tempAngelX = getRandomMeasurment(Double.parseDouble(textFieldStartValue.getText()));
                    if (isAngelLowerThanAngelTempOrAngelUnequalZero(angelX, tempAngelX)) {
                        angelX = tempAngelX;
                    } else {
                        angelX = 0.0;
                    }
                    measure.setAngelX(angelX);

                    Double tempAngelY = getRandomMeasurment(Double.parseDouble(textFieldStartValue.getText()));
                    if (isAngelLowerThanAngelTempOrAngelUnequalZero(angelY, tempAngelY)) {
                        angelY = tempAngelY;
                    } else {
                        angelY = 0.0;
                    }
                    measure.setAngelY(angelY);
                    measure.setTemperature(1.0);

                    measureList.add(measure);
                }
            }
            i++;
        }
        return measureList;
    }

    private boolean isAngelLowerThanAngelTempOrAngelUnequalZero(Double angel, Double tempAngel) {
        return angel < tempAngel + Double.parseDouble(textFieldMaxAdd.getText()) || angel != 0.0;
    }

    private Double getRandomMeasurment(Double baseValue) {
        RandomMeasurementGeneratorImpl randomMeasurementGenerator = new RandomMeasurementGeneratorImpl();

        return randomMeasurementGenerator.generateMeasurement(baseValue, Double.parseDouble(textFieldDeviation.getText()));
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
            textFieldMaxAdd.setDisable(false);
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

    private void saveInclinometerMeasurementToExcel(List<InclinometerChain> inclinometerChainList) throws IOException {
        List<String> fileList = new ArrayList<>();

        for (int i = 0; i < inclinometerChainList.size(); i++) {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("IG");

            String inclinometerName = inclinometerChainList.get(i).getInclinometerName();
            List<Measure> measureList = inclinometerChainList.get(i).getMeasureList();
            org.apache.poi.ss.usermodel.CellStyle cellStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.CreationHelper createHelper = workbook.getCreationHelper();

            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-mm-yyyy"));

            for (int j = 0; j < measureList.size(); j++) {
                Row row = sheet.createRow(j);
                org.apache.poi.ss.usermodel.Cell cell0 = row.createCell(0);
                cell0.setCellStyle(cellStyle);
                cell0.setCellValue(measureList.get(j).getMeasureDate());
                org.apache.poi.ss.usermodel.Cell cell1 = row.createCell(1);
                cell1.setCellValue(measureList.get(j).getId() + 1);
                org.apache.poi.ss.usermodel.Cell cell2 = row.createCell(2);
                cell2.setCellValue(inclinometerName);
                org.apache.poi.ss.usermodel.Cell cell3 = row.createCell(3);
                cell3.setCellValue(measureList.get(j).getNumberOfMeasure() + 1);
                org.apache.poi.ss.usermodel.Cell cell4 = row.createCell(4);
                cell4.setCellValue(measureList.get(j).getAngelX());
                org.apache.poi.ss.usermodel.Cell cell5 = row.createCell(5);
                cell5.setCellValue(measureList.get(j).getAngelY());
                org.apache.poi.ss.usermodel.Cell cell6 = row.createCell(6);
                cell6.setCellValue(measureList.get(j).getTemperature());
            }

            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + inclinometerName + ".xlsx";

            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
            fileList.add(fileLocation);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : fileList) {
            stringBuilder.append(s + "\n");
        }
        textResult.setText(stringBuilder.toString());
    }


    private void showInfoAboutFinishCreatMeasuremant() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Generowanie obliczeń...");
        alert.setContentText("Chyba przebiegło prawidłowo.");

        alert.showAndWait();
    }
}

