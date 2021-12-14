package mwarehouse.warehouse;

import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import mwarehouse.warehouse.common.ConnectionTCP;
import mwarehouse.warehouse.entity.Command;
import mwarehouse.warehouse.entity.Task;
import mwarehouse.warehouse.entity.User;

public class SavingFileTaskController {
    private ConnectionTCP connectionTCP;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_saveOtchet;

    @FXML
    private TextField field_nameFile;

    @FXML
    private TextField field_unCorrect_nameFile;

    @FXML
    void SaveFile(ActionEvent event) throws IOException {

        String fileName;
        Boolean q,w,o,r,t,y,u,p;
        fileName = field_nameFile.getText();

        q=fileName.contains("/");
        w =fileName.contains("\"");
        o=fileName.contains(":");
        r=fileName.contains("*");
        t=fileName.contains("<");
        y=fileName.contains(">");
        u=fileName.contains("|");
        p=fileName.contains("?");

        if(q==true ||w==true || p==true || r==true || t==true || y==true|| u==true || o==true){

            System.out.println(q+ " " + w +" " +  o + " " + r + " " + t + " " + y + " " + u + " " +  p);
            field_unCorrect_nameFile.setText("Некорректное имя файла!");
            field_unCorrect_nameFile.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
            return;
        }
        else{
            field_unCorrect_nameFile.setText("Файл успешно сохранен!");
            field_unCorrect_nameFile.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
            field_nameFile.setText("");
            fileName+=".txt";
        }

        try {
            connectionTCP = ConnectionTCP.getInstance();   // Создание сокета для передачи данных. Сокета для установки соединения уже создан раннее (в RequestHandler.java)
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }


        connectionTCP.writeObject(Command.READTASKS);
        List<Task> allTasks = (List<Task>) connectionTCP.readObject();
        try {
            FileWriter fileWriter = new FileWriter(fileName, false);
            fileWriter.write("Задания для выполнения : \n");
            for (int i = 0; i < allTasks.size(); i++) {

                String task = allTasks.get(i).getTask();

                fileWriter.write("\t" + task + "\n");

            }
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {

    }

}
