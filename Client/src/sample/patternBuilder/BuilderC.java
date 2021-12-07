package sample.patternBuilder;

import sample.classes.Car;

public interface BuilderC {
    BuilderC setIdCar(String idCar);
    BuilderC setBrand(String brand);
    BuilderC setYear(String year);
    BuilderC setVolume(String volume);
    BuilderC setEngine(String engine);
    BuilderC setPriceDay(String priceDay);
    Car build();

}
