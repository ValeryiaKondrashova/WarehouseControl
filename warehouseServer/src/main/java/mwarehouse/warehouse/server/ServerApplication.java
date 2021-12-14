package mwarehouse.warehouse.server;

import mwarehouse.warehouse.database.RequestHandler;
import mwarehouse.warehouse.singleton.ProgramLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication {
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(6666)) {  //сокет сервера

            ProgramLogger.getProgramLogger().addLogInfo("------------------------- Многопоточный сервер запущен -------------------------");

            System.out.println("The multi-threaded server has started");
            while (true) {
                Socket clientSocket = serverSocket.accept();  //получаем айпи сокета клиента. accept ожидает соединение

                ProgramLogger.getProgramLogger().addLogInfo("Установлено новое соединение! IP:"+ clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                System.out.println("New connection established\n" +
                        "IP:" + clientSocket.getInetAddress() + ":" + clientSocket.getPort()); // соединение по таким-то данных
                new Thread(new RequestHandler(clientSocket)).start();// обработчик клиентских запросов
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}