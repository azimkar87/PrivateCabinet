package com.example.user.privatecabinet;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import java.math.BigInteger;
import android.widget.ArrayAdapter;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.widget.Toast;
import com.example.user.privatecabinet.myQuery;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    final String NAMESPACE = "http://tempuri.org/";
    final String URL = "http://212.112.121.170:3356/PaymentHistory.asmx";
    final String SOAP_ACTION = "http://tempuri.org/GetTableByLS";
    final String METHOD_NAME = "GetTableByLS";


    Button Mybutton;
    String LS="";
    String[] currentBalanceInfo;
    String [] [] payments;
    String [] [] meterReading;
    String Address="";
    String Fio;
    TextView addresstextViewOnNavMine;
    TextView lvFioTextViewOnNavMine;
    WebView webView;
    Intent myIntent;
    Bundle b;


    String dolg, peniaDolga, oplata_subsidia, otoplenieBezPU, toBezPU, itogoBezPU, GV, toGV, itogoGV, penia, itogoZaTek, itogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      webView=(WebView) findViewById(R.id.wv);
   webView.getSettings().setJavaScriptEnabled(true);
 //  webView.loadDataWithBaseURL(null,"Http://teploseti.kg/", "text/html", "UTF-8", null);
  webView.loadUrl("Http://teploseti.kg/");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        myIntent=getIntent();
        LS=myIntent.getStringExtra("ls");
        Fio=myIntent.getStringExtra("fio");
        Address=myIntent.getStringExtra("address");
        b = myIntent.getExtras();
        payments= (String[][])b.getSerializable("payments");
        meterReading=(String[][])b.getSerializable("meterreading");
        addresstextViewOnNavMine=(TextView)headerView.findViewById(R.id.addresstextViewOnNav);
        lvFioTextViewOnNavMine=(TextView)headerView.findViewById(R.id.lvFio);
        lvFioTextViewOnNavMine.setText(Fio);
        addresstextViewOnNavMine.setText(Address);
        currentBalanceInfo=myIntent.getStringArrayExtra("balance");
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        for (int i=0;i<=3;i++)
        {
            for (int j=0;j<=meterReading[0].length-1;j++)
            {
                Log.i("MeterReading", String.valueOf(i)+"*"+String.valueOf(i)+"-"+meterReading[i][j]);
            }
        }

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_current_balance) {
            Intent intent = new Intent(getApplicationContext(), CurrentBalance.class);
            intent.putExtra("balance", currentBalanceInfo);
            startActivity(intent);

        }  else if (id == R.id.nav_history_payment) {
            Intent intent = new Intent(getApplicationContext(), HistoryPayment.class);
            Bundle myBundle=new Bundle();
            myBundle.putSerializable("payments",payments);
            intent.putExtras(myBundle);
            startActivity(intent);
        }
        else if (id == R.id.nav_exit) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
        }
        else if (id == R.id.nav_history_meter_reading) {
            Intent intent = new Intent(getApplicationContext(), HistoryMeterReading.class);
            Bundle myBundle=new Bundle();
            myBundle.putSerializable("meterreading",meterReading);
            intent.putExtras(myBundle);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




 }












