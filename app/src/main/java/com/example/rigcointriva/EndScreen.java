package com.example.rigcointriva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndScreen extends AppCompatActivity {
    private Button btnRestart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        Intent intent = getIntent();
        String printResult = intent.getStringExtra("game_result");

        String money = intent.getStringExtra("money_earned");

        TextView txtConclusion = findViewById(R.id.txtConclusion);
        txtConclusion.setText(printResult);

        TextView txtMoney = findViewById(R.id.txtMoney);
        txtMoney.setText("You earned " + money);

        btnRestart = (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restart();
            }
        });
    }
    public void restart()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}