package com.example.zhoud.iotproject4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    private GraphView graphView;
    private LineGraphSeries<DataPoint> series;
    private ArrayList<String> BT, Time, Stage;
    private  Map<Integer, String> map, map2;
    private ImageButton fullscreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     requestWindowFeature(Window.FEATURE_NO_TITLE);
   //     getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
   //             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_history);

        graphView = (GraphView) findViewById(R.id.graphview);
        fullscreen = (ImageButton) findViewById(R.id.fullscreen);
        series = new LineGraphSeries<DataPoint>();
        ScanDB S3 = new ScanDB();
        Thread T = new Thread(S3);
        T.start();
        try{
            T.join();
        }
        catch (java.lang.InterruptedException e){
            Log.i("","");
        }
        BT = S3.getBT();
        Stage = S3.getStage();
        Time = S3.getTime();
        Log.i("BT",BT.toString());
        Log.i("Stage", Stage.toString());
        Log.i("BTSIZE",String.valueOf(BT.size()));
        Log.i("Time", Time.toString());
        java.util.Date d;
        map = new HashMap<>();
        map2 = new HashMap<>();
               for(int i=0; i<BT.size();i++){
                  /*  String date = Time.get(i).split(" ")[0];
                    Log.i("datadatedatedate", date);
                    int year = Integer.valueOf(date.split("-")[0]);
                    Log.i("datadatedatedate", String.valueOf(year));
                    int month = Integer.valueOf(date.split("-")[1]);
                    int day = Integer.valueOf(date.split("-")[2]);
                    String time = Time.get(i).split(" ")[1];
                    int hour = Integer.valueOf(time.split(":")[0]);
                    int minute = Integer.valueOf(time.split(":")[1]);
                    int second = Integer.valueOf(time.split(":")[2]);
                   d = new Date(year,month,day, hour, minute, second);*/
                    series.appendData(new DataPoint(i, Double.valueOf(BT.get(i))), true, BT.size());
                   map.put(i,Time.get(i));
                   map2.put(i,Stage.get(i));
                }

       graphView.getViewport().setScalable(true);
       // graphView.getViewport().setXAxisBoundsManual(true);
//graphView.getViewport().setMinX(1);
        //graphView.getViewport().setScrollable(true);
      //  graphView.getViewport().setScrollableY(true);
      graphView.getViewport().setScalableY(true);
        graphView.setTitle("Rebound Time");

      //  graphView.
                graphView.addSeries(series);

        graphView.setTitleTextSize(70);
        series.setColor(R.color.colorPrimary);
        series.setDrawDataPoints(true);
        //series.setDataPointsRadius(15);
        series.setOnDataPointTapListener(new OnDataPointTapListener(){

            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {

                       int a = (int)dataPoint.getX();
                String date = map.get(a);
                String stage = map2.get(a);
                Toast.makeText(HistoryActivity.this, date+":"+" Rebound time: "+String.valueOf(dataPoint.getY())+"s Stage: "+stage, Toast.LENGTH_LONG).show();
            }
        });
        fullscreen.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View view) {

              Intent intent = new Intent(HistoryActivity.this, History2Activity.class);
               HistoryActivity.this.startActivity(intent);
           }
        });

    }
}
