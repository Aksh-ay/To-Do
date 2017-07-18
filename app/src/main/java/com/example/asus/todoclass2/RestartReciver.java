package com.example.asus.todoclass2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

public class RestartReciver extends BroadcastReceiver {
    long currentTime;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.


        ExpenseOpenHelper expenseOpenHelper = ExpenseOpenHelper.getOpenHelperInstance(context);
        SQLiteDatabase database = expenseOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(ExpenseOpenHelper.Expense_Table_Name, null, null, null, null, null, null);
//       String column[] = {ExpenseOpenHelper.Expense_TITLE};
//       Cursor cursor = database.query(ExpenseOpenHelper.Expense_Table_Name,column,ExpenseOpenHelper.Expense_Category+"= food",null,null, null, null);

        while (cursor.moveToNext()) {

            String title = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.Expense_TITLE));
            long epoch = cursor.getLong(cursor.getColumnIndex(ExpenseOpenHelper.Expense_DateTIme));
            int id = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.Expense_Id));
            int timeFlag = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.Expense_TimeFlag));


//            double price = cursor.getDouble(cursor.getColumnIndex(ExpenseOpenHelper.Expense_Price));
//            String category = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.Expense_Category));
//            int timeFlag = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.Expense_TimeFlag));

            Calendar calendar = Calendar.getInstance();
            currentTime =calendar.getTime().getTime();
           if(timeFlag==1 && currentTime<epoch)
            {AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent (context,Alarm.class);
             i.putExtra("Task",title);
             i.putExtra("epoch",epoch);
            i.putExtra("ID",id);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,id,i,0);

            am.set(AlarmManager.RTC,epoch,pendingIntent);}


        }
    }
}