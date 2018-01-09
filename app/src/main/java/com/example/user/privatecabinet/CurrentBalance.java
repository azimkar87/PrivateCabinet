package com.example.user.privatecabinet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class CurrentBalance extends AppCompatActivity {
    String[] currentBalanceInfo;
    ListView lvBalanceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_balance);
        setTitle("Текущий баланс");
        lvBalanceInfo=(ListView) findViewById(R.id.lv);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
        HashMap<String, String> map;
        Intent myIntent=getIntent();
        currentBalanceInfo=myIntent.getStringArrayExtra("balance");

        map=new HashMap<>();
        map.put("Caption","Долг");
        map.put("Value", currentBalanceInfo[0]);
        arrayList.add(map);

        map=new HashMap<>();
        map.put("Caption","Пеня долга");
        map.put("Value", currentBalanceInfo[1]);
        arrayList.add(map);

        map=new HashMap<>();
        map.put("Caption","Оплата/Субсидия");
        map.put("Value", currentBalanceInfo[2]);
        arrayList.add(map);

        map=new HashMap<>();
        map.put("Caption","Отопление без прибора учета");
        map.put("Value", currentBalanceInfo[3]);
        arrayList.add(map);

        map=new HashMap<>();
        map.put("Caption","Техобслуживание без прибора учета");
        map.put("Value", currentBalanceInfo[4]);
        arrayList.add(map);

        map=new HashMap<>();
        map.put("Caption","Итого без прибора учета");
        map.put("Value", currentBalanceInfo[5]);
        arrayList.add(map);

        map=new HashMap<>();
        map.put("Caption","Горячая вода");
        map.put("Value", currentBalanceInfo[6]);
        arrayList.add(map);

        map=new HashMap<>();
        map.put("Caption","Техослуживание горячей воды");
        map.put("Value", currentBalanceInfo[7]);
        arrayList.add(map);

        map=new HashMap<>();
        map.put("Caption","Итого горячей воды");
        map.put("Value", currentBalanceInfo[8]);
        arrayList.add(map);

        map=new HashMap<>();
        map.put("Caption","Пеня");
        map.put("Value", currentBalanceInfo[9]);
        arrayList.add(map);

        map=new HashMap<>();
        map.put("Caption","Итого за текущий месяц");
        map.put("Value", currentBalanceInfo[10]);
        arrayList.add(map);

        map=new HashMap<>();
        map.put("Caption","Итого");
        map.put("Value", currentBalanceInfo[11]);
        arrayList.add(map);

        SimpleAdapter adapter =new SimpleAdapter(this,arrayList,android.R.layout.simple_list_item_2,new String[]{"Caption", "Value"},new int[]{android.R.id.text1, android.R.id.text2});
        lvBalanceInfo.setAdapter(adapter);


    }

}
