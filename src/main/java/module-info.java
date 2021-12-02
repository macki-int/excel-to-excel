module pl.trollsysstems.exceltoexcel {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens pl.trollsysstems.exceltoexcel to javafx.fxml;
    exports pl.trollsysstems.exceltoexcel;
}