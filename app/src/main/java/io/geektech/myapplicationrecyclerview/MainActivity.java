package io.geektech.myapplicationrecyclerview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private List<MainModel> list;
    private MainAdapter adapter;
    private Button btnAdd;
    private static final int REQUEST_CODE = 2;
    private static final int REQUEST_COD = 3;
    public static final String KEY = "key";
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new MainAdapter(list, this);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ApplicationActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            MainModel model = (MainModel) data.getSerializableExtra(ApplicationActivity.KEY);
            adapter.addData(model);
        }
        if (requestCode == REQUEST_COD && resultCode == RESULT_OK && data != null) {
            MainModel model = (MainModel) data.getSerializableExtra(ApplicationActivity.KEY);
            adapter.addData1(model, pos);
        }
    }

    @Override
    public void onItemClick(int position) {
        pos = position;
        Intent intent = new Intent(MainActivity.this, ApplicationActivity.class);
        intent.putExtra(KEY,adapter.list.get(position));
        startActivityForResult(intent, REQUEST_COD);
    }
}