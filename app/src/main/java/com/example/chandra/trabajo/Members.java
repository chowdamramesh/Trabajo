package com.example.chandra.trabajo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import cloud_controller.AddMembersAsync;

public class Members extends AppCompatActivity {

    EditText phnNUm;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        phnNUm = (EditText) findViewById(R.id.memPhnNum);
        addBtn = (Button) findViewById(R.id.addMembtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> inputList = new ArrayList<String>();
                inputList.add(getIntent().getStringExtra("username").toString());
                inputList.add(phnNUm.getText().toString());

                try {
                    List<String> result =new AddMembersAsync().execute(inputList).get();
                    Toast.makeText(Members.this,result.get(1) + " added To playArea" , Toast.LENGTH_SHORT).show();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(getApplicationContext(),Homescreen.class));
            }
        });
    }
}
