package com.example.zhoud.iotproject4;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etpassword);
        final Button bLogin = (Button) findViewById(R.id.btnLogin);
        final TextView registerlink = (TextView) findViewById(R.id.tvRegister);

        registerlink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent registIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(registIntent);
            }
        });
        bLogin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                Log.i("adfssdfsd","asdfsdda");
            //    Response.Listener<String> responseListener = new Response.Listener<String>(){
             //       public void onResponse(String response){
                      //  try {
                         //   JSONObject jsonResponse = new JSONObject(response);
                         //   boolean success = jsonResponse.getBoolean("success");
                      //      if (success){
                              //  String name = jsonResponse.getString("name");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                             //   intent.putExtra("name",name);
                            //    intent.putExtra("username",username);

                                LoginActivity.this.startActivity(intent);

                        //    }else{
                        //        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        //        builder.setMessage("Login Failed")
                         //               .setNegativeButton("Retry", null)
                          //              .create()
                           //             .show();


                     //       }
                      //  } catch (JSONException e) {
                   //         e.printStackTrace();
                   //     }
            //        }
                };
           //     LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
          //      RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
           //     queue.add(loginRequest);
      //      }

        });

    }
}
