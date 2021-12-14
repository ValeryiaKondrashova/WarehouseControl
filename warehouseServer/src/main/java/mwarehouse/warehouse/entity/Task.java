package mwarehouse.warehouse.entity;

import java.io.Serializable;

public class Task implements Serializable {
    private int id;
    private String task;


    public Task(String task){ // когда клиент присылает информацию
        this.task = task;
    }

    public Task(int id, String task){
        this.id = id;
        this.task = task;
    }

    public void setId(int id) { this.id = id; }
    public void setTask(String task) { this.task = task; }

    public int getId() { return id; }
    public String getTask() { return task; }

}
