package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.classes.Account;

public class Usermenu {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Button historyButton;

    @FXML
    private URL location;
    @FXML
    private Button renthouseButton;

    @FXML
    private Button rentcarButton;
    @FXML
    private Button graf2Button;
    @FXML
    private Button rentButton;
    @FXML
    private Button exitButton;

    @FXML
    void initialize() {
        try{
            Socket socket = new Socket("localhost",12348);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Account acc = new Account();
            String accc = acc.log;
            renthouseButton.setOnAction(actionEvent -> {
                Stage stage = (Stage) renthouseButton.getScene().getWindow();
                stage.close();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/renthousecontroller.fxml"));
                    Stage auto = new Stage();
                    auto.initStyle(StageStyle.DECORATED);

                    auto.setScene(new Scene(root, 700, 500));
                    auto.show();
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
            rentButton.setOnAction(actionEvent -> {
                Stage stage = (Stage) rentButton.getScene().getWindow();
                stage.close();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/rentcontroller.fxml"));
                    Stage auto = new Stage();
                    auto.initStyle(StageStyle.DECORATED);

                    auto.setScene(new Scene(root, 700, 500));
                    auto.show();
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
            rentcarButton.setOnAction(actionEvent -> {
                Stage stage = (Stage) rentcarButton.getScene().getWindow();
                stage.close();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/rentcarcontroller.fxml"));
                    Stage auto = new Stage();
                    auto.initStyle(StageStyle.DECORATED);

                    auto.setScene(new Scene(root, 700, 500));
                    auto.show();
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
            graf2Button.setOnAction(actionEvent -> {
                Stage stage = (Stage) graf2Button.getScene().getWindow();
                stage.close();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/popularcontroller.fxml"));
                    Stage auto = new Stage();
                    auto.initStyle(StageStyle.DECORATED);

                    auto.setScene(new Scene(root, 700, 500));
                    auto.show();
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
            historyButton.setOnAction(actionEvent -> {
                Stage stage = (Stage) historyButton.getScene().getWindow();
                stage.close();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/history.fxml"));
                    Stage auto = new Stage();
                    auto.initStyle(StageStyle.DECORATED);

                    auto.setScene(new Scene(root, 700, 400));
                    auto.show();
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
            exitButton.setOnAction(actionEvent -> {
                Stage stage = (Stage) exitButton.getScene().getWindow();
                stage.close();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/sample.fxml"));
                    Stage auto = new Stage();
                    auto.initStyle(StageStyle.DECORATED);
                    auto.setScene(new Scene(root, 700, 450));
                    auto.show();
                }catch (IOException e){
                    e.printStackTrace();
                }
                try {
                    writer.write("exitaccount");
                    writer.newLine();
                    writer.flush();
                    writer.write(accc);
                    writer.newLine();
                    writer.flush();
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