package com.example.asus.todoclass2;

import java.io.Serializable;

/**
 * Created by manishakhattar on 22/06/17.
 */

public class Expense implements Serializable{

    String title;
    int id;
    long epoch;
    int timeFlag;

    public Expense(String title,int id, long epoch , int timeFlag) {
        this.title = title;
        this.id=id;
         this.epoch = epoch;
        this.timeFlag = timeFlag;
    }
}
