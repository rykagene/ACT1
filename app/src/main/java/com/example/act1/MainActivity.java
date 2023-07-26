package com.example.act1;

import static com.example.act1.App.CHANNEL_ID;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE_CALL = 1;
    private static final int REQ_CODE_SMS = 1;
    EditText etSMS,etNUM;
    Button btnCALL,btnSMS,btnEXIT;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();

        Intent intent = new Intent(context,MyService.class);
        ContextCompat.startForegroundService(context,intent);
    }

    private void initialize(){
        etSMS = findViewById(R.id.etSMS);
        etNUM = findViewById(R.id.etNUM);
        btnCALL = findViewById(R.id.btnCALL);
        btnSMS = findViewById(R.id.btnSMS);
        btnEXIT = findViewById(R.id.btnEXIT);

        btnCALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent call = new Intent(Intent.ACTION_CALL);
                call.setData(Uri.parse("tel:"+etNUM.getText().toString()));
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},REQ_CODE_CALL);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Do you really want to call?")
                            .setMessage("Are you sure?")
                            .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "You cancelled the call", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent call = new Intent(Intent.ACTION_CALL);
                            call.setData(Uri.parse("tel:"+etNUM.getText().toString()));
                            Toast.makeText(context, "The call has been started", Toast.LENGTH_SHORT).show();
                            startActivity(call);
                        }
                    });
                    builder.show();
                }
            }
        });
        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = etNUM.getText().toString();
                String msg = etSMS.getText().toString();
                SmsManager smsman = SmsManager.getDefault();
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},REQ_CODE_SMS);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Do you really want to message?")
                            .setMessage("Are you sure?")
                            .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context, "You cancelled the message", Toast.LENGTH_SHORT).show();
                                }
                            });
                    builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "The message has been sent", Toast.LENGTH_SHORT).show();
                            smsman.sendTextMessage(num,null,msg,null,null);
                        }
                    });
                    builder.show();
                }
            }
        });
        btnEXIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Do you really want to exit?")
                        .setMessage("Are you sure?")
                        .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(context, "You cancelled the function", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Bye", Toast.LENGTH_SHORT).show();
                        MainActivity.this.finish();
                        System.exit(0);
                    }
                });
                builder.show();

            }
        });

    }

}