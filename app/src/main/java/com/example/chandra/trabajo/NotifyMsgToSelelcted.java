package com.example.chandra.trabajo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cloud_controller.GcmAfterNewCreated;

public class NotifyMsgToSelelcted extends AppCompatActivity {

    EditText msg;
    Button btn;
    TextView to;
    public List<String> in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify_msg_to_selelcted);

        msg = (EditText) findViewById(R.id.msgg);
        btn = (Button) findViewById(R.id.btnNotify);
        to = (TextView) findViewById(R.id.toMem);

        to.setText(getIntent().getStringExtra("toUser").toString());

        in = new ArrayList<>();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                in.add(msg.getText().toString());
                in.add(getIntent().getStringExtra("toUser").toString());
                new GcmAfterNewCreated().execute(in).getStatus();

            }
        });

    }
}
