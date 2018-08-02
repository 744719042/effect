package com.example.effect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.effect.adapter.UserListAdapter;

public class ScrollViewActivity extends AppCompatActivity {
    private ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);
        listview = findViewById(R.id.list_view);
        listview.setAdapter(new UserListAdapter(this));
    }
}
