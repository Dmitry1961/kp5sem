package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.classes.Account;
import sample.classes.Car;
import sample.patternBuilder.BuilderCar;

public class RentCarController {
    private ObservableList<Car> carData = FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Label infoLabel;

    @FXML
    private TableView<Car> carTable;

    @FXML
    private TableColumn<Car, String> brandColums;

    @FXML
    private TableColumn<Car, String> yearColums;

    @FXML
    private TableColumn<Car, String> volumeColums;

    @FXML
    private TableColumn<Car, String> engineColums;

    @FXML
    private TableColumn<Car, String> priceColums;

    @FXML
    private TextField priceField;

    @FXML
    private Button rentButton;

    @FXML
    private Button poiskButton;
    public boolean isAlpha(String name) {
        return name.matches("[а-яА-Я]+");
    }
    @FXML
    private TextField poiskField;
    String idCar;
    String brand;
    String year;
    String volume;
    String engine;
    String priceDay;
    @FXML
    void initialize() {
        try{
            Socket socket = new Socket("localhost",12348);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.carTable.setEditable(true);
            poiskButton.setOnAction(actionEvent -> {
                this.carTable.getItems().clear();
                String Pr = poiskField.getText();
                String Pricee = priceField.getText();
                boolean flag = true;
                int rez=0;
                while(flag) {
                    try {
                        if (Integer.parseInt(Pricee) != 0 && Integer.parseInt(Pricee) > 0) {
                            flag = false;
                        } else {

                            flag = true;
                        }
                    } catch (NumberFormatException e) {
                        infoLabel.setText("Цена не число");
                        return;
                    }
                }
                if(poiskField.getText().equals("") || priceField.getText().equals("")){
                    infoLabel.setText("Цена не число");
                }
                else{
                    try {
                        writer.write("carVibor");
                        writer.newLine();
                        writer.flush();
                        writer.write(Pr);
                        writer.newLine();
                        writer.flush();
                        writer.write(Pricee);
                        writer.newLine();
                        writer.flush();
                        for (int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
                            this.idCar = reader.readLine();
                            this.brand = reader.readLine();
                            this.year = reader.readLine();
                            this.volume = reader.readLine();
                            this.engine = reader.readLine();
                            this.priceDay = reader.readLine();
                            this.carData.add(new BuilderCar().setIdCar(this.idCar).setBrand(this.brand)
                                    .setYear(this.year).setVolume(this.volume).setEngine(this.engine)
                                    .setPriceDay(this.priceDay).build());
                            this.carTable.setItems(this.carData);
                        }
                    } catch (IOException var6) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setContentText(var6.getMessage());
                        alert.showAndWait();
                    }
                    this.brandColums.setCellValueFactory(new PropertyValueFactory("brand"));
                    this.yearColums.setCellValueFactory(new PropertyValueFactory("year"));
                    this.volumeColums.setCellValueFactory(new PropertyValueFactory("volume"));
                    this.engineColums.setCellValueFactory(new PropertyValueFactory("engine"));
                    this.priceColums.setCellValueFactory(new PropertyValueFactory("priceDay"));
                }
            });
            rentButton.setOnAction(actionEvent -> {
                Car selectedItem = (Car) this.carTable.getSelectionModel().getSelectedItem();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date datee = new Date();
                String qqq = dateFormat.format(datee);
                if (selectedItem == null) {
                    infoLabel.setText("Выбор не осуществлен");
                }
                else{
                    try {
                        writer.write("rentCar");
                        writer.newLine();
                        writer.flush();
                        String Id = selectedItem.getIdCar();
                        writer.write(Id);
                        writer.newLine();
                        writer.flush();
                        String Brand = selectedItem.getBrand();
                        writer.write(Brand);
                        writer.newLine();
                        writer.flush();
                        String year = selectedItem.getYear();
                        writer.write(year);
                        writer.newLine();
                        writer.flush();
                        String priceDay = selectedItem.getPriceDay();
                        writer.write(priceDay);
                        writer.newLine();
                        writer.flush();
                        String time = qqq;
                        writer.write(time);
                        writer.newLine();
                        writer.flush();
                        writer.write(LocalDate.parse(qqq).plusDays(1).toString());
                        writer.newLine();
                        writer.flush();
                        Account account = new Account();
                        String nameUsers = account.log;
                        writer.write(nameUsers);
                        writer.newLine();
                        writer.flush();
                        infoLabel.setText("Аренда совершена");
                    } catch (IOException  var6) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setContentText(var6.getMessage());
                        alert.showAndWait();

                    }
                }
            });
            exitButton.setOnAction(actionEvent -> {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/usermenu.fxml"));
                    Stage auto = new Stage();
                    auto.initStyle(StageStyle.DECORATED);
                    auto.setScene(new Scene(root, 700, 500));
                    auto.show();
                }catch (IOException e){
                    e.printStackTrace();
                }
                try {
                    writer.close();
                    reader.close();
                    socket.close();
                } catch (IOException var6) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText(var6.getMessage());
                    alert.showAndWait();
                }
            });
        }
        catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
