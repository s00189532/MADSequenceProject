package edu.bbialowas.madsequenceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private final int BLUE = 1;
    private final int RED = 2;
    private final int GREY = 3;
    private final int GREEN = 4;

    TextView round, score;
    Button bRed, bBlue, bWhite, bGreen, fb;

    public static int sequenceCount = 4, scoreValue = 0, roundValue = 1;
    int n = 0;
    private Object mutex = new Object();

    int[] gameSequence = new int[120];
    int arrayIndex = 0;

    Boolean played = false;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        round = findViewById(R.id.tvRoundValue);
        score = findViewById(R.id.tvScoreValue);

        bRed = findViewById(R.id.btnRed);
        bBlue = findViewById(R.id.btnBlue);
        bWhite = findViewById(R.id.btnGrey);
        bGreen = findViewById(R.id.btnGreen);

        round.setText(String.valueOf(roundValue));
        score.setText(String.valueOf(scoreValue));

        view = new View(this);
    }

    public void doPlay(View view)
    {
        if(!played)
        {
            ct.start();
            played = true;
        }
    }

    public void doFinish(View view)
    {

    }

    public void doReset(View view)
    {
        round.setText(String.valueOf(1));
        score.setText(String.valueOf(0));
    }

    CountDownTimer ct = new CountDownTimer((1000 * sequenceCount) + 2000, 1500)
    {
        public void onTick(long millisUntilFinished)
        {
            //mTextField.setText("seconds remaining: " + millisUntilFinished / 1500);
            getRandomButton();
            //here you can have your logic to set text to edittext
        }

        public void onFinish()
        {
            played = false;
            //mTextField.setText("done!");
            // we now have the game sequence

            for (int i = 0; i< arrayIndex; i++)
            {
                Log.d("game sequence", String.valueOf(gameSequence[i]));
            }

            Intent playActivity = new Intent(view.getContext(), PlayScreen.class);

            playActivity.putExtra("sequenceCount", sequenceCount);
            playActivity.putExtra("sequenceArray", gameSequence);

            playActivity.putExtra("score", Integer.valueOf(score.getText().toString()));
            playActivity.putExtra("round", Integer.valueOf(round.getText().toString()));

            startActivity(playActivity);


        }
    };

    private void getRandomButton()
    {
        n = getRandom(4);

        switch (n)
        {
            case 1:
                flashButton(bBlue);
                gameSequence[arrayIndex++] = BLUE;
                break;
            case 2:
                flashButton(bRed);
                gameSequence[arrayIndex++] = RED;
                break;
            case 3:
                flashButton(bWhite);
                gameSequence[arrayIndex++] = GREY;
                break;
            case 4:
                flashButton(bGreen);
                gameSequence[arrayIndex++] = GREEN;
                break;
            default:
                break;
        }   // end switch
    }

    private int getRandom(int maxValue)
    {
        return ((int) ((Math.random() * maxValue) + 1));
    }

    private void flashButton(Button button)
    {
        fb = button;
        Handler handler = new Handler();
        Runnable r = new Runnable()
        {
            public void run()
            {
                fb.setPressed(true);
                fb.invalidate();
                fb.performClick();
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable()
                {
                    public void run()
                    {
                        fb.setPressed(false);
                        fb.invalidate();
                    }
                };
                handler1.postDelayed(r1, 600);
            } // end runnable
        };
        handler.postDelayed(r, 600);
    }
}