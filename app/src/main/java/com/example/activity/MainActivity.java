package com.example.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.R;
import com.example.data.Record;

public class MainActivity extends AppCompatActivity {

    private TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_play).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });
        tvScore = findViewById(R.id.tv_maxscore);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvScore.setText(String.valueOf(new Record(this).maxScore()));
    }
}