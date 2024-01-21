package com.example.hw6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityThree extends AppCompatActivity {
    private TextView textViewName;
    private TextView textViewPhone;
    private TextView textViewEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        textViewName = findViewById(R.id.textViewName);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewEmail = findViewById(R.id.textViewEmail);

        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("contact_name");
            String phone = intent.getStringExtra("contact_phone");
            String email = intent.getStringExtra("contact_email");

            // Use the received data as needed
            TextView textViewName = findViewById(R.id.textViewName);
            TextView textViewPhone = findViewById(R.id.textViewPhone);
            TextView textViewEmail = findViewById(R.id.textViewEmail);

            textViewName.setText("Name: " + name);
            textViewPhone.setText("Phone: " + phone);
            textViewEmail.setText("Email: " + email);
        }
    }
}