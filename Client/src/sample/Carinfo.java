package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.classes.Car;
import sample.classes.RentCar;
import sample.classes.RentHouse;
import sample.classes.Timee;

public class Carinfo {
    private ObservableList<RentCar> carData = FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Label infoLabel;

    @FXML
    private TableView<RentCar> carTable;

    @FXML
    private TableColumn<RentCar, String> brandColums;

    @FXML
    private TableColumn<RentCar, String> yearColums;

    @FXML
    private TableColumn<RentCar, String> priceColums;

    @FXML
    private TableColumn<RentCar, String> timeColums;

    @FXML
    private TableColumn<RentCar, String> timeEndColums;

    @FXML
    private Button rentButton;

    @FXML
    private Label dateTime;

    @FXML
    private Label priceLabel;
    String idRentCar;
    String brand;
    String year;
    String priceDay;
    String time;
    String timeEnd;
    String userName;
    int prosroch=0;
    @FXML
    void initialize() {
        try {
            Socket socket = new Socket("localhost", 12348);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Timee timee = new Timee();
            Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                timee.dateTimee = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
                dateTime.setText(timee.dateTimee);
            }), new KeyFrame(Duration.seconds(1)));

            clock.setCycleCount(Animation.INDEFINITE);
            clock.play();
            this.carTable.setEditable(true);
            try{
                writer.write("RentCarInfo");
                writer.newLine();
                writer.flush();
                for(int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
                    this.idRentCar = reader.readLine();
                    this.brand = reader.readLine();
                    this.year = reader.readLine();
                    this.priceDay = reader.readLine();
                    this.time = reader.readLine();
                    this.timeEnd = reader.readLine();
                    this.userName = reader.readLine();
                    String index10 = timeEnd.substring(0, 4);
                    String index11 = timeEnd.substring(5, 7);
                    String index12 = timeEnd.substring(8, 10);
                    String q = index10 + index11 + index12;
                    int num = Integer.parseInt(q);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date datee = new Date();
                    String qqq = dateFormat.format(datee);
                    String index1 = qqq.substring(0, 4);
                    String index2 = qqq.substring(5, 7);
                    String index3 = qqq.substring(8, 10);
                    String w = index1 + index2 + index3;
                    int num2 = Integer.parseInt(w);
                    if(num2 < num) {

                    }
                    else {
                        int razn = num2 - num;
                        prosroch += 20 * razn;
                    }
                    this.carData.add(new RentCar(this.idRentCar, this.brand, this.year,
                            this.priceDay,this.time,this.timeEnd,this.userName));
                    this.carTable.setItems(this.carData);
                }

            }catch (IOException var6) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText(var6.getMessage());
                alert.showAndWait();
            }
            String text =  String.valueOf(prosroch);
            priceLabel.setText("Общая просрочки=" + text);
            rentButton.setOnAction(actionEvent -> {
                RentCar selectedItem = (RentCar)this.carTable.getSelectionModel().getSelectedItem();
                if(selectedItem==null){
                    infoLabel.setText("Выбор не осуществлен");
                }
                else {
                    try {
                        writer.write("deleteCarRent");
                        writer.newLine();
                        writer.flush();
                        String ID = selectedItem.getIdRentCar();
                        writer.write(ID);
                        writer.newLine();
                        writer.flush();
                        writer.write("Транспорт");
                        writer.newLine();
                        writer.flush();
                        String Price = selectedItem.getPriceDay();
                        writer.write(Price);
                        writer.newLine();
                        writer.flush();
                        writer.write(text);
                        writer.newLine();
                        writer.flush();
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date datee = new Date();
                        String qqq = dateFormat.format(datee);
                        writer.write(qqq);
                        writer.newLine();
                        writer.flush();
                        String nameUser = selectedItem.getUserName();
                        writer.write(nameUser);
                        writer.newLine();
                        writer.flush();
                        this.carTable.getItems().remove(selectedItem);
                    } catch (IOException var6) {
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
        this.brandColums.setCellValueFactory(new PropertyValueFactory("brand"));
        this.yearColums.setCellValueFactory(new PropertyValueFactory("year"));
        this.priceColums.setCellValueFactory(new PropertyValueFactory("priceDay"));
        this.timeColums.setCellValueFactory(new PropertyValueFactory("time"));
        this.timeEndColums.setCellValueFactory(new PropertyValueFactory("timeEnd"));
    }
}

