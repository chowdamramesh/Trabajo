package com.example.chandra.trabajo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import cloud_controller.Gcm_SignUpAsyncTask;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    EditText usrName,usrUsername,usrPassword,usrEmail,usrPhone;
    Button signUpGcm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usrName = (EditText) findViewById(R.id.usrName);
        usrUsername  = (EditText) findViewById(R.id.usrUsername);
        usrPassword  = (EditText) findViewById(R.id.usrPassword);
        usrEmail = (EditText) findViewById(R.id.usrEmail);
        usrPhone = (EditText) findViewById(R.id.usrPhoneNum);

        signUpGcm = (Button) findViewById(R.id.signUpGcm);



        signUpGcm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpGcm:
                ArrayList<String> detailss = new ArrayList<>();
                String name = usrName.getText().toString();
                String usrname = usrUsername.getText().toString();
                String pass = usrPassword.getText().toString();
                String num = usrPhone.getText().toString();
                String email = usrEmail.getText().toString();




                detailss.add(name);
                detailss.add(usrname);
                detailss.add(pass);
                detailss.add(num);
                detailss.add(email);
//                detailss.add(memSince);

//                User user = new User(name,usrname,pass);

                String usrr = "";
                try {
                    usrr = new Gcm_SignUpAsyncTask().execute(new Pair<Context, ArrayList<String>>(this,detailss)).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(!(usrr.equals(null))) {
//                    resgisterUser(user);
                    startActivity(new Intent(this,MainActivity.class));
                }
                break;
        }
    }

//    private void resgisterUser(User user){
//        startActivity(new Intent(this,MainActivity.class));
//    }
}
