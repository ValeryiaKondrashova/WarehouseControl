package mwarehouse.warehouse.entity;

import java.io.Serializable;

public enum Command implements Serializable {
    CREATE,
    CREATE1,
    READ,
    READ1,
    UPDATE,
    UPDATE1,
    DELETE,
    DELETE1,
    EXIT,
    READMANUFACTURER,
    READTYPE,
    READSTORAGE,
    READTASKS,
    READMODEL,
    DELETETASK,
    CREATETASK,
}
