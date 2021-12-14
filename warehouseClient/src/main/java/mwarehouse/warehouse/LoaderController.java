package mwarehouse.warehouse;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mwarehouse.warehouse.common.ConnectionTCP;
import mwarehouse.warehouse.entity.*;

public class LoaderController {
    private ConnectionTCP connectionTCP;
    private final ObservableList<TaskProperty> tableTaskProperties = FXCollections.observableArrayList();// вызовет конструктор 0


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_exit_byLoader;

    @FXML
    private Button button_view_byLoader;

    @FXML
    private Button button_put;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<TaskProperty> table_tasks;

    @FXML
    private TableColumn<TaskProperty, Integer> id_column;

    @FXML
    private TableColumn<TaskProperty, String> task_column;

    @FXML
    private TextField unCorrect_field;

    public void ShowDialogForSaving(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("SavingFileTask.fxml"));
            stage.setTitle("Создание текстового отчета");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner( ((Node)event.getSource()).getScene().getWindow() );
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        try {
            connectionTCP = ConnectionTCP.getInstance();  // Создание сокета для передачи данных. Сокета для установки соединения уже создан раннее (в RequestHandler.java)
            System.out.println("Нашел клиента и сокет готов для передачи! :)");
        } catch (IOException e) {
            System.out.println("Не нашел клиента! :(");
            e.printStackTrace();
            System.exit(-1);
        }

        id_column.setCellValueFactory(cellValue -> cellValue.getValue().idProperty().asObject());
        task_column.setCellValueFactory(cellValue -> cellValue.getValue().taskProperty());

        button_view_byLoader.setOnAction(event -> {
            System.out.println("Грузчик нажал на ПРОСМОТРЕТЬ");

            tableTaskProperties.clear();

            connectionTCP.writeObject(Command.READTASKS);
            List<Task> allTasks = (List<Task>) connectionTCP.readObject();
            for (int i = 0; i < allTasks.size(); i++) {
                TaskProperty e = new TaskProperty(allTasks.get(i));
                tableTaskProperties.add(e);
            }
            table_tasks.setItems(tableTaskProperties);// устанавливаем значение обсёрвабл листа в таблицу
        });

        button_put.setOnAction(event -> { //удаляем из списка заданий, когда грузчик берет задание на выполнение
            try {
                unCorrect_field.setText("");
                int id = table_tasks.getSelectionModel().getSelectedItem().getId();
                connectionTCP.writeObject(Command.DELETETASK);
                connectionTCP.writeObject(id);
            } catch (NullPointerException e) {// если 0
                unCorrect_field.setText("Выберите задание!");
            }
        });

        button_exit_byLoader.setOnAction(event -> {
            connectionTCP.writeObject(Command.EXIT);
            connectionTCP.close();
            System.exit(0);
        });

        FilteredList<TaskProperty> filterData = new FilteredList<>(tableTaskProperties, b->true);
        filterField.textProperty().addListener((observable,oldValue,newValue) -> {
            filterData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(user.getTask().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    System.out.println("Qwerty");
                    return true; //фильтр для логина
                }
                else{
                    return false;
                }
            });
            SortedList<TaskProperty> sortedDate = new SortedList<>(filterData);
            sortedDate.comparatorProperty().bind(table_tasks.comparatorProperty());

            System.out.println("sortedDate = " + sortedDate);
            table_tasks.setItems(sortedDate);
        });

    }

}
