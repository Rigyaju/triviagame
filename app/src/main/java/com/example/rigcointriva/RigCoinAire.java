package com.example.rigcointriva;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class RigCoinAire {
    //(String[] questions, String[] correctAnswers, String[] incorrectAnswers1, String[] incorrectAnswers2, String[] incorrectAnswers3)
    public static final int POSSIBLE_ANSWERS = 4; //how many answers A-D
    public static final String ALPHABET = "ABCD";

    public int correctIndex = 0;
    public ArrayList <String> possibleAnswers = new ArrayList();

    public int chooseQuestion(String[] questions) //may be more than a random number gen in the future, needs to log used questions TODO: check for duplicate questions here
    {
        int amountOfQuestions = questions.length;

        int r = (int) (Math.random()*(amountOfQuestions));
        return r;
    }

    public String getQuestion(int quest, String[] questions) //reads and returns the question
    {
        String outputQuestion = questions[quest];
        return outputQuestion;
    }

    public ArrayList <String> getAnswers(int quest, String[] correctAnswers, String[] incorrectAnswers1, String[] incorrectAnswers2, String[] incorrectAnswers3) //returns the answers in the form of an array list
    {
        ArrayList <String> answers = new ArrayList();

        answers.add(correctAnswers[quest]);
        answers.add(incorrectAnswers1[quest]);
        answers.add(incorrectAnswers2[quest]);
        answers.add(incorrectAnswers3[quest]);

        return answers;
    }

    public ArrayList <String> shuffle(ArrayList <String> items)//shuffles the incorrect asnwers
    {
        for(int n = 1; n < items.size(); n++)
        {
            int newIndex = (int) (Math.random()*(POSSIBLE_ANSWERS-1)); //0-3
            String copy = items.get(n);//copy of the original
            String replace = items.get(newIndex);
            items.set(n, replace);
            items.set(newIndex, copy);

            if(newIndex == correctIndex)
                correctIndex = n;
        }
        return(items);
    }
}
