package sample.patternBuilder;

import sample.classes.Car;

public class BuilderCar implements BuilderC{
    Car car = new Car();
    @Override
    public BuilderC setIdCar(String idCar) {
        car.idCar = idCar;
        return this;
    }

    @Override
    public BuilderC setBrand(String brand) {
        car.brand = brand;
        return this;
    }

    @Override
    public BuilderC setYear(String year) {
        car.year = year;
        return this;
    }

    @Override
    public BuilderC setVolume(String volume) {
        car.volume = volume;
        return this;
    }

    @Override
    public BuilderC setEngine(String engine) {
        car.engine = engine;
        return this;
    }

    @Override
    public BuilderC setPriceDay(String priceDay) {
        car.priceDay = priceDay;
        return this;
    }

    @Override
    public Car build() {
        return car;
    }
}
