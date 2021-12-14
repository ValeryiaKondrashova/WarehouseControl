package mwarehouse.warehouse.entity;

import java.io.Serializable;

public class User implements Serializable {
    private int id;

    private String name;
    private String login;
    private String password;
    private String status;

    public User(String name, String login, String password, String status) {  // когда клиент присылает информацию
        this.name = name;
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public User(int id, String name, String login, String password, String status) {  //когда читаем их БД
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.status = status;
    }

    public User(String status){
        this.status=status;
    }

    public User() {

    }


    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

//    @Override
//    public String toWarehouse() {
//        return "Warehouse{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", login=" + login +
//                ", password=" + password +
//                ", status='" + status + '\'' +
//                '}';
//    }
//
}