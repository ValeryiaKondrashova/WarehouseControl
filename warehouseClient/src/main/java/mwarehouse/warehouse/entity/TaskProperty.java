package mwarehouse.warehouse.entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TaskProperty {
    private IntegerProperty id;
    private StringProperty task;

   public TaskProperty (Task taskk){
       id = new SimpleIntegerProperty(taskk.getId());
       task = new SimpleStringProperty(taskk.getTask());
   }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public String getTask() { return task.get(); }
    public StringProperty taskProperty() { return task; }



    public void setId(int id) { this.id.set(id); }

    public void setTask(String task) { this.task.set(task); }
}
