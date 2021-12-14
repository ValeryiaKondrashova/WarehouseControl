package mwarehouse.warehouse.database;

import mwarehouse.warehouse.entity.Product;
import mwarehouse.warehouse.entity.Task;
import mwarehouse.warehouse.entity.User;
import mwarehouse.warehouse.database.Configs;
import mwarehouse.warehouse.database.Const;
import mwarehouse.warehouse.singleton.ProgramLogger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends Configs {   //класс DatabaseHandler наследуется от Configs
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException{
        String сonnectionString = "jdbc:mysql://" + dbHost + ":"             // jdbc - то такоей плагин, который позволяет связать Java и MySQL
                + dbPort + "/" + dbName;                                    //в этой строке (connectionString) будет содержаться все то (все данные), что поможет нам подключиться к бд
        Class.forName("com.mysql.cj.jdbc.Driver");                             // указываем какой драйвер будем использовать

        //dbConnection = DriverManager.getConnection(сonnectionString,dbUser,dbPass);  //здесь заключаем само соединение
        try{
            return DriverManager.getConnection(сonnectionString,dbUser,dbPass);
        } catch (SQLException e){
            throw new RuntimeException("can't provide connection :(");
        }

    }

    public void signUpUser(User user) throws IOException { // Добавление пользователя в таблицу
        String insert = "INSERT INTO " + Const.USER_TABLE + "("  /* запрос добавления в бд (запрос insert) */
                + Const.USER_NAME + ","
                + Const.USER_LOGIN + ","
                + Const.USER_PASSWORD + ","
                + Const.USER_STATUS + ")" +
                "VALUES(?, ?, ?, (Select idstatus from statususer where statusName = ?))";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1,user.getName());
            prSt.setString(2,user.getLogin());
            prSt.setString(3,user.getPassword());
            prSt.setString(4,user.getStatus());


            prSt.executeUpdate(); //метод executeUpdate() позволяет занести что-либо в бд (в данном случае заносим в бд объект prSt
        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("При попытке добавления пользователя в БД введены некорректные данные.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addNewTask(Task task) throws IOException {
        String insertTask = "INSERT INTO tasks (task) VALUES (?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insertTask);
            prSt.setString(1,task.getTask());

            prSt.executeUpdate(); //метод executeUpdate() позволяет занести что-либо в бд (в данном случае заносим в бд объект prSt
        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("При попытке добавления пользователя в БД введены некорректные данные.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void signUpProduct(Product product){
        // добавляем модель с ценой в табл модель
        String insert = "INSERT INTO models (model,price) VALUES (?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1,product.getModel());
            prSt.setDouble(2,product.getPrice());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //Добавляем сам товар
        String insert2 = "INSERT INTO products (manufacturer_product, type_product, model_product, quantity_product, storage_product) VALUES ((SELECT idManufacturer FROM manufacturers WHERE manufacturer = ?),(SELECT idType FROM types WHERE type = ?),(SELECT idModel FROM models WHERE model = ?),?,(SELECT idStorage FROM storages WHERE storage = ?))";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert2);
            prSt.setString(1,product.getManufacturer());
            prSt.setString(2,product.getType());
            prSt.setString(3,product.getModel());
            prSt.setInt(4,product.getQuantity());
            prSt.setString(5,product.getStorage());

            prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // write, read
    public ResultSet getUser(User user) { //это метод, который возвращает
        ResultSet reSet = null;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " +
                Const.USER_LOGIN + "=? AND " + Const.USER_PASSWORD + "=?"; //создаем строку select, в которой будет хранится запрос
        // Что за строка select? (разбираем запрос) : выбираем всё (*) из таблицы Const.USER_TABLE (users), где поле Const.USER_LOGIN (login) будет равно чему либо (=?)
        // и (AND) поле Const.USER_PASSWORD (password) также будет равно чему либо (=?)

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1,user.getLogin());
            prSt.setString(2,user.getPassword());

            reSet= prSt.executeQuery(); // метод executeQuery() позволяет получить данные из бд
            // всё что мы получим из бд будет заносится в переменную reSet
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return reSet;

    }

    public List<User> getAllUsers() throws IOException {  //создаем метод getAllTech() который вернет  List<Techh> (список стран)  //метод который отвечает за считываение из БД
        //List<User> - это массив( точнее список) Tech (это как массив стрингов, только у нас список techniques)
        List<User> users = new ArrayList<>();  //это как int a = new int();

        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("select users.idusers, users.name, users.login,users.password, statusName from users JOIN statususer ON users.statusUser=statususer.idstatus"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                int id = resultSet.getInt("idusers");
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String statusUser = resultSet.getString("statusName");

                //считали строку из result состоящую из элементов id, name, number и тд
                User userr = new User(id, name, login, password, statusUser);
                //теперь создали объект techniques и поместили туда считанные данные
                users.add(userr);
                //  и добавили страну(userr) в массив users
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("При попытке просмотра пользователей из БД возникла ошибка");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users; //вернули считанный массив
    }

    public void updateUser(User userr) throws IOException { // !!!!!!!!!!!!!
        try (Connection connection = getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update users JOIN statususer ON users.statusUser=statususer.idstatus set name = ?, login = ?, password = ?, statusUser = (Select idstatus from statususer where statusName = ?) where idusers = ?")) { //не правильный statusUser (в колонке прописано "пользователь", а не 0 либо 1

            preparedStatement.setString(1, userr.getName());
            preparedStatement.setString(2, userr.getLogin());
            preparedStatement.setString(3, userr.getPassword());
            preparedStatement.setString(4, userr.getStatus());
            preparedStatement.setInt(5, userr.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("При попытке редактирования пользователя возникла ошибка! Введены некорректные данные.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product){
        try (Connection connection = getDbConnection();
             //Заменить запрос!!!!!!!
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update products JOIN manufacturers ON products.manufacturer_product = manufacturers.idManufacturer JOIN types ON products.type_product = types.idType JOIN models ON products.model_product = models.idModel JOIN storages ON  products.storage_product = storages.idStorage set manufacturer_product=(Select idManufacturer from manufacturers where manufacturer = ?), type_product=(Select idType from types where type = ?), model_product=(Select idModel from models where model = ?), quantity_product = ?, storage_product=(Select idStorage from storages where storage = ?) where idproduct = ?")) {;
            preparedStatement.setString(1, product.getManufacturer());
            preparedStatement.setString(2, product.getType());
            preparedStatement.setString(3, product.getModel());
            preparedStatement.setInt(4, product.getQuantity());
            //preparedStatement.setDouble(5, product.getPrice());
            preparedStatement.setString(5, product.getStorage());
            preparedStatement.setInt(6, product.getId());

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserByID(int id) throws IOException {
        try (Connection connection = getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from users where idusers = ?")) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("Ошибка! Не удалось удалить пользователя.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteProductByID (int id) throws IOException {
        try (Connection connection = getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from products where idproduct = ?")) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("Ошибка! Не удалось удалить пользователя.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteTaskByID (int id) throws IOException {
        try (Connection connection = getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from tasks where idTask = ?")) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("Ошибка! Не удалось удалить задание.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() throws IOException {  //создаем метод getAllTech() который вернет  List<Techh> (список стран)  //метод который отвечает за считываение из БД
        //List<User> - это массив( точнее список) Tech (это как массив стрингов, только у нас список techniques)
        List<Product> products = new ArrayList<>();  //это как int a = new int();

        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT products.idproduct, manufacturer, type, model, products.quantity_product, price, storage, warehouse FROM products JOIN manufacturers ON products.manufacturer_product = manufacturers.idManufacturer JOIN types ON products.type_product = types.idType JOIN models ON products.model_product = models.idModel JOIN storages ON  products.storage_product = storages.idStorage JOIN warehouses ON storages.warehouseStorage = warehouses.idWarehouse"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                int id = resultSet.getInt("idproduct");
                String manufacturer = resultSet.getString("manufacturer");
                String type = resultSet.getString("type");
                String model = resultSet.getString("model");
                int quantity = resultSet.getInt("quantity_product");
                double price = resultSet.getDouble("price");
                String storage = resultSet.getString("storage");
                String warehouse = resultSet.getString("warehouse");

                //считали строку из result состоящую из элементов id, name, number и тд
                Product productt = new Product(id, manufacturer, type, model, quantity, price, storage, warehouse);
                //теперь создали объект techniques и поместили туда считанные данные
                products.add(productt);
                //  и добавили страну(userr) в массив users
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ProgramLogger.getProgramLogger().addLogInfo("При попытке просмотра пользователей из БД возникла ошибка");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products; //вернули считанный массив
    }

    public List<Task> gettAllTask(){

        List<Task> tasks = new ArrayList<>();

        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT idTask, task from tasks");

            while (resultSet.next()) {
                int id = resultSet.getInt("idTask");
                String task = resultSet.getString("task");

                Task taskk = new Task(id, task);

                tasks.add(taskk);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public List<String> getAllManufacturer(){

        List<String> manufacturers = new ArrayList<>();


        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT manufacturer from manufacturers"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                String manufacturer = resultSet.getString("manufacturer");

                //теперь создали объект techniques и поместили туда считанные данные
                manufacturers.add(manufacturer);

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return manufacturers;
    }

    public List<String> getAllType(){

        List<String> types = new ArrayList<>();

        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT type from types"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                String type = resultSet.getString("type");

                //теперь создали объект techniques и поместили туда считанные данные
                types.add(type);

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return types;
    }

    public List<String> getAllStorage(){

        List<String> storages = new ArrayList<>();

        try (Connection connection = getDbConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос // создали объект statement, для выполнения запроса по соединению connection

            ResultSet resultSet = statement.executeQuery("SELECT storage from storages"); // в resultSet записывает полученные данные (то есть страны)  // с помощью executeQuery мы выполняем запрос

            while (resultSet.next()) { //благодаря next() мы проходим по всем строкам(ячейкам) result (по факту это как двумерный массив)
                String storage = resultSet.getString("storage");

                //теперь создали объект techniques и поместили туда считанные данные
                storages.add(storage);

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return storages;

    }
}


