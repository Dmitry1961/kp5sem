package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.classes.Car;
import sample.classes.House;
import sample.patternBuilder.BuilderHouse;

public class HouseController {
    private ObservableList<House> houseData = FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label infoLabel;

    @FXML
    private TableView<House> houseTable;

    @FXML
    private TableColumn<House, String> countryColums;

    @FXML
    private TableColumn<House, String> typeColums;

    @FXML
    private TableColumn<House, String> countroomColums;

    @FXML
    private TableColumn<House, String> festiveColums;

    @FXML
    private TableColumn<House, String> priceColums;

    @FXML
    private ComboBox typeField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField priceField;

    @FXML
    private Button deleteButton;

    @FXML
    private Button redactButton;

    @FXML
    private Button poiskButton;
    public boolean isAlpha(String name) {
        return name.matches("[а-яА-Я]+");
    }
    @FXML
    private Button resetButton;

    @FXML
    private TextField countroomField;

    @FXML
    private CheckBox prazdnik;
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
            ObservableList<String> enginee = FXCollections.observableArrayList("Дом","Квартира");
            typeField.setItems(enginee);
            this.houseTable.setEditable(true);
            try{
                writer.write("HouseInfo");
                writer.newLine();
                writer.flush();
                for(int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
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
            }catch (IOException var6) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText(var6.getMessage());
                alert.showAndWait();
            }

