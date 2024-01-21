package com.example.hw2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Activity_Two extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        EditText editTextName = findViewById(R.id.editTextTextPersonName);
        EditText editTextPhone = findViewById(R.id.editTextPhone);
        EditText editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(view -> {
            CharSequence userName = editTextName.getText();
            CharSequence userPhone = editTextPhone.getText();
            CharSequence userEmail = editTextEmail.getText();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", userName.toString());
            resultIntent.putExtra("phone", userPhone.toString());
            resultIntent.putExtra("email", userEmail.toString());

            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }
}
