package mwarehouse.warehouse.entity;

import javafx.beans.property.*;

public class UserProperty {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty login;
    private StringProperty password;
    private StringProperty statusUser;

    public UserProperty(User user) {
        id = new SimpleIntegerProperty(user.getId());  //из обычно int преобразовываем в IntegerProperty
        name = new SimpleStringProperty(user.getName());
        login = new SimpleStringProperty(user.getLogin());
        password = new SimpleStringProperty(user.getPassword());
        statusUser = new SimpleStringProperty(user.getStatus());
    }

    public User toWarehouse(){
        return new User(
                id.intValue(),
                name.getValue(),
                login.getValue(),
                password.getValue(),
                statusUser.getValue()
        );
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public String getLogin() { return login.get(); }
    public StringProperty loginProperty() { return login; }

    public String getPassword() { return password.get(); }
    public StringProperty passwordProperty() { return password;}

    public String getStatusUser() { return statusUser.get(); }
    public StringProperty statusUserProperty() { return statusUser; }



    public void setId(int id) { this.id.set(id); }

    public void setName(String name) { this.name.set(name); }

    public void setLogin(String login) { this.login.set(login); }

    public void setPassword(String password) { this.password.set(password); }

    public void setStatusUser(String statusUser) { this.statusUser.set(statusUser); }
}

