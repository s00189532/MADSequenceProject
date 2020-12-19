package edu.bbialowas.madsequenceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    TextView round, score;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        round = findViewById(R.id.tvRoundValue);
        score = findViewById(R.id.tvScoreValue);
    }

    public void doPlay(View view)
    {

    }

    public void doFinish(View view)
    {

    }

    public void doReset(View view)
    {

    }
}