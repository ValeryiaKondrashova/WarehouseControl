package mwarehouse.warehouse.entity;

import javafx.beans.property.*;

public class ProductProperty {
    private IntegerProperty id;
    private StringProperty manufacturer;
    private StringProperty type;
    private StringProperty model;
    private IntegerProperty quantity;
    private DoubleProperty price;
    private StringProperty storage;
    private StringProperty warehouse;

    public ProductProperty(Product product){
        id = new SimpleIntegerProperty(product.getId());
        manufacturer = new SimpleStringProperty(product.getManufacturer());
        type = new SimpleStringProperty(product.getType());
        model = new SimpleStringProperty(product.getModel());
        quantity = new SimpleIntegerProperty(product.getQuantity());
        price = new SimpleDoubleProperty(product.getPrice());
        storage = new SimpleStringProperty(product.getStorage());
        warehouse = new SimpleStringProperty(product.getWarehouse());
    }

    public ProductProperty(IntegerProperty id, StringProperty manufacturer, StringProperty type, StringProperty model, IntegerProperty quantity, DoubleProperty price, StringProperty storage, StringProperty warehouse) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.type = type;
        this.model = model;
        this.quantity = quantity;
        this.price = price;
        this.storage = storage;
        this.warehouse = warehouse;
    }

    ProductProperty(Integer id, String manufacturer, String type, String model, Integer quantity, Double price, String storage, String warehouse){
        this.id = new SimpleIntegerProperty(id);
        this.manufacturer = new SimpleStringProperty(manufacturer);
        this.type = new SimpleStringProperty(type);
        this.model = new SimpleStringProperty(model);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.storage = new SimpleStringProperty(storage);
        this.warehouse = new SimpleStringProperty(warehouse);
    }

    public Product toWWarehouse(){
        return new Product(
                id.intValue(),
                manufacturer.getValue(),
                type.getValue(),
                model.getValue(),
                quantity.intValue(),
                price.doubleValue(),
                storage.getValue(),
                warehouse.getValue()
        );
    }


    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public String getManufacturer() { return manufacturer.get(); }
    public StringProperty manufacturerProperty() { return manufacturer; }

    public String getType() { return type.get(); }
    public StringProperty typeProperty() { return type; }

    public String getModel() { return model.get(); }
    public StringProperty modelProperty() { return model; }

    public int getQuantity() { return quantity.get(); }
    public IntegerProperty quantityProperty() { return quantity; }

    public double getPrice() { return price.get(); }
    public DoubleProperty priceProperty() { return price; }

    public String getStorage() { return storage.get(); }
    public StringProperty storageProperty() { return storage; }

    public String getWarehouse() { return warehouse.get(); }
    public StringProperty warehouseProperty() { return warehouse; }



    public void setId(int id) { this.id.set(id); }

    public void setManufacturer(String manufacturer) { this.manufacturer.set(manufacturer); }

    public void setType(String type) { this.type.set(type); }

    public void setModel(String model) { this.model.set(model); }

    public void setQuantity(int quantity) { this.quantity.set(quantity); }

    public void setPrice(double price) { this.price.set(price); }

    public void setStorage(String storage) { this.storage.set(storage); }

    public void setWarehouse(String warehouse) { this.warehouse.set(warehouse); }
}

