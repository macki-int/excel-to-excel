package pl.trollsysstems.exceltoexcel.service;

import java.util.Random;

public class RandomMeasurementGeneratorImpl implements RandomMeasurementGenerator {

    @Override
    public Double generateMeasurement(Double baseMeasurementValue, Double deviation) {
        Random random = new Random();
        Double randomValue = random.doubles(baseMeasurementValue, baseMeasurementValue * deviation)
                .limit(1)
                .findFirst()
                .getAsDouble();

        return randomValue;
    }
}
