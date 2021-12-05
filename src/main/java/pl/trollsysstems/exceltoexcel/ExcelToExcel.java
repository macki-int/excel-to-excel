package pl.trollsysstems.exceltoexcel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ExcelToExcel extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ExcelToExcel.class.getResource("excel-to-excel-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 575, 500);

        Image image = new Image("file:src/main/resources/excel_icon.png");

        stage.getIcons().add(image);
        stage.setTitle("ExcelToExcel");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}