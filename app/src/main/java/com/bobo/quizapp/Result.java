package com.bobo.quizapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Result extends AppCompatActivity {
    private TextView result, name;
    private Button btnRestart;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        result = findViewById(R.id.score);
        name = findViewById(R.id.name);
        btnRestart = findViewById(R.id.btnRestart);

        Intent intent = getIntent();

        name.setText(intent.getStringExtra("name"));
        result.setText("Your score is " + intent.getIntExtra("score",0) + " out of 10");

        btnRestart.setOnClickListener(v -> {
            Intent restartIntent = new Intent(Result.this,Category.class);
            restartIntent.putExtra("username",intent.getStringExtra("name"));
            startActivity(restartIntent);
        });

    }
}
