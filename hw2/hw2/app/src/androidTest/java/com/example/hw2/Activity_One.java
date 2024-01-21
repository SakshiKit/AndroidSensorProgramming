package com.example.hw2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Activity_One extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Bundle results = result.getData().getExtras();

                        ((TextView) findViewById(R.id.textViewName)).setText(results.getString("name"));
                        ((TextView) findViewById(R.id.textViewPhone)).setText(results.getString("phone"));
                        ((TextView) findViewById(R.id.textViewEmail)).setText(results.getString("email"));
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(this, Activity_Two.class);
            startForResult.launch(intent);
        });
    }
}
