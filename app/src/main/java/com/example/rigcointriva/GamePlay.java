package com.example.rigcointriva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GamePlay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        Intent intent = getIntent();
        QUESTIONS = intent.getStringArrayExtra("q");
        CORRECTANSWERS = intent.getStringArrayExtra("ca");
        INCORRECTANSWERS1 = intent.getStringArrayExtra("ic1");
        INCORRECTANSWERS2 = intent.getStringArrayExtra("ic2");
        INCORRECTANSWERS3 = intent.getStringArrayExtra("ic3");

        initiate();

        btnLifelines = (Button) findViewById(R.id.btnLifelines);
        btnLifelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu(view);
            }
        });
    }

    public Button btnLifelines;
    private ArrayList <String> possibleAnswers;
    public static final int[] REWARDS = {100, 5000, 1000, 20000, 50000, 125000, 500000}; //incraments of money earned to finish game
    public String ALPHABET = "ABCD";

    public int gameCycle = 0;
    public int inputtedAnswer;
    public int correctAnswer;
    public int amount_of_answers;
    public int bank = 0;
    public int safeIndex = 4;

    public boolean used50 = false;
    public boolean usedFriend = false;

    private String[] QUESTIONS = {"question"};
    private String[] CORRECTANSWERS = {"answer correct"};
    private String[] INCORRECTANSWERS1 = {"answer incorrect"};
    private String[] INCORRECTANSWERS2 = {"answer incorrect"};
    private String[] INCORRECTANSWERS3 = {"answer incorrect"};

    public void initiate() //Starts the process for asking a question
    {
        RigCoinAire rc = new RigCoinAire();
        int onThisQuestion = rc.chooseQuestion(QUESTIONS); //chooses a random question to ask
        String askThis = rc.getQuestion(onThisQuestion, QUESTIONS);

        TextView txtQuestion = findViewById(R.id.txtQuestion);//displays the question
        txtQuestion.setText(askThis);

        possibleAnswers = rc.getAnswers(onThisQuestion, CORRECTANSWERS, INCORRECTANSWERS1, INCORRECTANSWERS2, INCORRECTANSWERS3);//TODO don't make public ??
        possibleAnswers = rc.shuffle(possibleAnswers);
        printAnswers(possibleAnswers);

        correctAnswer = rc.correctIndex;
        //reset button colour
        bankBackground();
        amount_of_answers = rc.POSSIBLE_ANSWERS;
    }

    public void checkAnswer()
    {
        String response = "";
        if (inputtedAnswer == correctAnswer)
        {
            if(gameCycle < REWARDS.length)//checks if the game is over
            {
                bank = REWARDS[gameCycle];
                gameCycle ++;
                initiate();
            }
            else
            {
                response = "Congratulations!";
                concludeGame(response, "The Jackpot 1,000,000"); //move to final end screen
            }
        }
        else
        {
            response = "Incorrect";
            if(gameCycle >= safeIndex)
                concludeGame(response, "$" + Integer.toString(REWARDS[safeIndex]));
            else
                concludeGame(response, "$0");
            //TextView txtQuestion = findViewById(R.id.txtQuestion);
            //txtQuestion.setText(response);
        }
    }

    public void concludeGame(String conclusionData, String value)//prints the the end game message
    {
        Intent intent = new Intent(this, EndScreen.class); //goes to final end screen
        intent.putExtra("game_result", conclusionData);//Send the final result string to new activity
        intent.putExtra("money_earned", value);
        startActivity(intent);
    }

    public void printAnswers(ArrayList <String> items) // prints the answers on each button
    {
        Button btnA = findViewById(R.id.btnA);
        btnA.setText("A: " + items.get(0));

        Button btnB = findViewById(R.id.btnB);
        btnB.setText("B: " + items.get(1));

        Button btnC = findViewById(R.id.btnC);
        btnC.setText("C: " + items.get(2));

        Button btnD = findViewById(R.id.btnD);
        btnD.setText("D: " + items.get(3));
    }

    public void onBtnA (View view)//TODO These could be one method probably
    {
        TextView txtQuestion = findViewById(R.id.txtQuestion);
        String question = (String) txtQuestion.getText();
        txtQuestion.setText(question + "\n\nResponded with A\nCorrect answer was " + ALPHABET.charAt(correctAnswer));
        inputtedAnswer = 0;
        checkAnswer();
    }
    public void onBtnB (View view)
    {
        TextView txtQuestion = findViewById(R.id.txtQuestion);
        String question = (String) txtQuestion.getText();
        txtQuestion.setText(question + "\n\nResponded with B\nCorrect answer was " + ALPHABET.charAt(correctAnswer));
        inputtedAnswer = 1;
        checkAnswer();
    }
    public void onBtnC (View view)
    {
        TextView txtQuestion = findViewById(R.id.txtQuestion);
        String question = (String) txtQuestion.getText();
        txtQuestion.setText(question + "\n\nResponded with C\nCorrect answer was " + ALPHABET.charAt(correctAnswer));
        inputtedAnswer = 2;
        checkAnswer();
    }
    public void onBtnD (View view)
    {
        TextView txtQuestion = findViewById(R.id.txtQuestion);
        String question = (String) txtQuestion.getText();
        txtQuestion.setText(question + "\n\nResponded with D\nCorrect answer was " + ALPHABET.charAt(correctAnswer));
        inputtedAnswer = 3;
        checkAnswer();
    }

    public void showMenu(View v)
    {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.side_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.item1)
                    onBtn5050();
                if(menuItem.getItemId() == R.id.item2)
                    obBtnFriend();
                if(menuItem.getItemId() == R.id.item4)
                {
                    String walkOutAmount = "$0";
                    if(gameCycle > 0)
                         walkOutAmount = "$" + Integer.toString(REWARDS[gameCycle-1]);
                    concludeGame("Walked Away Early", walkOutAmount);
                }
                return false;
            }
        });
        popup.show();
    }


    public void onBtn5050()
    {
        //String usedColor = "#625EC0";
        Button btn5050 = findViewById(R.id.btnLifelines);
        //btn5050.setBackgroundColor(Color.parseColor(usedColor));
        if(!used50)
        {
           //call 50/50 method and set the new buttons to strikethrough.
            boolean[] values = fiftyFifty();
            String message = "Wrong Answer";
            if(!values[0])
            {
                Button btnA = findViewById(R.id.btnA);
                btnA.setText(message);
            }
            if(!values[1])
            {
                Button btn = findViewById(R.id.btnB);
                btn.setText(message);
            }
            if(!values[2])
            {
                Button btn = findViewById(R.id.btnC);
                btn.setText(message);
            }
            if(!values[3])
            {
                Button btn = findViewById(R.id.btnD);
                btn.setText(message);
            }
            used50 = true;
        }
    }
    public void obBtnFriend()
    {
        if(usedFriend)
            return;

        Intent i = getPackageManager().getLaunchIntentForPackage("com.android.contacts");

        if (i != null)
            startActivity(i);
        else
            Toast.makeText(GamePlay.this, "You have no friends", Toast.LENGTH_LONG).show();
        usedFriend = true;
    }

    public boolean[] fiftyFifty() //performs 50/50 lifeline, returns an array list of who stays and who get's removed
    {
        boolean[] values = new boolean[amount_of_answers];
        for(int n = 0; n < amount_of_answers; n++)//by default all true
            values[n] = true;

        int n = 0;
        while(n < 2)
        {
            int removeIndex = (int) (Math.random()*(amount_of_answers-1));//0-3
            if((values[removeIndex]) && (removeIndex != correctAnswer))
            {
                values[removeIndex] = false;
                n++;
            }
        }
        return values;
    }

    public void bankBackground() //TODO inefficient code to upgrade in the future THE WORST CODE
    {
        String color = "#FF8E04";
        String completed = "#715CBC";
        String gren = "#4CAF50";
        String safe = "#5843A3";

        if(gameCycle == 0)
        {
            TextView txtBank = findViewById(R.id.txtBank1);
            txtBank.setBackgroundColor(Color.parseColor(color));
        }
        if(gameCycle == 1)
        {
            TextView txtBank2 = findViewById(R.id.txtBank2);
            txtBank2.setBackgroundColor(Color.parseColor(color));

            TextView txtBank = findViewById(R.id.txtBank1);
            txtBank.setBackgroundColor(Color.parseColor(completed));
        }
        if(gameCycle == 2)
        {
            TextView txtBank3 = findViewById(R.id.txtBank3);
            txtBank3.setBackgroundColor(Color.parseColor(color));

            TextView txtBank2 = findViewById(R.id.txtBank2);
            txtBank2.setBackgroundColor(Color.parseColor(completed));
        }
        if(gameCycle == 3)
        {
            TextView txtBank4 = findViewById(R.id.txtBank4);
            txtBank4.setBackgroundColor(Color.parseColor(color));

            TextView txtBank3 = findViewById(R.id.txtBank3);
            txtBank3.setBackgroundColor(Color.parseColor(completed));
        }
        if(gameCycle == 4)
        {
            TextView txtBank5 = findViewById(R.id.txtBank5);
            txtBank5.setBackgroundColor(Color.parseColor(gren));//safe spot

            TextView txtBank4 = findViewById(R.id.txtBank4);
            txtBank4.setBackgroundColor(Color.parseColor(completed));
        }
        if(gameCycle == 5)
        {
            TextView txtBank6 = findViewById(R.id.txtBank6);
            txtBank6.setBackgroundColor(Color.parseColor(color));

            TextView txtBank5 = findViewById(R.id.txtBank5);
            txtBank5.setBackgroundColor(Color.parseColor(safe));//mark safe spot
        }
        if(gameCycle == 6)
        {
            TextView txtBank7 = findViewById(R.id.txtBank7);
            txtBank7.setBackgroundColor(Color.parseColor(color));

            TextView txtBank6 = findViewById(R.id.txtBank6);
            txtBank6.setBackgroundColor(Color.parseColor(completed));
        }
        if(gameCycle == 7)
        {
            TextView txtBank8 = findViewById(R.id.txtBank8);
            txtBank8.setBackgroundColor(Color.parseColor(color));

            TextView txtBank7 = findViewById(R.id.txtBank7);
            txtBank7.setBackgroundColor(Color.parseColor(completed));
        }

    }
}