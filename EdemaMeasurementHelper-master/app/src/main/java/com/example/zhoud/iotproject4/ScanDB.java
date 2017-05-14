package com.example.zhoud.iotproject4;

import android.util.Log;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by zhoud on 2017/5/5.
 */

public class ScanDB implements Runnable {
    String YOUR_ACCESS_KEY, YOUR_SECRET_KEY;
    private BasicAWSCredentials credentialsProvider;
    ArrayList<String> Time;
    ArrayList<String> BT;
    ArrayList<String> Stage;
    @Override
    public void run() {
        ArrayList<ArrayList<String>> R = new ArrayList<>();
        YOUR_ACCESS_KEY = "";
        YOUR_SECRET_KEY = "";
  
        credentialsProvider = new BasicAWSCredentials(YOUR_ACCESS_KEY, YOUR_SECRET_KEY);
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);

        ScanRequest scanRequest = new ScanRequest().withTableName("yunlei");
        ScanResult scanResult = ddbClient.scan(scanRequest);
        //Map<String, AttributeValue> unsortMap = new HashMap<String, AttributeValue>();
        Map<String, Map<String, AttributeValue>> map = new HashMap<>() ;
        for (Map<String, AttributeValue> item : scanResult.getItems()) {
            map.put(item.get("SAVETIME").getS(), item);
            Log.i("itemtiem",item.toString());
            //unsortMap.put(item.
            Log.i(item.keySet().toString(),"dfd");
        }
        Map<String, Map<String, AttributeValue>> treeMap = new TreeMap<>(map);
        Time = new ArrayList<>();
        BT   = new ArrayList<>();
        Stage = new ArrayList<>();
        for (Map.Entry<String, Map<String, AttributeValue>> entry : treeMap.entrySet()) {
            //Time.add( map.get(entry.getKey()).get("DATETIME") ;
            Log.i("item","Key : " + entry.getKey()
                    + " Value : " + entry.getValue());
            Time.add(entry.getValue().get("DATETIME").getS());
            BT.add(entry.getValue().get("BOUNCINGTIME").getS());
            Stage.add(entry.getValue().get("STAGE").getS());
        }
        // Log.i("BT",BT.toString());
        //  Log.i("Time", Time.toString());
        R.add(Time);
        R.add(BT);
        R.add(Stage);

        //  return null;

    }
    public ArrayList<String> getTime(){
        return Time;
    }
    public ArrayList<String> getBT(){
        return BT;
    }
    public ArrayList<String> getStage() { return Stage;}
}
