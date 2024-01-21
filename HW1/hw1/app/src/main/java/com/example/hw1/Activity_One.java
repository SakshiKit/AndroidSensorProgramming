package com.example.hw1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Activity_One extends AppCompatActivity {

    private MyViewModel myViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        TextView textView = findViewById(R.id.textView);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, Activity_Two.class);
            startActivity(intent);
        });

        myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
        myViewModel.getData().observe(this, data -> {
            textView.setText(data.toString());
        });
    }

    @Override
    protected void onResume() {
        // this function is called when the user resumes activity one.
        myViewModel.incrementData();
        super.onResume();
    }
}
