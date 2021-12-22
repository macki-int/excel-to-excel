package pl.trollsysstems.exceltoexcel.model;

import java.time.LocalDate;

public class Measure {
    private int id;
    private LocalDate measureDate;
    private int numberOfMeasure;
    private Double angelX;
    private Double angleY;
    private Double temperature;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getMeasureDate() {
        return measureDate;
    }

    public void setMeasureDate(LocalDate measureDate) {
        this.measureDate = measureDate;
    }

    public int getNumberOfMeasure() {
        return numberOfMeasure;
    }

    public void setNumberOfMeasure(int numberOfMeasure) {
        this.numberOfMeasure = numberOfMeasure;
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

    @Override
    public String toString() {
        return "Measure{" +
                "id=" + id +
                ", measureDate=" + measureDate +
                ", numberOfMeasure=" + numberOfMeasure +
                ", angelX=" + angelX +
                ", angleY=" + angleY +
                ", temperature=" + temperature +
                '}';
    }
}
