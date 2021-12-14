package mwarehouse.warehouse.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;

    private String manufacturer;
    private String type;
    private String model;
    private Integer quantity;
    private Double price;
    private String storage;
    private String warehouse;

    public Product(String manufacturer, String type, String model, Integer quantity, Double price, String storage, String warehouse) {
        this.manufacturer = manufacturer;
        this.type = type;
        this.model = model;
        this.quantity = quantity;
        this.price = price;
        this.storage = storage;
        this.warehouse = warehouse;
    }

    public Product(int id, String manufacturer, String type, String model, Integer quantity, Double price, String storage, String warehouse) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.type = type;
        this.model = model;
        this.quantity = quantity;
        this.price = price;
        this.storage = storage;
        this.warehouse = warehouse;
    }

    public Product(){

    }

    // конструктор для добавления товара
    public Product(String manufacturer, String type, String model, Integer quantity, Double price, String storage) {
        this.manufacturer = manufacturer;
        this.type = type;
        this.model = model;
        this.quantity = quantity;
        this.price = price;
        this.storage = storage;
    }


    public void setId(int id) { this.id = id; }

    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public void setType(String type) { this.type = type; }

    public void setModel(String model) { this.model = model; }

    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public void setPrice(Double price) { this.price = price; }

    public void setStorage(String storage) { this.storage = storage; }

    public void setWarehouse(String warehouse) { this.warehouse = warehouse; }


    public int getId() { return id; }

    public String getManufacturer() { return manufacturer; }

    public String getType() { return type; }

    public String getModel() { return model; }

    public Integer getQuantity() { return quantity; }

    public Double getPrice() { return price; }

    public String getStorage() { return storage; }

    public String getWarehouse() { return warehouse; }

}




