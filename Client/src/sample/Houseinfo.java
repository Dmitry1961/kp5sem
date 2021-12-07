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
import sample.classes.House;
import sample.classes.RentCar;
import sample.classes.RentHouse;
import sample.classes.Timee;

public class Houseinfo {
    private ObservableList<RentHouse> houseData = FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Label infoLabel;

    @FXML
    private TableView<RentHouse> houseTable;

    @FXML
    private TableColumn<RentHouse, String> countryColums;

    @FXML
    private TableColumn<RentHouse, String> priceColums;

    @FXML
    private TableColumn<RentHouse, String> timeColums;

    @FXML
    private TableColumn<RentHouse, String> timeEndColums;

    @FXML
    private Button rentButton;

    @FXML
    String idRentHouse;
    String country;
    String priceDay;
    String time;
    String timeEnd;
    String userName;
    @FXML
    private Label priceLabel;
    @FXML
    private Label dateTime;
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
            this.houseTable.setEditable(true);
            try{
                writer.write("RentHouseInfo");
                writer.newLine();
                writer.flush();
                for(int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
                    this.idRentHouse = reader.readLine();
                    this.country = reader.readLine();
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
                    this.houseData.add(new RentHouse(this.idRentHouse, this.country, this.priceDay,
                            this.time,this.timeEnd,this.userName));
                    this.houseTable.setItems(this.houseData);
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
                RentHouse selectedItem = (RentHouse)this.houseTable.getSelectionModel().getSelectedItem();
                if(selectedItem==null){
                    infoLabel.setText("Выбор не осуществлен");
                }
                else {
                    try {
                        writer.write("deleteHouseRent");
                        writer.newLine();
                        writer.flush();
                        String ID = selectedItem.getIdRentHouse();
                        writer.write(ID);
                        writer.newLine();
                        writer.flush();
                        writer.write("Недвижимость");
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
                        this.houseTable.getItems().remove(selectedItem);
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
        this.countryColums.setCellValueFactory(new PropertyValueFactory("country"));
        this.priceColums.setCellValueFactory(new PropertyValueFactory("priceDay"));
        this.timeColums.setCellValueFactory(new PropertyValueFactory("time"));
        this.timeEndColums.setCellValueFactory(new PropertyValueFactory("timeEnd"));
    }
}
