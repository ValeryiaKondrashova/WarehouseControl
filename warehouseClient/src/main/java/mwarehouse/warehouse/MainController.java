package mwarehouse.warehouse;

import java.io.IOException;
import java.net.Socket;
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
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mwarehouse.warehouse.common.ConnectionTCP;
import mwarehouse.warehouse.entity.Command;
import mwarehouse.warehouse.entity.User;
import mwarehouse.warehouse.entity.UserProperty;
import mwarehouse.warehouse.singleton.ProgramLogger;

public class MainController {
    private ConnectionTCP connectionTCP;
    private final ObservableList<UserProperty> tableCountryProperties = FXCollections.observableArrayList();// вызовет конструктор 0

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_add;

    @FXML
    private Button button_delete;

    @FXML
    private Button button_diagram;

    @FXML
    private Button button_edit;

    @FXML
    private ImageView button_grafics;

    @FXML
    private Button button_otchet;

    @FXML
    private Button button_exit;

    @FXML
    private Button button_save_d;

    @FXML
    private Button button_view;

    @FXML
    private TableColumn<UserProperty, Integer> column_id;

    @FXML
    private TableColumn<UserProperty, String> column_login;

    @FXML
    private TableColumn<UserProperty, String> column_name;

    @FXML
    private TableColumn<UserProperty, String> column_password;

    @FXML
    private TableColumn<UserProperty, String> column_statusUser;

    @FXML
    private TableView<UserProperty> table_user;

//    @FXML
//    private TextField field_id;

    @FXML
    private TextField field_login;

    @FXML
    private TextField field_name;

    @FXML
    private TextField field_password;

    @FXML
    private TextField field_status;

    @FXML
    private TextField field_unCorrect;

    @FXML
    private TextField filterField;

