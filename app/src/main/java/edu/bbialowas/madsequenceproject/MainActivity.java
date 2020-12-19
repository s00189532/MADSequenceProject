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

    int sequenceCount = 4, n = 0;
    private Object mutex = new Object();

    int[] gameSequence = new int[120];
    int arrayIndex = 0;

    Boolean played = false;

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

        round.setText(String.valueOf(1));
        score.setText(String.valueOf(0));
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

    CountDownTimer ct = new CountDownTimer(6000, 1500) {

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
                Log.d("game sequence", String.valueOf(gameSequence[i]));
            // start next activity

            // put the sequence into the next activity
            // stack overglow https://stackoverflow.com/questions/3848148/sending-arrays-with-intent-putextra
            //Intent i = new Intent(A.this, B.class);
            //i.putExtra("numbers", array);
            //startActivity(i);

            // start the next activity
            // int[] arrayB = extras.getIntArray("numbers");
        }
    };

    private void getRandomButton()
    {
        n = getRandom(sequenceCount);
        //Toast.makeText(this, "Number = " + n, Toast.LENGTH_SHORT).show();

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