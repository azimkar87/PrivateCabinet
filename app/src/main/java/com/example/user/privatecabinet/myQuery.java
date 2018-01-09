package com.example.user.privatecabinet;

/**
 * Created by user on 28.12.2017.
 */

public class myQuery
{
    public String methodName;
    public String soapAction;
    public String url;
    public String nameSpace;
myQuery(){
    methodName="";
    soapAction="";
    url="http://212.112.121.170:3356/PaymentHistory.asmx";
    nameSpace="http://tempuri.org/";
}
    myQuery (String methodName)
    {
        this.methodName=methodName;
        this.soapAction="http://tempuri.org/"+methodName;
        this.url="http://212.112.121.170:3356/PaymentHistory.asmx";
        this.nameSpace="http://tempuri.org/";
    }
}
