package mwarehouse.warehouse;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import mwarehouse.warehouse.common.ConnectionTCP;
import mwarehouse.warehouse.entity.Command;
import mwarehouse.warehouse.entity.Task;
import mwarehouse.warehouse.entity.TaskProperty;

public class AddTaskController {
    private ConnectionTCP connectionTCP;
    private final ObservableList<TaskProperty> tableProperties = FXCollections.observableArrayList();// вызовет конструктор 0


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addTask;

    @FXML
    private Button viewTask;

    @FXML
    private TextField field_newTask;

    @FXML
    private TableView<TaskProperty> table_tasks;

    @FXML
    private TableColumn<TaskProperty, Integer> id_column;

    @FXML
    private TableColumn<TaskProperty, String> task_column;


    @FXML
    void initialize() {
        try{
            connectionTCP = ConnectionTCP.getInstance();  // Создание сокета для передачи данных. Сокета для установки соединения уже создан раннее (в RequestHandler.java)
            System.out.println("Нашел клиента и сокет готов для передачи! :)");
        } catch (IOException e) {
            System.out.println("Не нашел клиента! :(");
            e.printStackTrace();
            System.exit(-1);
        }

        id_column.setCellValueFactory(cellValue -> cellValue.getValue().idProperty().asObject());
        task_column.setCellValueFactory(cellValue -> cellValue.getValue().taskProperty());


        viewTask.setOnAction(event -> {
            tableProperties.clear();

            connectionTCP.writeObject(Command.READTASKS);
            List<Task> allTasks = (List<Task>) connectionTCP.readObject();
            for (int i = 0; i < allTasks.size(); i++) {
                TaskProperty e = new TaskProperty(allTasks.get(i));
                tableProperties.add(e);
            }
            table_tasks.setItems(tableProperties);// устанавливаем значение обсёрвабл листа в таблицу
        });


        addTask.setOnAction(event -> {
            String task = field_newTask.getText();
            if(task.isEmpty()){
                field_newTask.setText("Введите задачу!");
                System.out.println("task empty: " + task);
            }
            else {
                Task newTask = new Task(task);

                connectionTCP.writeObject(Command.CREATETASK);
                connectionTCP.writeObject(newTask);

                field_newTask.setText("Задача добавлена!");
                System.out.println("task full: " + task);
            }
        });
    }

}
