package com.example.asus.todoclass2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by manishakhattar on 22/06/17.
 */

public class ExpenseListAdapter extends ArrayAdapter<Expense> {

    ArrayList<Expense> expenseArrayList;
    Context context;

      OnCheckBoxClickedListener mlistener;
      void setOnCheckClickedListener(OnCheckBoxClickedListener listener){
          this.mlistener = listener;
      }

    //    OnListButtonClickedListener listener;
//    void setOnButtonClickedListener(OnListButtonClickedListener listerner){
//    this.listener = listener;
//}
    public ExpenseListAdapter(@NonNull Context context, ArrayList<Expense> expenseArrayList) {
        super(context, 0);
        this.context = context;
        this.expenseArrayList = expenseArrayList;
    }
    @Override
    public int getCount() {
        return expenseArrayList.size();
    }

    static class ExpenseViewHolder{

        CheckBox checkBox;
        TextView serialNumberTextView;
        TextView nameTextView;
        TextView categoryTextView ;
        TextView priceTextView ;

        ExpenseViewHolder(CheckBox checkBox , TextView serialNumberTextView , TextView nameTextView, TextView categoryTextView, TextView priceTextView){
            this.checkBox = checkBox;
            this.serialNumberTextView = serialNumberTextView;
            this.nameTextView = nameTextView;
            this.categoryTextView = categoryTextView;
            this.priceTextView = priceTextView;
        }

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
           convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);
            TextView serialNumberTextView = (TextView)convertView.findViewById(R.id.serialNumberTextView);
            CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.checkbox);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.expenseNameTextView);
            TextView categoryTextView = (TextView) convertView.findViewById(R.id.expenseCategoryTextView);
            TextView priceTextView = (TextView) convertView.findViewById(R.id.expensePriceTextView);
            ExpenseViewHolder expenseViewHolder = new ExpenseViewHolder(checkBox , serialNumberTextView,nameTextView, categoryTextView, priceTextView);
            convertView.setTag(expenseViewHolder);
            expenseViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int Id = expenseArrayList.get(position).id;
                    mlistener.CheckBoxClicked(Id);

                }
            });
//            expenseViewHolder.button.setOnClickListener(new View.OnClickListener() )



        }

        Expense e = expenseArrayList.get(position);
        ExpenseViewHolder expenseViewHolder = (ExpenseViewHolder)convertView.getTag();
        expenseViewHolder.checkBox.setChecked(false);
        expenseViewHolder.serialNumberTextView.setText(e.id+"");
        expenseViewHolder.nameTextView.setText(e.title);
        expenseViewHolder.categoryTextView.setText(e.category);
        expenseViewHolder.priceTextView.setText(e.price+"");

        return  convertView;
    }
}

  interface OnCheckBoxClickedListener{
     void  CheckBoxClicked (int Id);
}

//interface  OnListButtonClickedListener{
//    void listButtonClicked( View v, int pos);
//}