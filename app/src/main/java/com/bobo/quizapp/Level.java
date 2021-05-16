package com.bobo.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Level extends AppCompatActivity {

    private ListView levelList;
    private Button btnNext;
    private String url = "https://opentdb.com/api.php?amount=10&category=23&difficulty=easy", userName, category;
    private int categoryIndex, position;
    private boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.level_activity);

        levelList = findViewById(R.id.categoryList);
        btnNext = findViewById(R.id.btnNext);

        Intent levelIntent = getIntent();

        userName = levelIntent.getStringExtra("username");
        category = levelIntent.getStringExtra("category");
        categoryIndex = levelIntent.getIntExtra("position",0);


        ArrayList<String> level = new ArrayList<>();
        level.add("Any Difficulty");
        level.add("Easy");
        level.add("Medium");
        level.add("Hard");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                level
        );

        levelList.setAdapter(categoryAdapter);

        btnNext.setOnClickListener(v -> {
            if(!this.isClicked) {
                Toast.makeText(Level.this, "Select difficulty", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Level.this, Questions.class);
                intent.putExtra("username", userName);
                intent.putExtra("category", category);
                intent.putExtra("level", level.get(this.position).toLowerCase());
                intent.putExtra("position", this.categoryIndex);
                startActivity(intent);
            }
        });

        levelList.setOnItemClickListener((parent, view, position, id) -> {
            this.isClicked = true;
            this.position = position;
        });
    }

}
