package edu.bbialowas.madsequenceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView round, score;
    Button bRed, bBlue, bWhite, bGreen, fb;
    int sequenceCount = 4, n = 0;
    private Object mutex = new Object();

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
    }

    public void doPlay(View view)
    {
        ct.start();
    }

    public void doFinish(View view)
    {

    }

    public void doReset(View view)
    {

    }

    CountDownTimer ct = new CountDownTimer(6000, 1500) {

        public void onTick(long millisUntilFinished)
        {
            //mTextField.setText("seconds remaining: " + millisUntilFinished / 1500);
            oneButton();
            //here you can have your logic to set text to edittext
        }

        public void onFinish()
        {
            //mTextField.setText("done!");
        }
    };

    private void oneButton()
    {
        n = getRandom(sequenceCount);

        //Toast.makeText(this, "Number = " + n, Toast.LENGTH_SHORT).show();

        switch (n)
        {
            case 1:
                flashButton(bBlue);
                break;
            case 2:
                flashButton(bRed);
                break;
            case 3:
                flashButton(bWhite);
                break;
            case 4:
                flashButton(bGreen);
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

    public void doTest(View view) {
        for (int i = 0; i < sequenceCount; i++) {
            int x = getRandom(sequenceCount);

            //Toast.makeText(this, "Number = " + String.valueOf(x), Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, "Number = " + x, Toast.LENGTH_SHORT).show();

            if (x == 1)
                flashButton(bBlue);
            else if (x == 2)
                flashButton(bRed);
            else if (x == 3)
                flashButton(bWhite);
            else if (x == 4)
                flashButton(bGreen);
        }
    }
}