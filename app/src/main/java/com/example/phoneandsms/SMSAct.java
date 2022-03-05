package com.example.phoneandsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SMSAct extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 10;
    Button btn1;
    EditText input1;
    EditText input2;
    EditText Time;
    final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsact);
        clickRequestPermission();
        btn1 = findViewById(R.id.btn1);
        input1 = findViewById(R.id.input1);
        input2 = findViewById(R.id.input2);
        Time = findViewById(R.id.time);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int finaltime;
                if (input1.getText().toString().isEmpty() || input2.getText().toString().isEmpty()) {
                    toast();
                } else {
                    if (!Time.getText().toString().isEmpty()) {
                        int time = Integer.parseInt(Time.getText().toString().trim());
                        finaltime = time * 1000;
                    } else {
                        finaltime = 0;
                    }
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(input1.getText().toString().trim(),
                                    null, input2.getText().toString().trim(), null, null);
                        }
                    }, finaltime);


                }
            }
        });

    }

    public void toast() {
        Toast.makeText(this, "Chưa nhập số điện thoại hoặc tin nhắn", Toast.LENGTH_SHORT).show();
    }


    private void clickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        String[] permission = {Manifest.permission.SEND_SMS};
        requestPermissions(permission, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Người dùng đã cấp quyền", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(SMSAct.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}