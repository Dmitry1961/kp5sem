package sample.patternBuilder;

import sample.classes.House;

public class BuilderHouse implements BulderH{
    House house = new House();
    @Override
    public BulderH setIdHouse(String idHouse) {
        house.idHouse = idHouse;
        return this;
    }

    @Override
    public BulderH setCountry(String country) {
        house.country = country;
        return this;
    }

    @Override
    public BulderH setType(String type) {
        house.type = type;
        return this;
    }

    @Override
    public BulderH setCountRoom(String countRoom) {
        house.countRoom = countRoom;
        return this;
    }

    @Override
    public BulderH setFestive(String festive) {
        house.festive = festive;
        return this;
    }

    @Override
    public BulderH setPriceDay(String priceDay) {
        house.priceDay = priceDay;
        return this;
    }

    @Override
    public House build() {
        return house;
    }
}
