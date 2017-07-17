package com.example.asus.todoclass2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 6/27/2017.
 */

 class ExpenseOpenHelper extends SQLiteOpenHelper {

    public  final static String Expense_Table_Name = "Expense";
    public  final static String Expense_TITLE = "title";
    public  final static String Expense_Id = "_id";
//    public  final static String Expense_Price = "price";
//    public  final static String Expense_Category = "category";
    public  final static String Expense_DateTIme = "dateTime";
    public final static  String Expense_TimeFlag="timeFlag";
    public static  ExpenseOpenHelper expenseOpenHelper;

    public  static  ExpenseOpenHelper getOpenHelperInstance (Context context){

        if (expenseOpenHelper == null){
            expenseOpenHelper = new ExpenseOpenHelper(context);
        }
        return expenseOpenHelper;
    }

    private   ExpenseOpenHelper(Context context){
        super(context, "Expenses.dp",null,1);
    }//object cannot b created outside class.

    @Override
    public void onCreate(SQLiteDatabase db) {
      String query = "create table " + Expense_Table_Name +"( " + Expense_Id +" integer primary key autoincrement, " + Expense_TITLE
              +" text, " + Expense_TimeFlag + " integer, " + Expense_DateTIme + " bigint);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Column insert
        //Drop table
        //Create


    }
}
