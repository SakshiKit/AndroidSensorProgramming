package com.example.hw7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    WebView wv;
    private ArrayList<String> bookmarks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSubmit = (Button) findViewById(R.id.buttonSubmit);
        Button btnBookmark = (Button) findViewById(R.id.buttonBookmark);

        btnSubmit.setOnCreateContextMenuListener(this);

        EditText editTextUrl = findViewById(R.id.editTextUrl);

        bookmarks = new ArrayList<>();

        wv = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = wv.getSettings();
        webSettings.setBuiltInZoomControls(true);
        wv.loadUrl(
                " https://www.futurity.org/wp/wp-content/uploads/2019/02/viceroy-butterfly_1600.jpg ");

        btnSubmit.setOnClickListener(e -> {
            String url = editTextUrl.getText().toString();
            wv.loadUrl(url);

        });
        btnBookmark.setOnClickListener(e -> {
            String url = editTextUrl.getText().toString();

            bookmarks.add(url);
            invalidateOptionsMenu();

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return menuChoice(item);
    }

    private boolean menuChoice(MenuItem item) {
        wv.loadUrl(bookmarks.get(item.getItemId()));

        return false;
    }
    private void createMenu(Menu menu) {
        for (int i = 0; i < bookmarks.size(); i++) {
            MenuItem item = menu.add(0, i, i, bookmarks.get(i));

        }

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        createMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        createMenu(menu);
        return true;
    }

}