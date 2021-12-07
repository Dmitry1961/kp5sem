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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.classes.User;

public class UserController {
    private ObservableList<User> userData = FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label infoLabel;

    @FXML
    private Button exitButton;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> idColums;

    @FXML
    private TableColumn<User, String> usernameColums;

    @FXML
    private TableColumn<User, String> loginColums;

    @FXML
    private TableColumn<User, String> passwordColums;

    @FXML
    private Button deleteButton;
    @FXML
    private Button redactButton;


    String idUser;
    String username;
    String login;
    String password;
    @FXML
    void initialize() {
        try{
            Socket socket = new Socket("localhost",12348);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.userTable.setEditable(true);
            try{
                writer.write("readUsers");
                writer.newLine();
                writer.flush();
                for(int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
                    this.idUser = reader.readLine();
                    this.username = reader.readLine();
                    this.login = reader.readLine();
                    this.password = reader.readLine();
                    if(this.login.equals("admin") || this.password.equals("admin")){
                    }
                    else {

                        this.userData.add(new User(this.idUser, this.username, this.login, this.password));
                        this.userTable.setItems(this.userData);
                    }
                }
            }catch (IOException var6) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText(var6.getMessage());
                alert.showAndWait();
            }
            deleteButton.setOnAction(actionEvent -> {
                User selectedItem = (User)this.userTable.getSelectionModel().getSelectedItem();
                if(selectedItem==null){
                    System.out.println("Выбор не осуществлен");
                }
                else {
                    String b;
                    try {
                        writer.write("deleteUser");
                        writer.newLine();
                        writer.flush();
                        String ID = selectedItem.getIdUser();
                        writer.write(ID);
                        writer.newLine();
                        writer.flush();
                        infoLabel.setText("");
                        b = reader.readLine();
                        if (b.equals("0")) {
                            this.userTable.getItems().remove(selectedItem);
                        } else if (b.equals("1")) {
                            System.out.println("Админа удалить нельзя");
                        }
                    } catch (IOException var6) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error");
                        alert.setContentText(var6.getMessage());
                        alert.showAndWait();
                    }
                }
            });
            redactButton.setOnAction(actionEvent -> {
                this.usernameColums.setCellFactory(TextFieldTableCell.forTableColumn());
                this.usernameColums.setOnEditCommit((t) -> {
                    String b;
                    String cell = ((User)t.getRowValue()).getIdUser();
                    ((User)t.getTableView().getItems().get(t.getTablePosition().getRow())).setUsername((String)t.getNewValue());
                    String userName = (String)t.getNewValue();
                    if(userName.equals("")){
                        infoLabel.setText("Проверьте ввод");
                    }
                    else {
                        try {
                            writer.write("editUsername");
                            writer.newLine();
                            writer.flush();
                            writer.write(userName);
                            writer.newLine();
                            writer.flush();
                            writer.write(cell);
                            writer.newLine();
                            writer.flush();
                            infoLabel.setText("");
                            b = reader.readLine();
                            if (b.equals("0")) {
                                System.out.println("Редактирование прошло успешно");
                            } else if (b.equals("1")) {
                                System.out.println("Админа редактировать нельзя");
                            }
                        } catch (IOException var6) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setContentText(var6.getMessage());
                            alert.showAndWait();
                        }
                    }
                });
                this.loginColums.setCellFactory(TextFieldTableCell.forTableColumn());
                this.loginColums.setOnEditCommit((t) -> {
                    String b;
                    String cell = ((User)t.getRowValue()).getIdUser();
                    ((User)t.getTableView().getItems().get(t.getTablePosition().getRow())).setLogin((String)t.getNewValue());
                    String Capital = (String)t.getNewValue();
                    if(Capital.equals("")){
                        infoLabel.setText("Проверьте ввод");
                    }
                    else {
                        String loginn = Capital;
                        try {
                            writer.write("authorization");
                            writer.newLine();
                            writer.flush();
                            for (int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
                                this.username = reader.readLine();
                                this.login = reader.readLine();
                                this.password = reader.readLine();
                                if (loginn.equals(login)) {
                                    break;
                                }
                            }
                        } catch (IOException var6) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setContentText(var6.getMessage());
                            alert.showAndWait();
                        }
                        if (Capital.equals(login)) {
                            System.out.println("Повтор логина невозможен");
                            infoLabel.setText("Повтор логина невозможен");
                        } else {
                            try {
                                writer.write("editLogin");
                                writer.newLine();
                                writer.flush();
                                writer.write(Capital);
                                writer.newLine();
                                writer.flush();
                                writer.write(cell);
                                writer.newLine();
                                writer.flush();
                                infoLabel.setText("");
                                b = reader.readLine();
                                if (b.equals("0")) {
                                    System.out.println("Редактирование прошло успешно");
                                } else if (b.equals("1")) {
                                    System.out.println("Админа редактировать нельзя");
                                }
                            } catch (IOException var6) {
                                Alert alert = new Alert(Alert.AlertType.WARNING);
                                alert.setTitle("Error");
                                alert.setContentText(var6.getMessage());
                                alert.showAndWait();
                            }
                        }
                    }

                });
                this.passwordColums.setCellFactory(TextFieldTableCell.forTableColumn());
                this.passwordColums.setOnEditCommit((t) -> {
                    String b;
                    String cell = ((User)t.getRowValue()).getIdUser();
                    ((User)t.getTableView().getItems().get(t.getTablePosition().getRow())).setPassword((String)t.getNewValue());
                    String Password = (String)t.getNewValue();
                    if(Password.equals("")){
                        infoLabel.setText("Проверьте ввод");
                    }
                    else {
                        try {
                            writer.write("editPassword");
                            writer.newLine();
                            writer.flush();
                            writer.write(Password);
                            writer.newLine();
                            writer.flush();
                            writer.write(cell);
                            writer.newLine();
                            writer.flush();
                            b = reader.readLine();
                            if (b.equals("0")) {
                                System.out.println("Редактирование прошло успешно");
                            } else if (b.equals("1")) {
                                System.out.println("Админа редактировать нельзя");
                            }
                        } catch (IOException var6) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error");
                            alert.setContentText(var6.getMessage());
                            alert.showAndWait();
                        }
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
        this.idColums.setCellValueFactory(new PropertyValueFactory("idUser"));
        this.usernameColums.setCellValueFactory(new PropertyValueFactory("username"));
        this.loginColums.setCellValueFactory(new PropertyValueFactory("login"));
        this.passwordColums.setCellValueFactory(new PropertyValueFactory("password"));
    }
}