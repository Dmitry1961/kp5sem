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

public class RentController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Button renthouseButton;

    @FXML
    private Button renthouseButton1;

    @FXML
    void initialize() {
        try {
            Socket socket = new Socket("localhost", 12348);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
            renthouseButton.setOnAction(actionEvent -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/houseinfo.fxml"));
                    Stage auto = new Stage();
                    auto.initStyle(StageStyle.DECORATED);

                    auto.setScene(new Scene(root, 700, 300));
                    auto.show();
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
            renthouseButton1.setOnAction(actionEvent -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/carinfo.fxml"));
                    Stage auto = new Stage();
                    auto.initStyle(StageStyle.DECORATED);

                    auto.setScene(new Scene(root, 700, 300));
                    auto.show();
                }catch (IOException e){
                    e.printStackTrace();
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
