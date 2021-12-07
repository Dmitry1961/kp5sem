package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
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
import sample.classes.Income;
import sample.patternBuilder.BuilderHouse;

public class HistoryInfo {
    private ObservableList<Income> incomesData = FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Label infoLabel;

    @FXML
    private TableView<Income> houseTable;

    @FXML
    private TableColumn<Income, String> nameColums;

    @FXML
    private TableColumn<Income, String> priceColums;

    @FXML
    private TableColumn<Income, String> prColums;

    @FXML
    private TableColumn<Income, String> timeEndColums;

    @FXML
    private Button rentButton;

    @FXML
    private Label dateTime;
    String idIncome;
    String nameRent;
    String price;
    String prosrosh;
    String datee;
    String username;
    String text = "";
    @FXML
    void initialize() {
        try{
            Socket socket = new Socket("localhost",12348);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Account acc = new Account();
            String accc = acc.log;
            text += "История арендованных средств, в лице пользователя: " + accc + "\n";
            this.houseTable.setEditable(true);
            try{
                writer.write("History");
                writer.newLine();
                writer.flush();
                writer.write(accc);
                writer.newLine();
                writer.flush();
                for(int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
                    this.idIncome = reader.readLine();
                    this.nameRent = reader.readLine();
                    this.price = reader.readLine();
                    this.prosrosh = reader.readLine();
                    this.datee = reader.readLine();
                    this.username = reader.readLine();
                    text += "Наименование: " + this.nameRent + ",Цена: " + price + ",Просрочка срока: " + prosrosh + ",Дата: " + datee + "\n";
                    this.incomesData.add(new Income(this.idIncome,this.nameRent,this.price,this.prosrosh, this.datee,this.username));
                    this.houseTable.setItems(this.incomesData);
                }
            }catch (IOException var6) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText(var6.getMessage());
                alert.showAndWait();
            }
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
            rentButton.setOnAction(actionEvent -> {
                try {
                    String filePath = accc + ".txt";
                    File file = new File(filePath);
                    infoLabel.setText("Готово");
                    FileWriter wr = new FileWriter(filePath, true);
                    wr.append(text);
                    wr.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            });
        }
        catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        this.nameColums.setCellValueFactory(new PropertyValueFactory("nameRent"));
        this.priceColums.setCellValueFactory(new PropertyValueFactory("price"));
        this.prColums.setCellValueFactory(new PropertyValueFactory("prosrosh"));
        this.timeEndColums.setCellValueFactory(new PropertyValueFactory("datee"));
    }
}
