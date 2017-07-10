package com.example.asus.todoclass2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ExpenseDetailActivity extends AppCompatActivity {


    String title;
    int Id;
    EditText titleTextView;
    EditText categoryTextView;
    EditText priceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);
        titleTextView = (EditText) findViewById(R.id.expenseDetailTitleTextView);
        categoryTextView = (EditText) findViewById(R.id.expenseDetailCategoryTextView);
        priceTextView = (EditText) findViewById(R.id.expenseDetailPriceTextView);
        Button submitButton = (Button) findViewById(R.id.expenseDetailSubmitButton);
        Intent i = getIntent();
        title   = i.getStringExtra(IntentConstants.EXPENSE_TITLE);
        Id = i.getIntExtra("id", -1);
        setTitle(title);
         if (Id!= -1)
         { double oldprice = i.getDoubleExtra("price",-1);
             titleTextView.setText(title);
             categoryTextView.setText(i.getStringExtra("category"));
             priceTextView.setText(oldprice+"");
         }
            submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = titleTextView.getText().toString();
                String newCategory = categoryTextView.getText().toString();
                String price = priceTextView.getText().toString();
                double newPrice = 0;

                if(newTitle.trim().isEmpty()){
                    titleTextView.setError("This Field cannont be empty.");
                    return;
                }

                if (!price.isEmpty()){
                    newPrice = Double.parseDouble(price);
            }
               ExpenseOpenHelper expenseOpenHelper = ExpenseOpenHelper.getOpenHelperInstance(ExpenseDetailActivity.this);
                SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(ExpenseOpenHelper.Expense_TITLE, newTitle);
                cv.put(ExpenseOpenHelper.Expense_Category,newCategory);
                cv.put(ExpenseOpenHelper.Expense_Price, newPrice);

                if(Id==-1)
                    database.insert(ExpenseOpenHelper.Expense_Table_Name,null,cv);
                else
                    database.update(ExpenseOpenHelper.Expense_Table_Name,cv,ExpenseOpenHelper.Expense_Id + "=" + Id, null);
//                Intent i = new Intent();
//                i.putExtra(IntentConstants.EXPENSE_TITLE, newTitle);
//                i.putExtra("position",position1);
//                i.putExtra("price",newPrice);
//                i.putExtra("category",newCategory);
                setResult(RESULT_OK);
                finish();
            }
        });




    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
