package sample;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PopularController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button exitButton;

    @FXML
    private Button reviewButton;

    @FXML
    private Label infoLabel;

    @FXML
    private StackedBarChart<String, Number> graf;
    String price;
    String datee;
    @FXML
    void initialize() {
        try{
            Socket socket = new Socket("localhost",12348);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
            XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
            int i1 = 0;
            int i2 = 0;
            int pricenedv = 0;
            int pricecar = 0;
            String text = "Отчет о продажах составлен Администратором\n";
            try {
                writer.write("Data1gra");
                writer.newLine();
                writer.flush();
                writer.write("Недвижимость");
                writer.newLine();
                writer.flush();
                for (int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
                    this.price = reader.readLine();
                    this.datee = reader.readLine();
                    int expr = Integer.parseInt(this.price);
                    i1++;
                    pricenedv += expr;
                    text += "Недвижимость: Дата возврата  " + this.datee + ", Прибыль с учетом шрафов = " + pricenedv+"\n";

                }
                System.out.println(i1);
                System.out.println(pricenedv);
            } catch (IOException var6) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText(var6.getMessage());
                alert.showAndWait();
            }
            try {
                writer.write("Data2gra");
                writer.newLine();
                writer.flush();
                for (int stop = Integer.parseInt(reader.readLine()); stop != 0; stop = Integer.parseInt(reader.readLine())) {
                    this.price = reader.readLine();
                    this.datee = reader.readLine();
                    int expr = Integer.parseInt(this.price);
                    i2++;
                    pricecar += expr;
                    text += "Транспорт: Дата возврата " + this.datee + ", Прибыль с учетом шрафов = " + pricecar+"\n";

                }
                System.out.println(i2);
                System.out.println(pricecar);
                text += "Общая прибыль с недвижимости = " + pricenedv + "\n";
                text += "Общая прибыль с транспорта = " + pricecar + "\n";
                int obch = pricenedv + pricecar;
                text += "Общая прибыль  = " + obch + "\n\n";
                series1.setName("Недвижимость");
                series2.setName("Транспорт");
                series1.getData().add(new XYChart.Data<String, Number>("Недвижимость", i1));
                series2.getData().add(new XYChart.Data<String, Number>("Транспорт", i2));
                graf.getData().addAll(series1, series2);
            } catch (IOException var6) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error");
                alert.setContentText(var6.getMessage());
                alert.showAndWait();
            }
            String finalText = text;

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