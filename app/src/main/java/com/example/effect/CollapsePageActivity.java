package com.example.effect;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.example.effect.adapter.RecyclerAdapter;
import com.example.effect.adapter.UserListAdapter;

public class CollapsePageActivity extends AppCompatActivity {
    private View listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapse_page);
        listview = findViewById(R.id.list_view);

        if (listview instanceof ListView) {
            ((ListView)listview).setAdapter(new UserListAdapter(this));
        } else if (listview instanceof RecyclerView) {
            RecyclerView recyclerView = (RecyclerView) listview;
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            RecyclerAdapter recyclerAdapter = new RecyclerAdapter(UserListAdapter.users, this, R.layout.user_item);
            recyclerView.setAdapter(recyclerAdapter);
        }
    }
}
