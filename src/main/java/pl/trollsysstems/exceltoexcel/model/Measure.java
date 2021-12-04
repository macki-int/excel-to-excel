package pl.trollsysstems.exceltoexcel.model;

import java.time.LocalDateTime;

public class Measure {
    private int id;
    private LocalDateTime measureDateAndTime;
    private Double angelX;
    private Double angleY;
    private Double temperature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getMeasureDateAndTime() {
        return measureDateAndTime;
    }

    public void setMeasureDateAndTime(LocalDateTime measureDateAndTime) {
        this.measureDateAndTime = measureDateAndTime;
    }

    public Double getAngelX() {
        return angelX;
    }

    public void setAngelX(Double angelX) {
        this.angelX = angelX;
    }

    public Double getAngleY() {
        return angleY;
    }

    public void setAngleY(Double angleY) {
        this.angleY = angleY;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
