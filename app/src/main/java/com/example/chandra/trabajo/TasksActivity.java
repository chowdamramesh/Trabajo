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
import cloud_controller.CreateTasksAsync;

public class TasksActivity extends AppCompatActivity implements View.OnClickListener{

    EditText tName,tTprojName,tDesc,tStart,tEnd,tAssgnMem;
    Button tNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);


        tName = (EditText) findViewById(R.id.cTaskName);
        tTprojName = (EditText) findViewById(R.id.cTprojName);
        tDesc = (EditText) findViewById(R.id.cTaskDesc);
        tStart = (EditText) findViewById(R.id.cTaskStart);

        tStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setDate(v,1);
                return false;
            }
        });

        tEnd = (EditText) findViewById(R.id.cTaskEnd);
        tEnd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                setDate(v,2);
                return false;
            }
        });

        tAssgnMem = (EditText) findViewById(R.id.cTaskAssgnPh);

        tNew = (Button) findViewById(R.id.cTaskNew);

        tNew.setOnClickListener(this);

        Intent i = getIntent();
//        Toast.makeText(TasksActivity.this, i.getStringExtra("username") , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cTaskNew:
                String usrName = getIntent().getStringExtra("username");
                String tProjName = tTprojName.getText().toString();
                String taskName = tName.getText().toString();
                String taskDecsc = tDesc.getText().toString();
                String taskStart = tStart.getText().toString();
                String taskEnd = tEnd.getText().toString();
                String taskAssgn = tAssgnMem.getText().toString();

                ArrayList<String> addd = new ArrayList<>();
                addd.add(usrName);
                addd.add(tProjName);
                addd.add(taskName);
                addd.add(taskDecsc);
                addd.add(taskStart);
                addd.add(taskEnd);
                addd.add(taskAssgn);
                String msg = "";
                try {
                    msg = new CreateTasksAsync().execute(addd).get().get(0).toString();
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
        tStart.setText(new StringBuilder().append(year).append("-")
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
        tEnd.setText(new StringBuilder().append(year).append("-")
                .append(b).append("-").append(a));
    }
}
