package com.bobo.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Category extends AppCompatActivity {

    private ListView categoryList;
    private Button btnNext;
    private String userName;
    private int categoryIndex = 0, position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_activity);

        categoryList = findViewById(R.id.categoryList);
        btnNext = findViewById(R.id.btnNext);

        Intent extra = getIntent();

        System.out.println(extra.hasExtra("username"));

        if(extra.hasExtra("username")) {
            userName = extra.getStringExtra("username");
        }

        ArrayList<String> category = new ArrayList<>();

        category.add("Any Category");
        category.add("General Knowledge");
        category.add("Entertainment: Books");
        category.add("Entertainment: Film");
        category.add("Entertainment: Music");
        category.add("Entertainment: Musical Theatres");
        category.add("Entertainment: Television");
        category.add("Entertainment: Video Games");
        category.add("Entertainment: Board Games");
        category.add("Science & Nature");
        category.add("Science: Computers");
        category.add("Science: Mathematics");
        category.add("Mythology");
        category.add("Sports");
        category.add("Geography");
        category.add("History");
        category.add("Politics");
        category.add("Art");
        category.add("Celebrities");
        category.add("Animals");
        category.add("Vehicles");
        category.add("Entertainment: Comics");
        category.add("Science: Gadgets");
        category.add("Entertainment: Japanese Anime & Manga");
        category.add("Entertainment: Cartoon & Animations");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                category
        );

        categoryList.setAdapter(categoryAdapter);

        btnNext.setOnClickListener(v -> {
            if (this.categoryIndex == 0) {
                Toast.makeText(Category.this, "Select category", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(Category.this, Level.class);
                intent.putExtra("category", category.get(this.position).toLowerCase());
                intent.putExtra("username", userName);
                intent.putExtra("position", this.categoryIndex);
                startActivity(intent);
            }
        });

        categoryList.setOnItemClickListener((parent, view, position, id) -> {
            this.categoryIndex = position+8;
            this.position = position;
        });
    }

}
