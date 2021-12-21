package pl.trollsysstems.exceltoexcel.service;

import java.util.Random;

public class RandomMeasurementGeneratorImpl implements RandomMeasurementGenerator {

    @Override
    public Double generateMeasurement(Double baseMeasurementValue) {
        Random random = new Random();

        Double randomValue = random.nextDouble();
        System.out.println(randomValue);
        return randomValue;
    }
}
