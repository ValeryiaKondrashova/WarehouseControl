package mwarehouse.warehouse;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mwarehouse.warehouse.common.ConnectionTCP;
import mwarehouse.warehouse.entity.Command;
import mwarehouse.warehouse.entity.Product;

public class EditProductController {
    ConnectionTCP connectionTCP;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private ComboBox manufacturer_ComboBox;

    @FXML
    private ComboBox type_ComboBox;

    @FXML
    private ComboBox storage_ComboBox;

    @FXML
    private ComboBox model_ComboBox;

    @FXML
    private TextField quantity_edit;

    @FXML
    private TextField price_edit;

//    @FXML
//    private TextField storage_add;

    @FXML
    private TextField qw_field;

    @FXML
    private Label label;

    String manufacturer,type, storage;
    String model, quantity, price;


    @FXML
    void SelectManuf(ActionEvent event) {
        manufacturer = manufacturer_ComboBox.getSelectionModel().getSelectedItem().toString();
        label.setText(manufacturer);

    }

    @FXML
    void SelectType(ActionEvent event) {
        type = type_ComboBox.getSelectionModel().getSelectedItem().toString();
        label.setText(type);

    }

    @FXML
    void SelectStorage(ActionEvent event) {
        storage = storage_ComboBox.getSelectionModel().getSelectedItem().toString();
        label.setText(storage);

    }

    @FXML
    void SelectModel(ActionEvent event) {
        model = model_ComboBox.getSelectionModel().getSelectedItem().toString();
        label.setText(model);
    }

    @FXML
    void Edit(ActionEvent event) throws IOException {

        if(model.isEmpty()){
            qw_field.setText("Выберите модель для редактирования!");
            qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
        }
        else {
            quantity = quantity_edit.getText();

            price = price_edit.getText();

            System.out.println("manufacturer= " + manufacturer + ", type= " + type + ", model= " + model + ", quantity= " + quantity + ", price= " + price + ", storage= " + storage);

            MainUserController mainUserController = new MainUserController();
            mainUserController.displayNameEdit(manufacturer,
                    type,
                    model,
                    quantity,
                    price,
                    storage);
            qw_field.setText("Товар успешно отредактирован!");
            qw_field.setStyle("-fx-background-color: #2E3348; -fx-text-fill: #fafafa;");
        }



    }

    @FXML
    void initialize() throws IOException {

        manufacturer="";
        type="";
        model = "";
        storage = "";

        price = "";
        quantity="";


        connectionTCP = ConnectionTCP.getInstance();


        connectionTCP.writeObject(Command.READMANUFACTURER);
        List<String> allProducts = (List<String>) connectionTCP.readObject();
        ObservableList allPr = FXCollections.observableArrayList(allProducts);
        manufacturer_ComboBox.setItems(allPr);

        connectionTCP.writeObject(Command.READTYPE);
        List<String> allTypes = (List<String>) connectionTCP.readObject();
        ObservableList allTy = FXCollections.observableArrayList(allTypes);
        type_ComboBox.setItems(allTy);

        connectionTCP.writeObject(Command.READSTORAGE);
        List<String> allStorages = (List<String>) connectionTCP.readObject();
        ObservableList allSt = FXCollections.observableArrayList(allStorages);
        storage_ComboBox.setItems(allSt);

        connectionTCP.writeObject(Command.READMODEL);
        List<String> allModels = (List<String>) connectionTCP.readObject();
        ObservableList allMdl = FXCollections.observableArrayList(allModels);
        model_ComboBox.setItems(allMdl);

    }

}
