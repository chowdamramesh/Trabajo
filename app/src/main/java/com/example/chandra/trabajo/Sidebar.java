package com.example.chandra.trabajo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cloud_controller.CreateNewProjectAsync;
import cloud_controller.GcmAfterNewCreated;

public class Sidebar extends AppCompatActivity implements View.OnClickListener{

    EditText pName,pDesc,pStart,pEnd;
    Button pNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sidebar);

        pName = (EditText) findViewById(R.id.cProjName);
        pDesc = (EditText) findViewById(R.id.cProjDesc);
        pStart = (EditText) findViewById(R.id.cProjStart);



        pStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setDate(v,1);
                return false;
            }
        });


        pEnd = (EditText) findViewById(R.id.cProjEnd);
        pEnd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setDate(v,2);
                return false;
            }
        });

        pNew = (Button) findViewById(R.id.cProjNew);

        pNew.setOnClickListener(this);


        Intent i = getIntent();
//        Toast.makeText(Sidebar.this, i.getStringExtra("username") , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cProjNew:
                String usrName = getIntent().getStringExtra("username");
                String projName = pName.getText().toString();
                String projDecsc = pDesc.getText().toString();
                String projStart = pStart.getText().toString();
                String projEnd = pEnd.getText().toString();
                ArrayList<String> addd = new ArrayList<>();
                addd.add(usrName);
                addd.add(projName);
                addd.add(projDecsc);
                addd.add(projStart);
                addd.add(projEnd);
                String msg = "";
                try {
                    msg = new CreateNewProjectAsync().execute(addd).get().get(0).toString();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                startActivity(new Intent(this,Homescreen.class));
                break;
        }
    }

    int year,month,day;

    public void setDate(View view , int a) {
        if(a == 1){
            showDialog(999);
        } else if(a ==2) {
            showDialog(888);
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        } else if(id == 888) {
            return new DatePickerDialog(this, myDateListenerr, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showStartDate(arg1, arg2+1, arg3);
        }
    };

    private DatePickerDialog.OnDateSetListener myDateListenerr = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            showEndDate(arg1, arg2+1, arg3);
        }
    };

    private void showStartDate(int year, int month, int day) {
        String a = "0";
        String b = "0";
        if(day<10) {
            a += String.valueOf(day);
        } else {
            a = String.valueOf(day);
        }
        if(month <10) {
            b += String.valueOf(month);
        } else {
            b = String.valueOf(month);
        }
        pStart.setText(new StringBuilder().append(year).append("-")
                .append(b).append("-").append(a));
    }

    private void showEndDate(int year, int month, int day) {
        String a = "0";
        String b = "0";
        if(day<10) {
            a += String.valueOf(day);
        } else {
            a = String.valueOf(day);
        }
        if(month <10) {
            b += String.valueOf(month);
        } else {
            b = String.valueOf(month);
        }
        pEnd.setText(new StringBuilder().append(year).append("-")
                .append(b).append("-").append(a));
    }

}
