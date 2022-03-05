package com.example.phoneandsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PhoneAct extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 10;
    Button btn1;
    EditText input1;
    EditText Time;
    final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        clickRequestPermission();
        input1 = findViewById(R.id.input1);
        btn1 = findViewById(R.id.btn1);
        Time = findViewById(R.id.time);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int finaltime;
                if (input1.getText().toString().isEmpty()) {
                    toastNum();
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
                            Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + input1.getText().toString().trim()));
                            startActivity(callIntent);
                        }
                    }, finaltime);
                }
            }
        });
    }

    public void toastNum() {
        Toast.makeText(this, "Chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
    }

    private void clickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        String[] permission = {Manifest.permission.CALL_PHONE};
        requestPermissions(permission, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Người dùng đã cấp quyền", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(PhoneAct.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}