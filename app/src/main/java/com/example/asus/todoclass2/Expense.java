package com.example.asus.todoclass2;

import java.io.Serializable;

/**
 * Created by manishakhattar on 22/06/17.
 */

public class Expense implements Serializable{

    String title;
    int id;
    int position;
    double price;
    String category;
    String description;

    public Expense(String title,int id, double price, String category) {
        this.title = title;
        this.id=id;
        this.price = price;
        this.category = category;
    }
}
