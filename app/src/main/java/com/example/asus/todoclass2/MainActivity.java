package com.example.asus.todoclass2;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.ConnectException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnCheckBoxClickedListener {

    ListView listView;
    //    ArrayList<String> expenseList;
    ArrayList<Expense> expenseList;
    ExpenseListAdapter expenseListAdapter;
//    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Tasks");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.expenseListView);
        expenseList = new ArrayList<>();
//        String str = "";
//        for (int i = 0; i < 20; i++) {
//            Expense e = new Expense();
//            e.position = i+1;
//            e.title =   "Expense " + (i + 1);
//            e.price = 100 + i*10;
//            e.category = "Food";
//            expenseList.add(e);
//            //    str = str + expenseList.get(i) + ";";
//        }


        expenseListAdapter = new ExpenseListAdapter(this, expenseList);
//        expenseListAdapter.setOnButtonClickedListener = (this);
        expenseListAdapter.setOnCheckClickedListener(this);
        listView.setAdapter(expenseListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //parent.getAdapter().getItem(position);

                Intent i = new Intent(MainActivity.this, ExpenseDetailActivity.class);
                i.putExtra(IntentConstants.EXPENSE_TITLE,expenseList.get(position).title);
                i.putExtra("category",expenseList.get(position).category);
                i.putExtra("price",expenseList.get(position).price);
              i.putExtra("id",expenseList.get(position).id);
              i.putExtra("epoch",expenseList.get(position).epoch);
                //startActivity(i);
                startActivityForResult(i, 1);

//                ExpenseDetailActivity.title = "abcd";

//                Toast.makeText(MainActivity.this, expenseList.get(position)
//                        + " Clicked ", Toast.LENGTH_SHORT).show();

            }
        });

        updateExpenseList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent i = new Intent(MainActivity.this, ExpenseDetailActivity.class);

                i.putExtra(IntentConstants.EXPENSE_TITLE,"Add");
                startActivityForResult(i,2);

            }
        });

    }

    private void updateExpenseList() {

        ExpenseOpenHelper expenseOpenHelper = ExpenseOpenHelper.getOpenHelperInstance(this);
        expenseList.clear();
        SQLiteDatabase database = expenseOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(ExpenseOpenHelper.Expense_Table_Name,null,null,null,null, null, null);
//       String column[] = {ExpenseOpenHelper.Expense_TITLE};
//       Cursor cursor = database.query(ExpenseOpenHelper.Expense_Table_Name,column,ExpenseOpenHelper.Expense_Category+"= food",null,null, null, null);
        while(cursor.moveToNext()){

            String title = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.Expense_TITLE));
            double price = cursor.getDouble(cursor.getColumnIndex(ExpenseOpenHelper.Expense_Price));
            int id = cursor.getInt(cursor.getColumnIndex(ExpenseOpenHelper.Expense_Id));
            String category = cursor.getString(cursor.getColumnIndex(ExpenseOpenHelper.Expense_Category));
            long epoch = cursor.getLong(cursor.getColumnIndex(ExpenseOpenHelper.Expense_DateTIme));
            Expense e = new Expense(title,id,price,category,epoch);
            expenseList.add(e);
        }

        expenseListAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {

                updateExpenseList();
//                String newTitle = data.getStringExtra(IntentConstants.EXPENSE_TITLE);
//                int position1 = data.getIntExtra("position",-1);
//                expenseList.get(position1).title = data.getStringExtra(IntentConstants.EXPENSE_TITLE);
//                expenseList.get(position1).category = data.getStringExtra("category");
//                expenseList.get(position1).price = data.getIntExtra("price",-1);
//                Log.i("MainActivityTag", "New Title " + newTitle);
            }else if(resultCode == RESULT_CANCELED){
                      return;
            } }

            if(requestCode == 2) {

                if (resultCode == RESULT_OK){
//                    Expense e = new Expense();
//                    e.title=data.getStringExtra(IntentConstants.EXPENSE_TITLE);
//                    e.category = data.getStringExtra("category");
//                    e.price = data.getDoubleExtra("price",-1);
//                    e.position=expenseList.size()+1;
//                    expenseList.add(e);
                    updateExpenseList();

                    expenseListAdapter.notifyDataSetChanged();
                }else if (resultCode==RESULT_CANCELED){
                    return;
                }
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(R.id.add == id){

            Intent i = new Intent(MainActivity.this, ExpenseDetailActivity.class);

            i.putExtra(IntentConstants.EXPENSE_TITLE,"Add");
            startActivityForResult(i,2);

        }
//            else if(R.id.remove == id){
//
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//
//            builder.setTitle("Delete");
//            builder.setCancelable(false);
////            builder.setMessage("Are you sure you want to delete ??");
//
//            View v = getLayoutInflater().inflate(R.layout.dialog_view,null);
//
//            final  TextView t = (TextView) v.findViewById(R.id.textview);
//            final EditText et = (EditText) v.findViewById(R.id.edittext);
//            t.setText("Enter the Item ID you want to delete.");
//            builder.setView(v);
//
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    int num = Integer.parseInt(et.getText().toString());
//
//                    for(int i = 0; i<expenseList.size();i++)
//                    {  if (expenseList.get(i).id==num)
//                        expenseList.remove(i);}
////                    expenseList.remove(num-1);
////                    for (int i = num-1;i<expenseList.size();i++){
////                        expenseList.get(i).id=i+1;
////                    }
//
//                    expenseListAdapter.notifyDataSetChanged();
//                    ExpenseOpenHelper expenseOpenHelper = ExpenseOpenHelper.getOpenHelperInstance(MainActivity.this);
//                    SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();
//                    database.delete(ExpenseOpenHelper.Expense_Table_Name,ExpenseOpenHelper.Expense_Id + "=" +num,null);
////                    ContentValues cv = new ContentValues();
////                    for(int i=0;i<expenseList.size();i++){
////                        cv.put(ExpenseOpenHelper.Expense_Id,expenseList.get(i).id);
////                        cv.put(ExpenseOpenHelper.Expense_TITLE,expenseList.get(i).title);
////                        cv.put(ExpenseOpenHelper.Expense_Category,expenseList.get(i).category);
////                        cv.put(ExpenseOpenHelper.Expense_Price,expenseList.get(i).price);
////                        database.insert(ExpenseOpenHelper.Expense_Table_Name,null,cv);
////                    }
//
//
//                }
//            });
//            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//
//            AlertDialog dialog = builder.create();
//            dialog.show(); }


         else if (id == R.id.aboutUs){
            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("https://www.codingninjas.in");
            i.setData(uri);
            startActivity(i);
        }else if(id == R.id.contactUs) {
            Intent i = new Intent();
            i.setAction(Intent.ACTION_DIAL);
            Uri uri = Uri.parse("tel:12345");
            i.setData(uri);
            startActivity(i);
        }else if(id == R.id.feedback){
                Intent i = new Intent();
                i.setAction(Intent.ACTION_SENDTO);
                Uri uri = Uri.parse("mailto:manisha@condingninjas.in");
                i.setData(uri);
            i.putExtra(Intent.EXTRA_SUBJECT,"Feedack");
            i.setData(uri);
            if(i.resolveActivity(getPackageManager()) !=null) {
                startActivity(i);
            }
        }

        return true;

    }

    @Override
    public void CheckBoxClicked(int Id) {
        for(int i = 0; i<expenseList.size();i++)
        {  if (expenseList.get(i).id==Id)
            expenseList.remove(i);}
        expenseListAdapter.notifyDataSetChanged();
        ExpenseOpenHelper expenseOpenHelper = ExpenseOpenHelper.getOpenHelperInstance(MainActivity.this);
        SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();
        database.delete(ExpenseOpenHelper.Expense_Table_Name,ExpenseOpenHelper.Expense_Id + "=" +Id,null);
        Toast.makeText(this, "Task Finished", Toast.LENGTH_SHORT).show();

//

    }
}

