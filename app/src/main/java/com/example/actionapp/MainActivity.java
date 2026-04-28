package com.example.actionapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    EditText etPhone, etMessage, etSearch, etDial;
    Button btnSend, btnView, btnDial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link Views
        etPhone   = findViewById(R.id.etPhone);
        etMessage = findViewById(R.id.etMessage);
        etSearch  = findViewById(R.id.etSearch);
        etDial    = findViewById(R.id.etDial);
        btnSend   = findViewById(R.id.btnSend);
        btnView   = findViewById(R.id.btnView);
        btnDial   = findViewById(R.id.btnDial);

        // ─────────────────────────────────────────
        // ACTION 1: SEND SMS
        // ─────────────────────────────────────────
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone   = etPhone.getText().toString().trim();
                String message = etMessage.getText().toString().trim();

                if (phone.isEmpty() || message.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Enter phone number and message", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.SEND_SMS}, 1);

                } else {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phone, null, message, null, null);
                    Toast.makeText(MainActivity.this, "SMS Sent!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ─────────────────────────────────────────
        // ACTION 2: VIEW (Web Search)
        // ─────────────────────────────────────────
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = etSearch.getText().toString().trim();

                if (query.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Enter a search term", Toast.LENGTH_SHORT).show();
                    return;
                }

                Uri searchUri = Uri.parse("https://www.google.com/search?q=" +
                        Uri.encode(query));
                Intent intent = new Intent(Intent.ACTION_VIEW, searchUri);
                startActivity(intent);
            }
        });

        // ─────────────────────────────────────────
        // ACTION 3: DIALUP
        // ─────────────────────────────────────────
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = etDial.getText().toString().trim();

                if (number.isEmpty()) {
                    Toast.makeText(MainActivity.this,
                            "Enter a number to dial", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 2);

                } else {
                    Uri callUri = Uri.parse("tel:" + number);
                    Intent intent = new Intent(Intent.ACTION_CALL, callUri);
                    startActivity(intent);
                }
            }
        });
    }
}