package com.example.chandra.trabajo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cloud_controller.GcmRegistrationAsyncTask;
import cloud_controller.LoginUser_Gcm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText usrNamee,usrPass;
    Button usrLogin,usrSignUp;
    UserLocalStore store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usrNamee = (EditText) findViewById(R.id.userNameForLogin);
        usrPass = (EditText) findViewById(R.id.userPasswordForLogin);

        usrLogin = (Button) findViewById(R.id.userLoginButton);
        usrSignUp = (Button) findViewById(R.id.newUserSignUp);

        usrLogin.setOnClickListener(this);
        usrSignUp.setOnClickListener(this);

        store = new UserLocalStore(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userLoginButton:
                ArrayList<String> details = new ArrayList<>();
                String usrName = usrNamee.getText().toString();
                String pass = usrPass.getText().toString();
                details.add(usrName);
                details.add(pass);

                User usrr = new User();

                try {
                    usrr = new LoginUser_Gcm().execute(details).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                User user=new User(usrName,pass);

                if(usrr !=null) {
                    if ((usrr.username.equals(user.username)) && (usrr.password.equals(user.password))) {
                        //                    Toast.makeText(MainActivity.this, usrr, Toast.LENGTH_SHORT).show();
                        //                    Intent i = new Intent(getApplicationContext(),Sidebar.class);
                        //                    i.putExtra("username",usrr);
                        //                    startActivity(i);
                        logUserIn(user);
                    } else {
                        showErrorMessage();
                    }
                } else {
                    showErrorMessage();
                }
                break;
            case R.id.newUserSignUp:
                startActivity(new Intent(getApplicationContext(),SignUp.class));
                break;
        }
    }

    private  void  showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        dialogBuilder.setMessage("Incorrect Details");
        dialogBuilder.setPositiveButton("Ok",null);
        dialogBuilder.show();
    }

    private void logUserIn(User returnedUser){
        new GcmRegistrationAsyncTask(this).execute();
        store.storeUserData(returnedUser);
        store.setUserLoggedIn(true);
        startActivity(new Intent(this,Homescreen.class));
    }


}
