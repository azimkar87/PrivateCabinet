package com.example.user.privatecabinet;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {
Button signInbutton;
    EditText lsEditText;
    ProgressDialog pd;
    String LS="";
    SoapObject Sonuc;
    String[] currentBalanceInfo;
    String [] [] payments;
    String [] [] meterReading;
    String Address;
    String Fio;
    int ID;

    final String NAMESPACE = "http://tempuri.org/";
    final String URL = "http://212.112.121.170:3356/PaymentHistory.asmx";
    final String SOAP_ACTION = "http://tempuri.org/GetTableByLS";
    final String METHOD_NAME = "GetTableByLS";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        signInbutton=(Button) findViewById(R.id.button2);
        lsEditText=(EditText) findViewById(R.id.editText12);
        pd = new ProgressDialog(LoginActivity.this);
        signInbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LS=lsEditText.getText().toString();

                MyTask task1 = new MyTask("GetAddresByLS");
                task1.execute();
                pd.setMessage("Сбор информации..об абоненте");

                MyTask task2 = new MyTask("GetTableByLS");
                task2.execute();
                pd.setMessage("Сбор информации..о балансе");
                pd.show();

                MyTask task3 = new MyTask("GetAllHistoryMeterReading");
                task3.execute();
                pd.setMessage("Сбор информации..об истории показаний");

                MyTask task4 = new MyTask("GetAllHistoryPaymentsByLS");
                task4.execute();
                pd.setMessage("Сбор информации..об истории платежей");

            }
        });
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }
    }

    public class MyTask extends AsyncTask<String, Void, SoapObject> {
        myQuery mQ;
        public MyTask(String mqParameter)
        {
            mQ=new myQuery(mqParameter);
        }
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected SoapObject doInBackground(String... params) {
            if (mQ.methodName == "GetTableByLS") {
                SoapObject spObjectGonder = new SoapObject(NAMESPACE, METHOD_NAME);
                spObjectGonder.addProperty("ls", LS);
                spObjectGonder.addProperty("month", 11);
                spObjectGonder.addProperty("year", 2017);
                SoapObject treeAbonent = null;
                long unixTime = System.currentTimeMillis() / 1000L;
                String firstFive = String.valueOf(unixTime).substring(0, 5);
                spObjectGonder.addProperty("hash", md5Crypto(LS + "*" + firstFive + "*"));
                try {
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(spObjectGonder);
                    HttpTransportSE transport = new HttpTransportSE(URL);
                    transport.debug = true;
                    try {
                        transport.call(SOAP_ACTION, envelope);
                        SoapObject myResponse = (SoapObject) envelope.getResponse();
                        SoapObject root = (SoapObject) myResponse.getProperty(1);
                        Object documentElement = root.getProperty(0);
                        SoapObject treeDocElement = (SoapObject) documentElement;
                        Object abonent = treeDocElement.getProperty(0);
                        treeAbonent = (SoapObject) abonent;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                Sonuc = (SoapObject) treeAbonent;

            }
            if (mQ.methodName == "GetAllHistoryPaymentsByLS") {
                SoapObject spObjectGonder = new SoapObject(mQ.nameSpace, mQ.methodName);
                spObjectGonder.addProperty("ls", LS);
                Log.i("Aziz",LS);
                SoapObject treePayment = null;
                try {
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(spObjectGonder);
                    HttpTransportSE transport = new HttpTransportSE(mQ.url);
                    transport.debug = true;
                    try {
                        transport.call(mQ.soapAction, envelope);
                        SoapObject myResponse = (SoapObject) envelope.getResponse();
                        SoapObject root = (SoapObject) myResponse.getProperty(1);
                        Object documentElement = root.getProperty(0);
                        treePayment =(SoapObject) documentElement ;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                Sonuc = (SoapObject) treePayment;
            }

            if (mQ.methodName == "GetAddresByLS") {
                SoapObject spObjectGonder = new SoapObject(mQ.nameSpace, mQ.methodName);
                spObjectGonder.addProperty("ls", LS);
                Log.i("Aziz",LS);
                SoapObject  Address = null;
                try {
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(spObjectGonder);
                    HttpTransportSE transport = new HttpTransportSE(mQ.url);
                    transport.debug = true;
                    try {
                        transport.call(mQ.soapAction, envelope);
                       SoapObject myResponse = (SoapObject) envelope.getResponse();
                        SoapObject root = (SoapObject) myResponse.getProperty(1);
                        Object documentElement = root.getProperty(0);
                        SoapObject treeAdress =(SoapObject) documentElement ;
                        Address=(SoapObject)treeAdress.getProperty(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                Sonuc =Address ;
            }

            if (mQ.methodName == "GetAllHistoryMeterReading") {
                SoapObject spObjectGonder = new SoapObject(mQ.nameSpace, mQ.methodName);
                spObjectGonder.addProperty("id", ID);
                SoapObject treeMeterReading = null;
                try {
                    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    envelope.dotNet = true;
                    envelope.setOutputSoapObject(spObjectGonder);
                    HttpTransportSE transport = new HttpTransportSE(mQ.url);
                    transport.debug = true;
                    try {
                        transport.call(mQ.soapAction, envelope);
                        SoapObject myResponse = (SoapObject) envelope.getResponse();
                        SoapObject root = (SoapObject) myResponse.getProperty(1);
                        Object documentElement = root.getProperty(0);
                        treeMeterReading =(SoapObject) documentElement ;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                Sonuc = (SoapObject) treeMeterReading;
            }


            return Sonuc;
        }


        @Override
        protected void onPostExecute(SoapObject result) {
            if (mQ.methodName == "GetTableByLS") {
                int countBalanceInfoColumns = result.getPropertyCount();
                currentBalanceInfo = new String[countBalanceInfoColumns];
                for (int i = 0; i <= countBalanceInfoColumns - 2; i++) {
                    currentBalanceInfo[i] = result.getProperty(i).toString();
                }
               Fio=result.getProperty(countBalanceInfoColumns - 1).toString();
            }
            if (mQ.methodName == "GetAllHistoryPaymentsByLS")
            {
                int countOfPayments=result.getPropertyCount();
                payments=new String[3][countOfPayments];
                for (int i=0;i<=countOfPayments-1;i++)
                {
                    SoapObject treeDocElement = (SoapObject) result.getProperty(i);
                    Object paymentDate = treeDocElement.getProperty(0);
                    Object paymentSum = treeDocElement.getProperty(1);
                    Object paymentType = treeDocElement.getProperty(2);
                    payments[0][i]=paymentDate.toString();
                    payments[1][i]=paymentSum.toString();
                    payments[2][i]=paymentType.toString();
                    Log.i("Aziz",payments[1][i]);
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("ls",LS);
                intent.putExtra("balance", currentBalanceInfo);
                intent.putExtra("address", Address);
                intent.putExtra("fio", Fio);
                Bundle myBundle=new Bundle();
                myBundle.putSerializable("payments",payments);
                myBundle.putSerializable("meterreading", meterReading);
                intent.putExtras(myBundle);
                overridePendingTransition(0, 0);
                startActivity(intent);

                pd.hide();
            }

            if (mQ.methodName == "GetAddresByLS") {
               Address=result.getProperty(0).toString();
                ID=Integer.parseInt(result.getProperty(1).toString());
                Log.i("Address", Address);
                }

            if (mQ.methodName == "GetAllHistoryMeterReading")
            {
                int countOfMeterReading=result.getPropertyCount();
                meterReading=new String[4][countOfMeterReading];
                for (int i=0;i<=countOfMeterReading-1;i++)
                {
                    SoapObject treeDocElement = (SoapObject) result.getProperty(i);
                    Object meterReadingYear = treeDocElement.getProperty(0);
                    Object meterReadingMonth = treeDocElement.getProperty(1);
                    Object meterReadingPrevious = treeDocElement.getProperty(2);
                    Object meterReadingCurrent = treeDocElement.getProperty(3);
                    meterReading[0][i]=meterReadingYear.toString();
                    meterReading[1][i]=meterReadingMonth.toString();
                    meterReading[2][i]=meterReadingPrevious.toString();
                    meterReading[3][i]=meterReadingCurrent.toString();

                }

            }

            }

        }



    public static String md5Crypto(String st) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}

