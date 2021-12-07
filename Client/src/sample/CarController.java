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
import sample.patternBuilder.BuilderC;
import sample.patternBuilder.BuilderCar;

public class CarController {
    private ObservableList<Car> carData = FXCollections.observableArrayList();
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
    private ComboBox brandField;

    @FXML
    private TextField yearField;

    @FXML
    private ComboBox volumeField;

    @FXML
    private ComboBox engineField;

    @FXML
    private TextField priceField;

    @FXML
    private Button deleteButton;

    @FXML
    private Button redactButton;

    @FXML
    private Button sortButton;

    @FXML
    private Button poiskButton;

    @FXML
    private TextField poiskField;

    @FXML
    private Button resetButton;
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
            ObservableList<String> brandd = FXCollections.observableArrayList("Audi", "BMW","Ford","Honda","Hyundai","Kia","Lada","Mazda","Mercedes-Benz","Nissan","Skoda");
            ObservableList<String> volumee = FXCollections.observableArrayList("1.0", "1.2","1.4","1.6","1.8","2.0", "2.2","2.4","2.6","2.8"
                    ,"3.0", "3.2","3.4","3.6","3.8","4.0", "4.2","4.4","4.6","4.8", "5.0", "5.2","5.4","5.6","5.8","6.0", "6.2","6.4","6.6","6.8"
                    ,"7.0", "7.2","7.4","7.6","7.8","8.0", "8.2","8.4","8.6","8.8","9.0");
            ObservableList<String> enginee = FXCollections.observableArrayList("Бензиновый", "Электрический","Дизельный","Гибрид-бензин","Гибрид-дизель");
            engineField.setItems(enginee);
            volumeField.setItems(volumee);
            brandField.setItems(brandd);
            this.carTable.setEditable(true);
            try{
                writer.write("CarInfo");
                writer.newLine();
                writer.flush();
                for(int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
                    this.idCar = reader.readLine();
                    this.brand = reader.readLine();
                    this.year = reader.readLine();
                    this.volume = reader.readLine();
                    this.engine = reader.readLine();
                    this.priceDay = reader.readLine();
                    BuilderC builderC = new BuilderCar();
                    this.carData.add(new BuilderCar().setIdCar(this.idCar).setBrand(this.brand)
                            .setYear(this.year).setVolume(this.volume).setEngine(this.engine)
                            .setPriceDay(this.priceDay).build());
                    this.carTable.setItems(this.carData);
                }
            }catch (IOException var6) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText(var6.getMessage());
                alert.showAndWait();
            }
            sortButton.setOnAction(actionEvent -> {
                Comparator<Car> comparator = Comparator.comparing(Car::getPriceDay);
                comparator = comparator.thenComparing(Car::getPriceDay);
                FXCollections.sort(carData, comparator);
            });
            resetButton.setOnAction(actionEvent -> {
                Stage stage = (Stage) resetButton.getScene().getWindow();
                stage.close();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/carcontroller.fxml"));
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
                String Yearr = yearField.getText();
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
                        infoLabel.setText("Дата не число");
                        return;
                    }
                }
                if(yearField.getText().equals("") || priceField.getText().equals("")){
                    infoLabel.setText("Проверьте ввод");
                }
                else {
                    String Brand =  brandField.getSelectionModel().getSelectedItem().toString();
                    String Year = yearField.getText();
                    String Volume = volumeField.getSelectionModel().getSelectedItem().toString();
                    String Engine = engineField.getSelectionModel().getSelectedItem().toString();
                    String Price = priceField.getText();
                    int rez1 = Integer.parseInt(Year);
                    int rez2 = Integer.parseInt(Price);

                    if (Brand.equals("") || Year.equals("") ||
                            Volume.equals("") || Engine.equals("") || Price.equals("")) {
                        infoLabel.setText("Проверьте ввод");
                    }

                    else if(rez1 < 1990){
                        infoLabel.setText("Год меньше 1990");
                    }
                    else if(rez2 < 50){
                        infoLabel.setText("Цена меньше 50");
                    }

                    else {
                        this.carTable.setItems(this.carData);
                        this.yearField.setText((String) null);
                        this.priceField.setText((String) null);
                        try {
                            writer.write("addCar");
                            writer.newLine();
                            writer.flush();
                            writer.write(Brand);
                            writer.newLine();
                            writer.flush();
                            writer.write(Year);
                            writer.newLine();
                            writer.flush();
                            writer.write(Volume);
                            writer.newLine();
                            writer.flush();
                            writer.write(Engine);
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
            redactButton.setOnAction(actionEvent -> {
                this.yearColums.setCellFactory(TextFieldTableCell.forTableColumn());
                this.yearColums.setOnEditCommit((t) -> {
                    String cell = ((Car)t.getRowValue()).getIdCar();
                    ((Car)t.getTableView().getItems().get(t.getTablePosition().getRow())).setYear((String)t.getNewValue());
                    String Name = (String)t.getNewValue();
                    boolean flag = true;
                    int rez=0;
                    while(flag) {
                        try {
                            if (Integer.parseInt(Name) != 0 && Integer.parseInt(Name) > 0) {
                                flag = false;
                            } else {

                                flag = true;
                            }
                        } catch (NumberFormatException e) {
                            infoLabel.setText("Год не число");
                            return;
                        }
                    }
                    if(Name.equals("")){
                        infoLabel.setText("Проверьте ввод");
                    }
                    else {
                        try {
                            writer.write("editYearCar");
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
                this.priceColums.setCellFactory(TextFieldTableCell.forTableColumn());
                this.priceColums.setOnEditCommit((t) -> {
                    String cell = ((Car)t.getRowValue()).getIdCar();
                    ((Car)t.getTableView().getItems().get(t.getTablePosition().getRow())).setPriceDay((String)t.getNewValue());
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
                            infoLabel.setText("Цена не число");
                            return;
                        }
                    }
                    try {
                        writer.write("editPriceCar");
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
            });
            deleteButton.setOnAction(actionEvent -> {
                Car selectedItem = (Car)this.carTable.getSelectionModel().getSelectedItem();
                if(selectedItem==null){
                    infoLabel.setText("Выбор не осуществлен");
                }
                else {
                    try {
                        writer.write("deleteCar");
                        writer.newLine();
                        writer.flush();
                        String ID = selectedItem.getIdCar();
                        writer.write(ID);
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
            poiskButton.setOnAction(actionEvent -> {
                if(this.poiskField.getText().trim().equals("")){
                    infoLabel.setText("Задайте поиск");
                }
                else{
                    this.carTable.getItems().clear();
                    String Poisk = this.poiskField.getText().trim();
                    try{
                        writer.write("CarPoisk");
                        writer.newLine();
                        writer.flush();
                        writer.write(Poisk);
                        writer.newLine();
                        writer.flush();
                        for(int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
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
                    }catch (IOException var6) {
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
        this.brandColums.setCellValueFactory(new PropertyValueFactory("brand"));
        this.yearColums.setCellValueFactory(new PropertyValueFactory("year"));
        this.volumeColums.setCellValueFactory(new PropertyValueFactory("volume"));
        this.engineColums.setCellValueFactory(new PropertyValueFactory("engine"));
        this.priceColums.setCellValueFactory(new PropertyValueFactory("priceDay"));
    }
}