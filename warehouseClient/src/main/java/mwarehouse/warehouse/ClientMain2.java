package mwarehouse.warehouse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mwarehouse.warehouse.singleton.ProgramLogger;

import java.io.IOException;


public class ClientMain2 extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Autorization.fxml"));
        Scene scene = new Scene(root, 700,400);
        stage.setTitle("Авторизация");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {

        launch();
        ProgramLogger.getProgramLogger().addLogInfo("Запуск клиента... Открытие окна авторизации");
    }
}