package com.example.user.privatecabinet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;



public class HistoryPayment extends AppCompatActivity {

    String [] [] payments;
    ListView lvHistoryPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> map;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date pDate=null;
        String formattedDate="";
        String pType="";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_payment);
        lvHistoryPayment=(ListView) findViewById(R.id.lvHistoryPayments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("История оплаты");
    Intent myIntent=getIntent();
    Bundle myBundle=myIntent.getExtras();
    payments=(String[][])myBundle.getSerializable("payments");
    int col=payments.length;
    int row=payments[0].length-1;


    for (int pRow=0; pRow<=row;pRow++)
    {
        if (payments[1][pRow].equals("0")) continue;
        map=new HashMap<>();
        try {

            pDate =(Date)df.parse(payments[0][pRow].substring(0,10));
            formattedDate = df.format(pDate);
        }
        catch (ParseException e) {
        e.printStackTrace();
    }

        if (payments[2][pRow].equals("3"))
        {
            pType = "Оплата";
        }
         else if (payments[2][pRow].equals("4")) {
            pType = "ТО домкомы";
        }
        else if (payments[2][pRow].equals("5")) {
            pType = "Сторно";
        }

        else if (payments[2][pRow].equals("6")) {
            pType = "Возмещение за ТО";
        }
        else if (payments[2][pRow].equals("7")) {
            pType = "Перекидка";
        }
        else if (payments[2][pRow].equals("8")) {
            pType = "Перенос налога";
        }
        else if (payments[2][pRow].equals("9")) {
            pType = "Субсидия";
        }
        else if (payments[2][pRow].equals("10")) {
            pType = "Республиканский бюджет";
        }
        else if (payments[2][pRow].equals("11")) {
            pType = "Местный бюджет";
        }

        else if (payments[2][pRow].equals("12")) {
            pType = "Энергетики";
        }
        else if (payments[2][pRow].equals("13")) {
            pType = "Перенос ТО на КУ";
        }
        else if (payments[2][pRow].equals("14")) {
            pType = "Перенос пени на КУ";
        }
        else if (payments[2][pRow].equals("15")) {
            pType = "Коррект.(дебит-кредит)";
        }
        else if (payments[2][pRow].equals("16")) {
            pType = "Возврат ТО";
        }
        else if (payments[2][pRow].equals("17")) {
            pType = "Списание";
        }
        else if (payments[2][pRow].equals("18")) {
            pType = "Возмещение";
        }

        map.put("DateAndType", payments[0][pRow].substring(0,10)+"  "+pType);
        map.put("Sum", payments[1][pRow]+" Сом");
        arrayList.add(map);
    }

        SimpleAdapter adapter =new SimpleAdapter(this,arrayList,android.R.layout.simple_list_item_2,new String[]{"DateAndType", "Sum"},new int[]{android.R.id.text1, android.R.id.text2});
        lvHistoryPayment.setAdapter(adapter);

    }

}
