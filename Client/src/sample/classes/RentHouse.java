package sample.classes;

public class RentHouse {
    private String idRentHouse;
    private String country;
    private String priceDay;
    private String time;
    private String timeEnd;
    private String userName;
    public RentHouse(String idRentHouse, String country, String priceDay, String time, String timeEnd,String userName) {
        this.idRentHouse = idRentHouse;
        this.country = country;
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

    public String getIdRentHouse() {
        return idRentHouse;
    }

    public void setIdRentHouse(String idRentHouse) {
        this.idRentHouse = idRentHouse;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
