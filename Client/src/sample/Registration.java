package sample;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.classes.User;
import sample.patternIterator.Iterator;

public class Registration {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonRegistr;

    @FXML
    private Label infoLabel;

    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField RPasswordField;

    @FXML
    private TextField usernameField;

    @FXML
    private Button exitButton;

    @FXML
    private PasswordField PasswordField;
    String idUser;
    String username;
    String login;
    String password;
    @FXML
    void initialize() {
        try{
            User user = new User();
            Socket socket = new Socket("localhost",12348);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            buttonRegistr.setOnAction(actionEvent -> {
                String username = this.usernameField.getText().trim();
                String login = this.LoginField.getText().trim();
                String password = this.PasswordField.getText().trim();
                String rpassword = this.RPasswordField.getText().trim();
                int i=0;
                try {
                    writer.write("authorization");
                    writer.newLine();
                    writer.flush();
                    for(int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {

                        this.username = reader.readLine();
                        this.login = reader.readLine();
                        this.password = reader.readLine();
                      i++;
                    }
                }catch (IOException var6){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText(var6.getMessage());
                    alert.showAndWait();
                }
                String[] mass = new String[i];
                int b=0;
                try {
                    writer.write("authorization");
                    writer.newLine();
                    writer.flush();
                    for(int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {

                        this.username = reader.readLine();
                        this.login = reader.readLine();
                        this.password = reader.readLine();
                        mass[b] = this.login;
                        b++;
                    }
                }catch (IOException var6){
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText(var6.getMessage());
                    alert.showAndWait();
                }
                user.setLog(mass);
                Iterator iterator = user.getIterator();
                while(iterator.hasNext()){
                    if(iterator.next().toString().equals(login)){
                        infoLabel.setText("Повтор логина невозможен");
                        return;
                    }
                    else{

                    }
                }
                //for(String person : user.log){
                //    if(login.equals(person)){
                //        infoLabel.setText("Повтор логина невозможен");
                //        return;
                //    }
                //}
                if(username.equals("") || login.equals("") || password.equals("")){
                    infoLabel.setText("Ошибка ввода данных");
                }
                else if(!password.equals(rpassword)){
                    infoLabel.setText("Пароли не совпадают");
                }
                else {
                    try {
                        writer.write("registration");
                        writer.newLine();
                        writer.flush();
                        writer.write(username);
                        writer.newLine();
                        writer.flush();
                        writer.write(login);
                        writer.newLine();
                        writer.flush();
                        writer.write(password);
                        writer.newLine();
                        writer.flush();
                        infoLabel.setText("Пользователь " + username + " зарегистрирован");
                    } catch (IOException var11) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setContentText(var11.getMessage());
                        alert.showAndWait();
                    }
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
