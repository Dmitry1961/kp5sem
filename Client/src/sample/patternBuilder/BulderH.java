package sample.patternBuilder;
import sample.classes.House;

public interface BulderH {
    BulderH setIdHouse(String idHouse);
    BulderH setCountry(String country);
    BulderH setType(String type);
    BulderH setCountRoom(String countRoom);
    BulderH setFestive(String festive);
    BulderH setPriceDay(String priceDay);
    House build();
}
