package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Date;
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

public class Adminmenu {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Button grafButton;
    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Button userButton;
    @FXML
    private Button addHouse;
    @FXML
    private Button oreviewButton;
    @FXML
    private Button addCar;
    @FXML
    void initialize() {
        try{
            Socket socket = new Socket("localhost",12348);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Account acc = new Account();
            String accc = acc.log;
            addCar.setOnAction(actionEvent -> {
                Stage stage = (Stage) addCar.getScene().getWindow();
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
            });
            oreviewButton.setOnAction(actionEvent -> {
                try {
                    Date dateNow = new Date();
                    String otchet = "Отзыв просмотрен дата просмотра: " + dateNow.toString() + "\n\n";
                    String filePath = "Review.txt";
                    File file = new File(filePath);
                    FileWriter wr = new FileWriter(filePath, true);
                    wr.append(otchet);
                    wr.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
                try {
                    ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "Review.txt");
                    pb.start();
                } catch (IOException e) {
                    System.out.println("Файла нету");
                }
            });
            grafButton.setOnAction(actionEvent -> {
                Stage stage = (Stage) grafButton.getScene().getWindow();
                stage.close();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/grafcontroller.fxml"));
                    Stage auto = new Stage();
                    auto.initStyle(StageStyle.DECORATED);

                    auto.setScene(new Scene(root, 700, 500));
                    auto.show();
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
            addHouse.setOnAction(actionEvent -> {
                Stage stage = (Stage) addHouse.getScene().getWindow();
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
            });
            userButton.setOnAction(actionEvent -> {
                Stage stage = (Stage) userButton.getScene().getWindow();
                stage.close();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("page/usercontroller.fxml"));
                    Stage auto = new Stage();
                    auto.initStyle(StageStyle.DECORATED);

                    auto.setScene(new Scene(root, 700, 500));
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