    public void ShowDialogForSaving(ActionEvent event) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("SavingFile.fxml"));
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
//    String fileNameForSaving;
//    public void displayNameFile(String fileName){
//        fileNameForSaving = fileName;
//    }



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


        System.out.println("Hello everyone!");
        column_id.setCellValueFactory(cellValue -> cellValue.getValue().idProperty().asObject());
        column_name.setCellValueFactory(cellValue -> cellValue.getValue().nameProperty());
        column_login.setCellValueFactory(cellValue -> cellValue.getValue().loginProperty());
        column_password.setCellValueFactory(cellValue -> cellValue.getValue().passwordProperty());
        column_statusUser.setCellValueFactory(cellValue -> cellValue.getValue().statusUserProperty());

        button_view.setOnAction(event -> {
            System.out.println("Клиент нажал на  ПРОСМОТРЕТЬ");
            try {
                ProgramLogger.getProgramLogger().addLogInfo("Администратор нажал на ПРОСМОТРЕТЬ");
            } catch (IOException e) {
                e.printStackTrace();
            }

            tableCountryProperties.clear();// чтобы не добавлять каждый раз к существующему списку

            try {
                ProgramLogger.getProgramLogger().addLogInfo("выполнение writeObject(Command.READ): запись списка пользователей в поток... Получение всех пользователей из БД");
            } catch (IOException e) {
                e.printStackTrace();
            }
            connectionTCP.writeObject(Command.READ);
            List<User> allUsers = (List<User>) connectionTCP.readObject();
            for (int i = 0; i < allUsers.size(); i++) {
                UserProperty e = new UserProperty(allUsers.get(i));
                tableCountryProperties.add(e);
            }
            table_user.setItems(tableCountryProperties);// устанавливаем значение обсёрвабл листа в таблицу
        });
        button_add.setOnAction(event -> {
            System.out.println("Клиент нажал ДОБАВИТЬ");
            try {
                String name = field_name.getText();
                String login = field_login.getText();
                String password = field_password.getText();
                String statusUser = field_status.getText();

                if(name== "" || login=="" || password=="" || statusUser=="") {
                    System.out.println("Присутствуют пустые поля!");
                    field_unCorrect.setText("Присутствуют пустые поля! Повторите ввод!");
                }else{
                    field_name.setText("");
                    field_login.setText("");
                    field_password.setText("");
                    field_status.setText("");
                    field_unCorrect.setText("");
                    //field_id.setText("");

                    User user = new User(
                            name,
                            login,
                            password,
                            statusUser);

                    ProgramLogger.getProgramLogger().addLogInfo("выполнение writeObject(Command.CREATE): запись введенного пользователя в поток... Добавление пользователя в БД");

                    connectionTCP.writeObject(Command.CREATE);
                    connectionTCP.writeObject(user);
                }
            } catch (RuntimeException | IOException e) {
                //field_id.setText("");
                field_name.setText("");
                field_login.setText("");
                field_password.setText("");
                field_status.setText("");
                field_unCorrect.setText("Некорректный ввод данных. Повторите попытку!");
            }
        });
        button_edit.setOnAction(event -> {
            System.out.println("Администратор нажал РЕДАКТИРОВАТЬ!");
            try {
                //field_unCorrect.setText("");
                User userr = table_user.getSelectionModel().getSelectedItem().toWarehouse();

//                String id = field_id.getText();
//                if (!id.isEmpty()) {
//                    userr.setName(id);
//                }
                String name = field_name.getText();
                if (!name.isEmpty()) {
                    userr.setName(name);
                }
                String loginText = field_login.getText();
                if (!loginText.isEmpty()) {
                    userr.setLogin(loginText);
                }
                String passwordText = field_password.getText();
                if (!passwordText.isEmpty()) {
                    userr.setPassword(passwordText);
                }
                String statusText = field_status.getText();
                if (!statusText.isEmpty()) {
                    userr.setStatus(statusText);
                }

                //field_id.setText("");
                field_name.setText("");
                field_login.setText("");
                field_password.setText("");
                field_status.setText("");
                field_unCorrect.setText("");

                ProgramLogger.getProgramLogger().addLogInfo("выполнение writeObject(Command.UPDATE): Редактирование пользователя в БД");
                connectionTCP.writeObject(Command.UPDATE);
                connectionTCP.writeObject(userr);
            } catch (NullPointerException e) {
                field_unCorrect.setText("Выберите строку!");
            } catch (RuntimeException e) {
                field_unCorrect.setText("Некорректный ввод данных. Повторите попытку!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button_delete.setOnAction(event -> {
            try {
                field_unCorrect.setText("");
                int id = table_user.getSelectionModel().getSelectedItem().getId();

                ProgramLogger.getProgramLogger().addLogInfo("выполнение writeObject(Command.DELETE): Удаление пользователя из БД");
                connectionTCP.writeObject(Command.DELETE);
                connectionTCP.writeObject(id);
            } catch (NullPointerException | IOException e) {// если 0
                field_unCorrect.setText("Выберите строку!");
            }
        });
        button_exit.setOnAction(event -> {
            connectionTCP.writeObject(Command.EXIT);
            connectionTCP.close();
            System.exit(0);
        });

        FilteredList<UserProperty> filterData = new FilteredList<>(tableCountryProperties, b->true);
        filterField.textProperty().addListener((observable,oldValue,newValue) -> {
            filterData.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if(user.getName().toLowerCase().indexOf(lowerCaseFilter) != -1 || user.getLogin().toLowerCase().indexOf(lowerCaseFilter) != -1 || user.getPassword().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    System.out.println("Qwerty");
                    return true; //фильтр для логина
                }
                else{
                    return false;
                }
            });
            SortedList<UserProperty> sortedDate = new SortedList<>(filterData);
            sortedDate.comparatorProperty().bind(table_user.comparatorProperty());

            System.out.println("sortedDate = " + sortedDate);
            table_user.setItems(sortedDate);
        });
    }

}
