package sample.classes;

public class Income {
    private String idIncome;
    private String nameRent;
    private String price;
    private String prosrosh;
    private String datee;
    private String username;

    public Income(String idIncome, String nameRent, String price, String prosrosh, String datee,String username) {
        this.idIncome = idIncome;
        this.nameRent = nameRent;
        this.price = price;
        this.prosrosh = prosrosh;
        this.datee = datee;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdIncome() {
        return idIncome;
    }

    public void setIdIncome(String idIncome) {
        this.idIncome = idIncome;
    }

    public String getNameRent() {
        return nameRent;
    }

    public void setNameRent(String nameRent) {
        this.nameRent = nameRent;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProsrosh() {
        return prosrosh;
    }

    public void setProsrosh(String prosrosh) {
        this.prosrosh = prosrosh;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }
}
