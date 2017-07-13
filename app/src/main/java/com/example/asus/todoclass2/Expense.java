package com.example.asus.todoclass2;

import java.io.Serializable;

/**
 * Created by manishakhattar on 22/06/17.
 */

public class Expense implements Serializable{

    String title;
    int id;
    long epoch;

    public Expense(String title,int id, long epoch) {
        this.title = title;
        this.id=id;
         this.epoch = epoch;
    }
}
