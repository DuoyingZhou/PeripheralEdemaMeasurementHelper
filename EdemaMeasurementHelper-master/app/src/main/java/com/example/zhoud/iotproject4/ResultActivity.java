package com.example.zhoud.iotproject4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView bouncingtimeview2;
    private TextView stage2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

      //  textViewResponse = (TextView) findViewById(R.id.textViewResponse);
        bouncingtimeview2 = (TextView) findViewById(R.id.bouncingtimeview2);
           stage2 = (TextView) findViewById(R.id.stagetext) ;
      //  textViewResponse.setText(Html.fromHtml("<b>Uploaded at <a href='" + MainActivity.mess + "'>" + MainActivity.mess + "</a></b>"));

        Log.i("textviewresponese", String.valueOf(Html.fromHtml("<b>Uploaded at <a href='" + MainActivity.mess + "'>" + MainActivity.mess + "</a></b>")));
     //   textViewResponse.setMovementMethod(LinkMovementMethod.getInstance());
        bouncingtimeview2.setText(MainActivity.BT.get(MainActivity.BT.size()-1)+" s");
        stage2.setText(MainActivity.Stage.get(MainActivity.Stage.size()-1));

    }
}
