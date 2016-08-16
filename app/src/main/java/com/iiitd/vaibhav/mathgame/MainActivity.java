package com.iiitd.vaibhav.mathgame;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int win_count = 0, current_question = -1;
    boolean new_question = false;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("win_count", win_count);
        savedInstanceState.putInt("current_question", current_question);
        savedInstanceState.putBoolean("new_question", new_question);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        win_count = savedInstanceState.getInt("win_count");
        current_question = savedInstanceState.getInt("current_question");
        new_question = savedInstanceState.getBoolean("new_question");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Round Reset", Toast.LENGTH_SHORT).show();
                current_question = -1;
                win_count = 0;
                new_question = false;
                EditText controller_txt_score_display = (EditText)findViewById(R.id.txt_score_display);
                String score = "" + win_count + " Correct Answers !";
                controller_txt_score_display.setText(score, TextView.BufferType.EDITABLE);
                EditText controller_txt_question_display = (EditText)findViewById(R.id.txt_question_display);
                controller_txt_question_display.setText("Welcome to PrimeFun. Test your math ability ! Click \'New Question\' to start", TextView.BufferType.EDITABLE);
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        //Placing the EditText at the correct location on the screen
        EditText controller_txt_question_display = (EditText)findViewById(R.id.txt_question_display); //getting the context of the EditText box
        ViewGroup.LayoutParams layoutParams = controller_txt_question_display.getLayoutParams(); //getting the layout parameters
        layoutParams.height = height/4 + (height/2 - height/4)/2;
        controller_txt_question_display.setLayoutParams(layoutParams); //setting the layout parameters
        controller_txt_question_display.setText("Welcome to PrimeFun. Test your math ability !. Click \'Next Question\' to start", TextView.BufferType.EDITABLE);
        EditText controller_txt_score_display = (EditText)findViewById(R.id.txt_score_display);
        String score = "" + win_count + " Correct Answers !";
        controller_txt_score_display.setText(score, TextView.BufferType.EDITABLE);
    }

    public void onBtnClickListner(View v){

        int btn_id = v.getId();
        switch(btn_id){
            case R.id.btn_true : if(current_question == -1)//the question has still not been displayed
                return;
                if(this.isPrime(current_question)) {
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                    if(new_question) { //increment counter if this is only a new question
                        win_count++;
                    }
                    EditText controller_txt_score_display = (EditText)findViewById(R.id.txt_score_display);
                    String score = "" + win_count + " Correct Answers !";
                    controller_txt_score_display.setText(score, TextView.BufferType.EDITABLE);
                }
                else
                    Toast.makeText(getApplicationContext(), "In Correct", Toast.LENGTH_SHORT).show();
                if(new_question) //this question is used
                    new_question = false;
                break;
            case R.id.btn_false : if(current_question == -1) //the question has still not been displayed
                return;
                if(!this.isPrime(current_question)) {
                    Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT).show();
                    if(new_question) { //increment counter if this is only a new question
                        win_count++;
                    }
                    EditText controller_txt_score_display = (EditText)findViewById(R.id.txt_score_display);
                    String score = "" + win_count + " Correct Answers !";
                    controller_txt_score_display.setText(score, TextView.BufferType.EDITABLE);
                }
                else
                    Toast.makeText(getApplicationContext(), "In Correct", Toast.LENGTH_SHORT).show();
                if(new_question)//this question is used
                    new_question = false;
                break;
            case R.id.btn_next_question : Random r = new Random();
                current_question = r.nextInt((1000-1) + 1) + 1; //generating a random number between 1 and 1000
                new_question = true; //to know this is the new question so that the win counter can be evaluated
                EditText controller_txt_question_display = (EditText)findViewById(R.id.txt_question_display);
                String question = "Is " + current_question + " a prime number ?";
                controller_txt_question_display.setText(question, TextView.BufferType.EDITABLE);
                break;
        }
    }

    public boolean isPrime(int current_question){
        int i, limit = (int)Math.sqrt((current_question*1.0));
        for(i = 2 ; i < limit ; i++){
            if(current_question % i == 0)
                return false;
        }
        return true;
    }
}
