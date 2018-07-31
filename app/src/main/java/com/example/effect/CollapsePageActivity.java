package com.example.effect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.effect.adapter.UserListAdapter;

public class CollapsePageActivity extends AppCompatActivity {
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapse_page);
        listview = findViewById(R.id.list_view);
        listview.setAdapter(new UserListAdapter(this));
    }
}
