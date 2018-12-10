package com.example.arpan.ezpay;

import android.app.Notification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class NotificationChannel extends AppCompatActivity {

    NotificationHelper helper;
    Button btnSend;
    EditText edtTitle, edtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_channel);

        helper = new NotificationHelper(this);
        edtTitle = (EditText)findViewById(R.id.edtTitle);
        edtContent = (EditText)findViewById(R.id.edtContent);
        btnSend = (Button)findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // String title = "Tuscaloosa Water and Sewer December Bill Available";//edtTitle.getText().toString();
               // String content = "Amount :$50 Deadline: 12/17/2018";//edtContent.getText().toString();
               // Notification.Builder builder = helper.getTestChannelNotification(title,content);
              //  helper.getManager().notify(new Random().nextInt(),builder.build());
            }
        });
    }
}
