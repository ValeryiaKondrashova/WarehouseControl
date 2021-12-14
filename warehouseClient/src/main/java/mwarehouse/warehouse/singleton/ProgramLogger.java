package mwarehouse.warehouse.singleton;

import java.io.FileWriter;
import java.io.IOException;

public class ProgramLogger {
    private static ProgramLogger programLogger;
    //private static String logFile="This is log file.";  // Создали строку (вместо файла), куда будем помещать состояние о нашей программе

    public static synchronized ProgramLogger getProgramLogger(){  // делаем этот метод синхронизированным. В случае работы когда два клиента работают и хотят сохранить в файл, чтобы один из подождал пока закончит первый.
                                                                  // если второй поток обратиться к методу, то второй поток подождет пока закончит работу с этим классом первый поток
        if(programLogger== null){
            programLogger = new ProgramLogger();
        }
        return programLogger;
    }

    private ProgramLogger(){

    }

    public void addLogInfo(String logInfo) throws IOException { //Эта та инфа, которую хотим занести в файл
        //logFile += logInfo + "\n";  //получили инфу, добавили в общую строку(или добавили в файл

        FileWriter fileWriter = new FileWriter("logFileCondition.txt", true);
        fileWriter.write(logInfo + "\n");
        fileWriter.close();

    }

//    public void showLogFile(){
//        System.out.println(logFile);
//    }

}