            resetButton.setOnAction(actionEvent -> {
                Stage stage = (Stage) resetButton.getScene().getWindow();
                stage.close();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/housecontroller.fxml"));
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
            addButton.setOnAction(actionEvent -> {
                String Pricee = priceField.getText();
                String Yearr = countroomField.getText();
                String Pr = countryField.getText();
                if(!isAlpha(Pr)){
                    infoLabel.setText("Ошибка ввода");
                    return;
                }
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
                flag = true;
                int rez10=0;
                while(flag) {
                    try {
                        if (Integer.parseInt(Yearr) != 0 && Integer.parseInt(Yearr) > 0) {
                            flag = false;
                        } else {

                            flag = true;
                        }
                    } catch (NumberFormatException e) {
                        infoLabel.setText("Колл комнат не число");
                        return;
                    }
                }
                if(countryField.getText().equals("") || priceField.getText().equals("") || countroomField.getText().equals("")){
                    infoLabel.setText("Проверьте ввод");
                }
                else {
                    String Country =  countryField.getText();
                    String Type = typeField.getSelectionModel().getSelectedItem().toString();
                    String Countryroom = countroomField.getText();
                    String Typee;
                    if(prazdnik.isSelected()){
                        Typee = "Да";
                    }
                    else{
                        Typee="Нет";
                    }
                    String Price = priceField.getText();
                    int rez1 = Integer.parseInt(Countryroom);
                    int rez2 = Integer.parseInt(Price);

                    if (Country.equals("") || Countryroom.equals("") ||
                            Price.equals("") ) {
                        infoLabel.setText("Проверьте ввод");
                    }

                    else if(rez1 < 1 || rez1>10){
                        infoLabel.setText("Колл комнат неверно");
                    }
                    else if(rez2 < 10){
                        infoLabel.setText("Цена меньше 10");
                    }

                    else {
                        this.houseTable.setItems(this.houseData);
                        this.countryField.setText((String) null);
                        this.countroomField.setText((String) null);
                        this.priceField.setText((String) null);
                        try {
                            writer.write("addHouse");
                            writer.newLine();
                            writer.flush();
                            writer.write(Country);
                            writer.newLine();
                            writer.flush();
                            writer.write(Type);
                            writer.newLine();
                            writer.flush();
                            writer.write(Countryroom);
                            writer.newLine();
                            writer.flush();
                            writer.write(Typee);
                            writer.newLine();
                            writer.flush();
                            writer.write(Price);
                            writer.newLine();
                            writer.flush();
                            infoLabel.setText("Добавлено");
                        } catch (IOException var11) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setContentText(var11.getMessage());
                            alert.showAndWait();
                        }
                    }
                }
            });
            deleteButton.setOnAction(actionEvent -> {
                House selectedItem = (House)this.houseTable.getSelectionModel().getSelectedItem();
                if(selectedItem==null){
                    infoLabel.setText("Выбор не осуществлен");
                }
                else {
                    try {
                        writer.write("deleteHouse");
                        writer.newLine();
                        writer.flush();
                        String ID = selectedItem.getIdHouse();
                        writer.write(ID);
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
            poiskButton.setOnAction(actionEvent -> {


                    this.houseTable.getItems().clear();
                    String Poisk = "Да";
                    try{
                        writer.write("HousePoisk");
                        writer.newLine();
                        writer.flush();
                        writer.write(Poisk);
                        writer.newLine();
                        writer.flush();
                        for(int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
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
                    }catch (IOException var6) {
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


            });
            redactButton.setOnAction(actionEvent -> {
                this.countryColums.setCellFactory(TextFieldTableCell.forTableColumn());
                this.countryColums.setOnEditCommit((t) -> {
                    String cell = ((House)t.getRowValue()).getIdHouse();
                    ((House)t.getTableView().getItems().get(t.getTablePosition().getRow())).setCountry((String)t.getNewValue());
                    String Name = (String)t.getNewValue();
                    if(Name.equals("")){
                        infoLabel.setText("Проверьте ввод");
                    }
                    else {
                        try {
                            writer.write("editCountryHouse");
                            writer.newLine();
                            writer.flush();
                            writer.write(Name);
                            writer.newLine();
                            writer.flush();
                            writer.write(cell);
                            writer.newLine();
                            writer.flush();
                            infoLabel.setText("");
                        } catch (IOException var6) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setContentText(var6.getMessage());
                            alert.showAndWait();
                        }
                    }
                });
                this.countroomColums.setCellFactory(TextFieldTableCell.forTableColumn());
                this.countroomColums.setOnEditCommit((t) -> {
                    String cell = ((House)t.getRowValue()).getIdHouse();
                    ((House)t.getTableView().getItems().get(t.getTablePosition().getRow())).setCountRoom((String)t.getNewValue());
                    String Price = (String)t.getNewValue();
                    boolean flag = true;
                    int rez=0;
                    while(flag) {
                        try {
                            if (Integer.parseInt(Price) != 0 && Integer.parseInt(Price) > 0) {
                                flag = false;
                            } else {

                                flag = true;
                            }
                        } catch (NumberFormatException e) {
                            infoLabel.setText("Колличество не число");
                            return;
                        }
                    }
                    try {
                        writer.write("editRoomHouse");
                        writer.newLine();
                        writer.flush();
                        writer.write(Price);
                        writer.newLine();
                        writer.flush();
                        writer.write(cell);
                        writer.newLine();
                        writer.flush();
                    } catch (IOException var6) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setContentText(var6.getMessage());
                        alert.showAndWait();
                    }

                });
                this.priceColums.setCellFactory(TextFieldTableCell.forTableColumn());
                this.priceColums.setOnEditCommit((t) -> {
                    String cell = ((House)t.getRowValue()).getIdHouse();
                    ((House)t.getTableView().getItems().get(t.getTablePosition().getRow())).setPriceDay((String)t.getNewValue());
                    String Pricee = (String)t.getNewValue();
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
                    try {
                        writer.write("editPriceHouse");
                        writer.newLine();
                        writer.flush();
                        writer.write(Pricee);
                        writer.newLine();
                        writer.flush();
                        writer.write(cell);
                        writer.newLine();
                        writer.flush();
                    } catch (IOException var6) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setContentText(var6.getMessage());
                        alert.showAndWait();
                    }

                });
            });
            exitButton.setOnAction(actionEvent -> {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/adminmenu.fxml"));
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
        this.countryColums.setCellValueFactory(new PropertyValueFactory("country"));
        this.typeColums.setCellValueFactory(new PropertyValueFactory("type"));
        this.countroomColums.setCellValueFactory(new PropertyValueFactory("countRoom"));
        this.festiveColums.setCellValueFactory(new PropertyValueFactory("festive"));
        this.priceColums.setCellValueFactory(new PropertyValueFactory("priceDay"));
    }
}
