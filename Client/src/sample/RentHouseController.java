package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
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
import sample.classes.House;
import sample.patternBuilder.BuilderHouse;

public class RentHouseController {
    private ObservableList<House> houseData = FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Label infoLabel;

    @FXML
    private TableView<House> houseTable;

    @FXML
    private TableColumn<House,String> countryColums;

    @FXML
    private TableColumn<House,String> typeColums;

    @FXML
    private TableColumn<House,String> countroomColums;

    @FXML
    private TableColumn<House,String> festiveColums;

    @FXML
    private TableColumn<House,String> priceColums;

    @FXML
    private TextField countryField;

    @FXML
    private Button poiskButton;

    @FXML
    private Button resetButton;
    public boolean isAlpha(String name) {
        return name.matches("[а-яА-Я]+");
    }
    @FXML
    private CheckBox prazdnik;

    @FXML
    private Button rentButton;
    String idHouse;
    String country;
    String type;
    String countRoom;
    String festive;
    String priceDay;
    @FXML
    void initialize() {
        try {
            Socket socket = new Socket("localhost", 12348);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.houseTable.setEditable(true);
            poiskButton.setOnAction(actionEvent -> {
                this.houseTable.getItems().clear();
                String Pr = countryField.getText();
                if(!isAlpha(Pr)){
                    infoLabel.setText("Ошибка ввода");
                    return;
                }
                if(countryField.getText().equals("")){
                    infoLabel.setText("Ошибка ввода");
                }
                else{
                    try {
                        String Country = countryField.getText().trim();
                        String praz;
                        if(prazdnik.isSelected()){
                            praz = "Да";
                        }
                        else{
                            praz = "Нет";
                        }
                        writer.write("houseVibor");
                        writer.newLine();
                        writer.flush();
                        writer.write(Country);
                        writer.newLine();
                        writer.flush();
                        writer.write(praz);
                        writer.newLine();
                        writer.flush();
                        for (int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
                            this.idHouse = reader.readLine();
                            this.country = reader.readLine();
                            this.type = reader.readLine();
                            this.countRoom = reader.readLine();
                            this.festive = reader.readLine();
                            this.priceDay = reader.readLine();
                            this.houseData.add(new BuilderHouse().setIdHouse(this.idHouse).setCountry(this.country).setType(this.type)
                                    .setCountRoom(this.countRoom).setFestive(this.festive).setPriceDay(this.priceDay).build());
                            this.houseTable.setItems(this.houseData);
                        }
                    } catch (IOException var6) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setContentText(var6.getMessage());
                        alert.showAndWait();
                    }
                    this.countryColums.setCellValueFactory(new PropertyValueFactory("country"));
                    this.typeColums.setCellValueFactory(new PropertyValueFactory("type"));
                    this.countroomColums.setCellValueFactory(new PropertyValueFactory("countRoom"));
                    this.festiveColums.setCellValueFactory(new PropertyValueFactory("festive"));
                    this.priceColums.setCellValueFactory(new PropertyValueFactory("priceDay"));

                }
            });
            rentButton.setOnAction(actionEvent -> {
                House selectedItem = (House) this.houseTable.getSelectionModel().getSelectedItem();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date datee = new Date();
                String qqq = dateFormat.format(datee);
                if (selectedItem == null) {
                    infoLabel.setText("Выбор не осуществлен");
                }
                else{
                    try {
                        writer.write("rentHouse");
                        writer.newLine();
                        writer.flush();
                        String Id = selectedItem.getIdHouse();
                        writer.write(Id);
                        writer.newLine();
                        writer.flush();
                        String Country = selectedItem.getCountry();
                        writer.write(Country);
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