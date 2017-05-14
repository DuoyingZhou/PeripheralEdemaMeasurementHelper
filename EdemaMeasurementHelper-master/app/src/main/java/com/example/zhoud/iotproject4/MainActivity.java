package com.example.zhoud.iotproject4;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton buttonChoose;
    private ImageButton buttonUpload;
    private ImageButton buttonHistory;

 //   private TextView bouncingtimeview;
  //  private TextView textViewResponse;

    private static final int MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE=123;
    private String selectedPath;

   //////////////////////////////////////////////////////////
    private static final int REQUEST_VIDEO_CAPTURE = 300;
   static public ArrayList<String> BT, Time, Stage;

static  String mess;



  //  private GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonChoose = (ImageButton) findViewById(R.id.buttonChoose);
        buttonUpload = (ImageButton) findViewById(R.id.buttonUpload);
        buttonHistory = (ImageButton) findViewById(R.id.history);
  //      textView = (TextView) findViewById(R.id.textView);

  //      displayRecordedVideo = (VideoView)findViewById(R.id.videoView);

    //    textViewResponse = (TextView) findViewById(R.id.textViewResponse);
    //    bouncingtimeview = (TextView) findViewById(R.id.bouncingtime);
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        buttonHistory.setOnClickListener(this);
   //     graphView = (GraphView) findViewById(R.id.graphview);

   //     series = new LineGraphSeries<DataPoint>();

    }

    private void chooseVideo() {

    //    Intent intent = new Intent();
      //  intent.setType("video/*");
       // intent.setAction(Intent.ACTION_GET_CONTENT);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        Intent videoCaptureIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(videoCaptureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(videoCaptureIntent, REQUEST_VIDEO_CAPTURE);
        }

      //  startActivityForResult(Intent.createChooser(intent, "Select a Video "), SELECT_VIDEO);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if  (requestCode == REQUEST_VIDEO_CAPTURE){
           // if (requestCode == SELECT_VIDEO) {

                Uri selectedImageUri = data.getData();

               // displayRecordedVideo.setVideoURI(selectedImageUri);
                //displayRecordedVideo.start();

                selectedPath = getPath(selectedImageUri);
                Log.i("asdfsdfasdfasdfdasf","aaaaaaaaaaaaaa");
              //  textView.setText(selectedPath);
            }
        }
    }

    public String getPath(Uri uri) {

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
            String path=cursor.getString(idx);
            cursor.close();
            return path;
        }

     //   return path;

    }


    private void uploadVideo() {
        class UploadVideo extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;
         //   Handler handle = new Handler() {
          //      public void handleMessage(Message msg) {
          //          super.handleMessage(msg);
           //        uploading.incrementProgressBy(2); // Incremented By Value 2
           //     }
          //  };

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

               uploading = ProgressDialog.show(MainActivity.this, "Uploading File", "Please wait...", false, false);
        //  ProgressDialog.show()
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
              //  s = "<?adfdsfsafs>";
          //      textViewResponse.setText(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>"));

         //       Log.i("textviewresponese", String.valueOf(Html.fromHtml("<b>Uploaded at <a href='" + s + "'>" + s + "</a></b>")));
         //       textViewResponse.setMovementMethod(LinkMovementMethod.getInstance());
         //       bouncingtimeview.setText("bouncingtime: "+BT.get(BT.size()-1));

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                MainActivity.this.startActivity(intent);


            }

            @Override
            protected String doInBackground(Void... params) {

                Upload u = new Upload();
                String msg = u.uploadVideo(selectedPath);
                Log.i("SDFDSFFS", msg);

                try {
                    TimeUnit.SECONDS.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ScanDB S2 = new ScanDB();
                Thread T = new Thread(S2);
                T.start();
                try{
                    T.join();
                }
                catch (java.lang.InterruptedException e){
                    Log.i("","");
                }
                BT = S2.getBT();
                Stage = S2.getStage();
                Time = S2.getTime();
                Log.i("BT",BT.toString());
                Log.i("BTSIZE",String.valueOf(BT.size()));
                Log.i("Stage", Stage.toString());
                Log.i("Time", Time.toString());
mess = msg;
                return msg;
            }
        }
        UploadVideo uv = new UploadVideo();
        uv.execute();
    }


    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            Log.i("asdfsdfasdfasdfdasf","aaaaaaaaaaaaaa");
            chooseVideo();


        }
        if (v == buttonUpload) {
            uploadVideo();
            Log.i("uploadvideovidovoo","afdsfsdsdf");

        }
        if(v == buttonHistory){
            Intent intent2 = new Intent(MainActivity.this, HistoryActivity.class);
            MainActivity.this.startActivity(intent2);
        }
    }
}
