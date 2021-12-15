package mwarehouse.warehouse.database;

import mwarehouse.warehouse.entity.Product;
import mwarehouse.warehouse.database.DatabaseHandler;
import mwarehouse.warehouse.common.ConnectionTCP;
import mwarehouse.warehouse.entity.Command;
import mwarehouse.warehouse.entity.Task;
import mwarehouse.warehouse.entity.User;
import mwarehouse.warehouse.singleton.ProgramLogger;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class RequestHandler implements Runnable {
    private final ConnectionTCP connectionTCP;  //создали объекта КЛАССА ConnectionTCP

    public RequestHandler(Socket socket) {
        connectionTCP = new ConnectionTCP(socket);//сокет соединения с клиентом
    } //connectionTCP - шнур

    @Override
    public void run() {
        DatabaseHandler userRepository = new DatabaseHandler();
        DatabaseHandler productRepository = new DatabaseHandler();

        while (true) {
            Command command = (Command) connectionTCP.readObject();
            System.out.println(command);
            switch (command) {
                case CREATE: {
                    User userr = (User) connectionTCP.readObject();
                    try {
                        userRepository.signUpUser(userr);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Пользователь добавлен в БД (Table: users)!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case CREATE1: {
                    Product product = (Product) connectionTCP.readObject();
                    try {
                        userRepository.signUpProduct(product);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Товар добавлен в БД (Table: products)!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case CREATETASK: {
                    Task task = (Task) connectionTCP.readObject();
                    try {
                        productRepository.addNewTask(task);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case READ: {
                    List<User> userr = null;
                    try {
                        userr = userRepository.getAllUsers();
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Данные из БД о пользователях просмотрены!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connectionTCP.writeObject(userr); // с помощью writeObject отправляем клиенту массив юзеров
                }
                break;
                case READ1: {
                    List<Product> products = null;
                    try {
                        products = productRepository.getAllProducts();
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Данные из БД о товарах просмотрены!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    connectionTCP.writeObject(products); // с помощью writeObject отправляем клиенту массив юзеров
                }
                break;
                case READTASKS: {
                    List<Task> tasks = null;
                    tasks = productRepository.gettAllTask();
                    connectionTCP.writeObject(tasks);

                }
                break;
                case READMANUFACTURER: {
                    List<String> manufacturers = null;
                    manufacturers = productRepository.getAllManufacturer();
                    connectionTCP.writeObject(manufacturers);


                }
                break;
                case READTYPE: {
                    List<String> types = null;
                    types = productRepository.getAllType();
                    connectionTCP.writeObject(types);

                }
                break;
                case READSTORAGE: {
                    List<String> storages = null;
                    storages = productRepository.getAllStorage();
                    connectionTCP.writeObject(storages);

                }
                break;
                case READMODEL: {
                    List<String> models = null;
                    models = productRepository.getAllModel();
                    connectionTCP.writeObject(models);

                }
                break;
                case UPDATE: {
                    User userr = (User) connectionTCP.readObject();

                    try {
                        userRepository.updateUser(userr);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Данные пользователя отредактированы!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case UPDATE1: {
                    Product product = (Product) connectionTCP.readObject();

                    try {
                        productRepository.updateProduct(product);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Данные пользователя отредактированы!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case DELETE: {
                    Integer id = (Integer) connectionTCP.readObject();
                    try {
                        userRepository.deleteUserByID(id);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Пользователь удален!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case DELETE1: {
                    Integer id = (Integer) connectionTCP.readObject();
                    try {
                        productRepository.deleteProductByID(id);
                        ProgramLogger.getProgramLogger().addLogInfo("Успешно! Товар удален!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case DELETETASK: {
                    Integer id = (Integer) connectionTCP.readObject();
                    try {
                        productRepository.deleteTaskByID(id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case EXIT: {
                    connectionTCP.close();
                    return;
                }
            }
        }
    }
}
