package mwarehouse.warehouse.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionTCP {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private static ConnectionTCP instance;

    private ConnectionTCP() throws IOException {
        socket = new Socket("127.0.0.1", 6666);
        try {
            this.outputStream = new ObjectOutputStream(socket.getOutputStream()); // ObjectOutputStream - записывает примитивные данные в поток вывода
            this.inputStream = new ObjectInputStream(socket.getInputStream());  // ObjectInputStream - Поток ввода объекта десериализует примитивные данные и объекты, ранее записанные с использованием потока вывода объекта.
            System.out.println("Приветик! я в ConnectionTCP!");
        } catch (IOException e) {
            throw new RuntimeException("can't initialise", e);
        }
    }

    public static ConnectionTCP getInstance() throws IOException {
        if (instance == null) {
            instance = new ConnectionTCP();
        }

        return instance;
    }

    //Метод writeObject используется для записи объекта в поток. Любой объект, включая строки и массивы, записывается
    // с помощью writeObject. В поток можно записать несколько объектов или примитивов. Объекты должны быть считаны
    // из соответствующего потока ввода объектов с теми же типами и в том же порядке, в каком они были записаны.
    public void writeObject(Object object) {
        try {
            outputStream.writeObject(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Метод readObjectиспользуется для считывания объекта из потока. Безопасное приведение Java должно использоваться
    // для получения нужного типа. В Java строки и массивы являются объектами и обрабатываются как объекты во время сериализации.
    // При чтении они должны быть приведены к ожидаемому типу.
    public Object readObject() {
        try {
            return inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);// не требует обработки
        }
    }

    public void close() {
        try {
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
