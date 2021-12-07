package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.classes.Account;

import static java.lang.System.exit;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button buttonAutoriz;

    @FXML
    private Label infoLabel;

    @FXML
    private Button buttonRegistr;

    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button exitButton;
    String nicknamee;
    String loginn;
    String passwordd;
    @FXML
    void initialize() {
        try{
            Socket socket = new Socket("localhost",12348);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            try {
                writer.write("CreateBd");
                writer.newLine();
                writer.flush();
            } catch (IOException var6) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText(var6.getMessage());
                alert.showAndWait();
            }
            buttonAutoriz.setOnAction(actionEvent -> {
                String loginnn = LoginField.getText().trim();
                String passworddd = PasswordField.getText().trim();
                try{
                    writer.write("authorization");
                    writer.newLine();
                    writer.flush();
                    for(int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
                        this.nicknamee = reader.readLine();
                        this.loginn = reader.readLine();
                        this.passwordd = reader.readLine();
                        if (!loginnn.equals("") && !passworddd.equals("")) {
                            if (loginnn.equals(loginn) && passworddd.equals(passwordd)) {
                                if (loginn.equals("admin") && passwordd.equals("admin")) {
                                    Account acc = new Account();
                                    acc.log = loginn;
                                    writer.write("account");
                                    writer.newLine();
                                    writer.flush();
                                    writer.write(loginn);
                                    writer.newLine();
                                    writer.flush();
                                    Stage stage = (Stage) buttonAutoriz.getScene().getWindow();
                                    stage.close();
                                    try {
                                        Parent root = FXMLLoader.load(getClass().getResource("page/adminmenu.fxml"));
                                        Stage auto = new Stage();
                                        auto.initStyle(StageStyle.DECORATED);

                                        auto.setScene(new Scene(root, 700, 500));
                                        auto.show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }else{
                                    Account acc = new Account();
                                    acc.log = loginn;
                                    writer.write("account");
                                    writer.newLine();
                                    writer.flush();
                                    writer.write(loginn);
                                    writer.newLine();
                                    writer.flush();
                                    Stage stage = (Stage) buttonAutoriz.getScene().getWindow();
                                    stage.close();
                                    try {
                                        Parent root = FXMLLoader.load(getClass().getResource("page/usermenu.fxml"));
                                        Stage auto = new Stage();
                                        auto.initStyle(StageStyle.DECORATED);

                                        auto.setScene(new Scene(root, 700, 500));
                                        auto.show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }else{
                                infoLabel.setText("Данные введены неверно");
                            }
                        }else
                        {
                            infoLabel.setText("Данные введены неверно");
                        }
                    }
                }catch (IOException var6) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error");
                    alert.setContentText(var6.getMessage());
                    alert.showAndWait();
                }
            });
            buttonRegistr.setOnAction(actionEvent -> {
                buttonRegistr.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("page/registration.fxml"));
                try {
                    loader.load();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            });
            exitButton.setOnAction(actionEvent -> {
                try {
                    writer.write("exit");
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
                exit(1);

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

