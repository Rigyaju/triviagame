package com.example.rigcointriva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //ImageView homeLogo = findViewById(R.id.homeLogo);
    }
    public void onStartBtn (View view)
    {
        gameQuestions gq = new gameQuestions();
        String[] questions = gq.questions;
        String[] correctAnswers = gq.correctAnswers;
        String[] incorrectAnswers1 = gq.incorrectAnswer1;
        String[] incorrectAnswers2 = gq.incorrectAnswer2;
        String[] incorrectAnswers3 = gq.incorrectAnswer3;
        openGameActivity(questions, correctAnswers, incorrectAnswers1, incorrectAnswers2, incorrectAnswers3);
    }
    /*public void onMode2 (View view) //TODO Add back Mutltiple Modes but with user creation
    {
        gameQuestions gq = new gameQuestions();
        String[] questions = gq.mode2Q;
        String[] correctAnswers = gq.mode2CA;
        String[] incorrectAnswers1 = gq.mode2IA1;
        String[] incorrectAnswers2 = gq.mode2IA2;
        String[] incorrectAnswers3 = gq.mode2IA3;
        openGameActivity(questions, correctAnswers, incorrectAnswers1, incorrectAnswers2, incorrectAnswers3);
    }*/

    public void onModify (View view)
    {
        Intent openEditor = new Intent(this, EditQuestions.class);
                //startActivity(openEditor);
        Toast.makeText(MainActivity.this, "Coming Soon", Toast.LENGTH_LONG).show();
    }

    public void openGameActivity(String[] questions, String[] correctAnswers, String[] incorrectAnswers1, String[] incorrectAnswers2, String[] incorrectAnswers3)
    {
        //Pass the question through the intent
        Intent in = new Intent(this, GamePlay.class);
        in.putExtra("q", questions);//Send the list of questions to the game activity
        in.putExtra("ca", correctAnswers);
        in.putExtra("ic1", incorrectAnswers1);
        in.putExtra("ic2", incorrectAnswers2);
        in.putExtra("ic3", incorrectAnswers3);
        startActivity(in);
    }
}