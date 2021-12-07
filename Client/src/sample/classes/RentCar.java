package sample.classes;

import com.sun.javafx.iio.common.PushbroomScaler;

public class RentCar {
    private String idRentCar;
    private String brand;
    private String year;
    private String priceDay;
    private String time;
    private String timeEnd;
    private String userName;
    public RentCar(String idRentCar, String brand,  String year, String priceDay, String time, String timeEnd, String userName) {
        this.idRentCar = idRentCar;
        this.brand = brand;
        this.year = year;
        this.priceDay = priceDay;
        this.time = time;
        this.timeEnd = timeEnd;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdRentCar() {
        return idRentCar;
    }

    public void setIdRentCar(String idRentCar) {
        this.idRentCar = idRentCar;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPriceDay() {
        return priceDay;
    }

    public void setPriceDay(String priceDay) {
        this.priceDay = priceDay;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
