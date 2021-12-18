module pl.trollsysstems.exceltoexcel {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires poi.ooxml;

    opens pl.trollsysstems.exceltoexcel to javafx.fxml;
    exports pl.trollsysstems.exceltoexcel;
    exports pl.trollsysstems.exceltoexcel.controller;
    opens pl.trollsysstems.exceltoexcel.controller to javafx.fxml;
}