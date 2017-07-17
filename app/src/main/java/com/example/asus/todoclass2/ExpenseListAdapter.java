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
import java.util.Calendar;

;

/**
 * Created by manishakhattar on 22/06/17.
 */

public class ExpenseListAdapter extends ArrayAdapter<Expense> {

    ArrayList<Expense> expenseArrayList;
    Context context;
    Long epochTime;
    String[] months = {"Jan" , "Feb" ,"Mar" ,"Apr" ,"May" ,"Jun" ,"Jul" ,"Aug" ,"Sep" ,"Oct" ,"Nov" ,"Dec"};
    String Month;
    int timeflag;


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
//        TextView serialNumberTextView;
        TextView nameTextView;
        TextView dateTextView;
        TextView timeTextView;

//        TextView categoryTextView ;
//        TextView priceTextView ;

        ExpenseViewHolder(CheckBox checkBox , TextView nameTextView,TextView dateTextView, TextView timeTextView){
            this.checkBox = checkBox;
//            this.serialNumberTextView = serialNumberTextView;
            this.nameTextView = nameTextView;
            this.dateTextView = dateTextView;
            this.timeTextView = timeTextView;
//            this.categoryTextView = categoryTextView;
//            this.priceTextView = priceTextView;
        }

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
           convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);
//            TextView serialNumberTextView = (TextView)convertView.findViewById(R.id.serialNumberTextView);
            CheckBox checkBox = (CheckBox)convertView.findViewById(R.id.checkbox);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.expenseNameTextView);
            TextView dateTextView=(TextView)convertView.findViewById(R.id.expenseDateTextView);
//            TextView categoryTextView = (TextView) convertView.findViewById(R.id.expenseCategoryTextView);
//            TextView priceTextView = (TextView) convertView.findViewById(R.id.expensePriceTextView);
            TextView timeTextView = (TextView)convertView.findViewById(R.id.expenseTimeTextView);
            ExpenseViewHolder expenseViewHolder = new ExpenseViewHolder(checkBox ,nameTextView,dateTextView,timeTextView);
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
//        expenseViewHolder.serialNumberTextView.setText(e.id+"");
        expenseViewHolder.nameTextView.setText(e.title);
        epochTime = e.epoch;
        timeflag = e.timeFlag;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(epochTime);
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DATE);
        final int hourofday = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        if (epochTime!=0)
        {  expenseViewHolder.dateTextView.setVisibility(View.VISIBLE);
            for (int i=1 ; i<=12;i++)
            {    if(i==month)
                Month = months[i];  }
            expenseViewHolder.dateTextView.setText(Month +" " + day + ", " + year);
             if(timeflag==1) {
                 expenseViewHolder.timeTextView.setVisibility(View.VISIBLE);
                 if (hourofday>12){
                     if (minute<10)
                         expenseViewHolder.timeTextView.setText(", " +(hourofday-12) + ":" + "0"+ minute + " PM");
                     else
                         expenseViewHolder.timeTextView.setText(", " +(hourofday-12) + ":" + minute+ " PM");
                 }
                 else if(hourofday<12 && hourofday>0) {
                     if (minute<10)
                         expenseViewHolder.timeTextView.setText(", " +hourofday + ":" + "0"+ minute + " AM");
                     else
                         expenseViewHolder.timeTextView.setText(", " +hourofday + ":" + minute+ " AM");
                 }
                 else if (hourofday==12) {
                     if (minute < 10)
                         expenseViewHolder.timeTextView.setText(", " +hourofday + ":" + "0" + minute + " PM");
                     else
                         expenseViewHolder.timeTextView.setText(", " + hourofday + ":" + minute + " PM");
                 }

                 else if (hourofday==0) {
                     if (minute < 10)
                         expenseViewHolder.timeTextView.setText(", " +(hourofday+12) + ":" + "0" + minute + " AM");
                     else
                         expenseViewHolder.timeTextView.setText(", " +(hourofday+12) + ":" + minute + " AM");
                 }

             }

        }




//        expenseViewHolder.categoryTextView.setText(e.category);
//        expenseViewHolder.priceTextView.setText(e.price+"");

        return  convertView;
    }
}

  interface OnCheckBoxClickedListener{
     void  CheckBoxClicked (int Id);
}

//interface  OnListButtonClickedListener{
//    void listButtonClicked( View v, int pos);
//}