package com.example.asus.todoclass2;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ExpenseDetailActivity extends AppCompatActivity {


    String title;
    int Id;
    EditText titleTextView;
    LinearLayout linearLayout;
//    EditText categoryTextView;
//    EditText priceTextView;
    EditText dateEditText;
    EditText timeEditText;
    long time;
    long oldtime;
    Calendar dateTime;
    int flag=0;
    int flag2=0;
    int alarmId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);
        titleTextView = (EditText) findViewById(R.id.expenseDetailTitleTextView);
        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
//        categoryTextView = (EditText) findViewById(R.id.expenseDetailCategoryTextView);
//        priceTextView = (EditText) findViewById(R.id.expenseDetailPriceTextView);
        dateEditText = (EditText) findViewById(R.id.expenseDetailDateTextView);
        timeEditText = (EditText) findViewById(R.id.expenseDetailTimeTextView);
        Button submitButton = (Button) findViewById(R.id.expenseDetailSubmitButton);
        Button dateButton = (Button) findViewById(R.id.dateButton);
        Button timeButton = (Button) findViewById(R.id.timeButton);
        Intent i = getIntent();
        title   = i.getStringExtra(IntentConstants.EXPENSE_TITLE);
        oldtime = i.getLongExtra("epoch",-1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(oldtime);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DATE);
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int min = calendar.get(Calendar.MINUTE);

        Id = i.getIntExtra("id", -1);
        setTitle(title);
         if (Id!= -1)
         {   setTitle("Update Task");
//             double oldprice = i.getDoubleExtra("price",-1);
             titleTextView.setText(title);
//             categoryTextView.setText(i.getStringExtra("category"));
//             priceTextView.setText(oldprice+"");

             if (oldtime != 0)
             {
             dateEditText.setText(day + "/ "  + (month + 1) +"/" + year );
                 linearLayout.setVisibility(View.VISIBLE);
                 if(hour==0&&min==0)
                     timeEditText.setHint("Time not set");
                 else
                     timeEditText.setText(hour + ":" + min);}

         }


        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                int month = newCalendar.get(Calendar.MONTH);  // Current month
                int year = newCalendar.get(Calendar.YEAR);   // Current year
                int day= newCalendar.get(Calendar.DATE);
                showDatePicker(ExpenseDetailActivity.this, year, month, day );
            }
        });

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                int month = newCalendar.get(Calendar.MONTH);  // Current month
                int year = newCalendar.get(Calendar.YEAR);   // Current year
                int day= newCalendar.get(Calendar.DATE);
                showDatePicker(ExpenseDetailActivity.this, year, month, day );

            }
        });




        timeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar newCalendar =  Calendar.getInstance();
                int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
                int mins = newCalendar.get(Calendar.MINUTE);
                showTimePicker(ExpenseDetailActivity.this,hour,mins);
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar =  Calendar.getInstance();
                int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
                int mins = newCalendar.get(Calendar.MINUTE);
                showTimePicker(ExpenseDetailActivity.this,hour,mins);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = titleTextView.getText().toString();
//                String newCategory = categoryTextView.getText().toString();
//                String price = priceTextView.getText().toString();
//                double newPrice = 0;

                if(newTitle.trim().isEmpty()){
                    titleTextView.setError("This Field cannont be empty.");
                    return;
                }

                String timeEdit =timeEditText.getText().toString();

                if(flag==1 && flag2==0){

                    if (timeEdit.trim().isEmpty()) {
                        dateTime.set(Calendar.HOUR_OF_DAY,0);
                        dateTime.set(Calendar.MINUTE,0);
                        dateTime.set(Calendar.SECOND,0);
                        time=dateTime.getTime().getTime();
                    }
                    else{
                        dateTime.set(Calendar.HOUR_OF_DAY,hour);
                        dateTime.set(Calendar.MINUTE,min);
                        dateTime.set(Calendar.SECOND,0);
                        time=dateTime.getTime().getTime();
                    }

                }

                if(flag==0 && flag2==1){
                    dateTime.set(year,month,day);
                    dateTime.set(Calendar.SECOND,0);
                    time=dateTime.getTime().getTime();}





//                if (!price.isEmpty()){
//                    newPrice = Double.parseDouble(price);
//                }


               ExpenseOpenHelper expenseOpenHelper = ExpenseOpenHelper.getOpenHelperInstance(ExpenseDetailActivity.this);
                SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(ExpenseOpenHelper.Expense_TITLE, newTitle);
//                cv.put(ExpenseOpenHelper.Expense_Category,newCategory);
//                cv.put(ExpenseOpenHelper.Expense_Price, newPrice);

                if(Id!=-1 && time ==0)
                    cv.put(ExpenseOpenHelper.Expense_DateTIme, oldtime);
                else
                    cv.put(ExpenseOpenHelper.Expense_DateTIme,time);

                if(Id==-1)
                    database.insert(ExpenseOpenHelper.Expense_Table_Name,null,cv);
                else
                    database.update(ExpenseOpenHelper.Expense_Table_Name,cv,ExpenseOpenHelper.Expense_Id + "=" + Id, null);

                if (!timeEditText.getText().toString().isEmpty()){
                    AlarmManager am = (AlarmManager) ExpenseDetailActivity.this.getSystemService(Context.ALARM_SERVICE);
                Intent i = new Intent (ExpenseDetailActivity.this,Alarm.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(ExpenseDetailActivity.this,1,i,0);
                if(Id!=-1 && time ==0)
                am.set(AlarmManager.RTC,oldtime,pendingIntent);
                else
                am.set(AlarmManager.RTC,time,pendingIntent);}

//                Intent i = new Intent();
//                i.putExtra(IntentConstants.EXPENSE_TITLE, newTitle);
//                i.putExtra("position",position1);
//                i.putExtra("price",newPrice);
//                i.putExtra("category",newCategory);
                setResult(RESULT_OK);
                finish();

            }
        });

        // Steps for Date picker
        // Will show Date picker dialog on clicking edit text




    }


    public void showDatePicker(Context context, int initialYear, int initialMonth, int initialDay) {

        // Creating datePicker dialog object
        // It requires context and listener that is used when a date is selected by the user.

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    //This method is called when the user has finished selecting a date.
                    // Arguments passed are selected year, month and day
                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {

                        // To get epoch, You can store this date(in epoch) in database
                        dateTime = Calendar.getInstance();
                        dateTime.set(year, month, day);
                        dateTime.set(Calendar.SECOND,0);
                        time= dateTime.getTime().getTime();
                        // Setting date selected in the edit text
                        dateEditText.setText(day + "/" + (month + 1) + "/" + year);
                        linearLayout.setVisibility(View.VISIBLE);
                        flag=1;
                    }
                }, initialYear, initialMonth, initialDay);

        //Call show() to simply show the dialog
        datePickerDialog.show();



    }

    public void showTimePicker(Context context , int initialTime , int initialMinute){
         TimePickerDialog  timePickerDialog = new  TimePickerDialog(context ,
                 new TimePickerDialog.OnTimeSetListener() {
                     @Override
                     public void onTimeSet(TimePicker timePicker, int hourofday, int minute) {
                         if (flag==0)
                         {   dateTime=Calendar.getInstance();}
                         dateTime.set(Calendar.HOUR_OF_DAY, hourofday);
                         dateTime.set(Calendar.MINUTE, minute);
                         dateTime.set(Calendar.SECOND,0);
                         time = dateTime.getTime().getTime();
                         timeEditText.setText(hourofday + ":" + minute);
                         flag2=1;
                     }
                 },initialTime,initialMinute,false);
        timePickerDialog.show();








    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
