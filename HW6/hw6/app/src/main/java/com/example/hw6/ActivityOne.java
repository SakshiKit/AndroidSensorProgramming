package com.example.hw6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActivityOne extends AppCompatActivity {
    private FloatingActionButton fab;
    private ArrayList<Contact> contacts = new ArrayList<>();
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(e -> {
            Intent intent = new Intent(ActivityOne.this, ActivityTwo.class);

            // Start the activity for result
            startActivityForResult(intent, 1000);
        });
        ArrayAdapter<Contact> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                contacts
        );
        listView = findViewById(R.id.listView);

        listView.setAdapter(adapter);

        // Set item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click
                Contact selectedContact = contacts.get(position);

                Intent intent = new Intent(ActivityOne.this, ActivityThree.class);
                intent.putExtra("contact_name", selectedContact.name);
                intent.putExtra("contact_phone", selectedContact.phone);
                intent.putExtra("contact_email", selectedContact.email);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {

            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            String email = data.getStringExtra("email");

            contacts.add(new Contact(name, phone, email));

            ArrayAdapter<Contact> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    contacts
            );
            listView.setAdapter(adapter);
        }
    }
}