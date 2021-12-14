module mwarehouse.warehouse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens mwarehouse.warehouse to javafx.fxml;
    exports mwarehouse.warehouse;
    exports mwarehouse.warehouse.entity;
    opens mwarehouse.warehouse.entity to javafx.fxml;

    exports mwarehouse.warehouse.client;
    opens mwarehouse.warehouse.client to javafx.fxml;
}