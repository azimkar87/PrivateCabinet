package com.example.user.privatecabinet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.SimpleExpandableListAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class HistoryMeterReading extends AppCompatActivity {
    String [] [] meterReading;
    ListView lvsample;
    ExpandableListView eLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_meter_reading);
        eLv=(ExpandableListView) findViewById(R.id.lvExp);
        setTitle("История показаний");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent myIntent=getIntent();
        Bundle myBundle=myIntent.getExtras();
        meterReading=(String[][])myBundle.getSerializable("meterreading");
        int row=meterReading[0].length-1;
        String[] groups=new String [row+1];

        for (int i=0;i<=row;i++) {groups[i]=meterReading[0][i];}
       //Для получения уникальных значений
        Set<String> set=new HashSet<String>(Arrays.asList(groups));
        groups=set.toArray(new String[set.size()]);
        //Arrays.sort(groups);
        Arrays.sort(groups, Collections.reverseOrder());
        // коллекция для групп
        ArrayList<Map<String, String>> groupData;

        // коллекция для элементов одной группы
        ArrayList<Map<String, String>> childDataItem;

        // общая коллекция для коллекций элементов
        ArrayList<ArrayList<Map<String, String>>> childData;
        // в итоге получится childData = ArrayList<childDataItem>
        Map<String, String> m;

        groupData = new ArrayList<Map<String, String>>();
        for (String group : groups) {
            // заполняем список атрибутов для каждой группы
            m = new HashMap<String, String>();
            m.put("Year", group); // имя компании
            groupData.add(m);
        }
        // список атрибутов групп для чтения
        String groupFrom[] = new String[] {"Year"};
        // список ID view-элементов, в которые будет помещены атрибуты групп
        int groupTo[] = new int[] {android.R.id.text1};

        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<Map<String, String>>>();

        // создаем коллекцию элементов для первой группы


        // заполняем список атрибутов для каждого элемента
        for (int yearCounter=0;yearCounter<=(groups.length-1);yearCounter++) {
            childDataItem = new ArrayList<Map<String, String>>();
           for (int monthCounter=0;monthCounter<=row;monthCounter++ ) {
              if (groups[yearCounter]==meterReading[0][monthCounter]) {
                  String monthString;
                  switch(Integer.parseInt(meterReading[1][monthCounter]))
                  {
                      case 1:  monthString = "Январь";
                          break;
                      case 2:  monthString = "Февраль";
                          break;
                      case 3:  monthString = "Март";
                          break;
                      case 4:  monthString = "Апрель";
                          break;
                      case 5:  monthString = "Май";
                          break;
                      case 6:  monthString = "Июнь";
                          break;
                      case 7:  monthString = "Июль";
                          break;
                      case 8:  monthString = "Август";
                          break;
                      case 9:  monthString = "Сентябрь";
                          break;
                      case 10: monthString = "Октябрь";
                          break;
                      case 11: monthString = "Ноябрь";
                          break;
                      case 12: monthString = "Декабрь";
                          break;
                      default: monthString =" ";
                          break;
                  }
                  m = new HashMap<String, String>();
                  m.put("monthName", monthString);
                  m.put("meterReading","Показание прибора: "+meterReading[3][monthCounter] );
                  childDataItem.add(m);
              }
           }
            childData.add(childDataItem);
        }

        // список атрибутов элементов для чтения
        String childFrom[] = new String[] {"monthName","meterReading"};
        // список ID view-элементов, в которые будет помещены атрибуты элементов
        int childTo[] = new int[] {android.R.id.text1, android.R.id.text2};
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(this,groupData,android.R.layout.simple_expandable_list_item_1,groupFrom,groupTo,childData,android.R.layout.simple_list_item_2,childFrom,childTo);
        eLv.setAdapter(adapter);

    }

}
