package com.example.zhoud.iotproject4;




import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etName = (EditText) findViewById(R.id.etname);
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etpassword);
        final Button bRegister = (Button) findViewById(R.id.btnRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();

                final String password = etPassword.getText().toString();

         //       Response.Listener<String> responseListener = new Response.Listener<String>() {
               //     @Override
               //     public void onResponse(String response) {

        //                System.out.println(response);
        //                try {
        //                    JSONObject jsonResponse = new JSONObject(response);
         //                   boolean success = jsonResponse.getBoolean("success");
         //                   if (success) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
         //                   } else {
         //                       AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
          //                      builder.setMessage("Register Failed")
         //                               .setNegativeButton("Retry", null)
         //                               .create()
         //                               .show();
        //                    }
               //         } catch (JSONException e) {
              //              System.out.println("dsfjdsjfldsjfldskfjsl");
          //                  e.printStackTrace();
           //             }
        //           }
          //      };

             //   RegisterRequest registerRequest = new RegisterRequest(name, username, password, responseListener);
        //        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
          //      queue.add(registerRequest);
            }
        });
    }
}