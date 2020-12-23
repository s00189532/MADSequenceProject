package edu.bbialowas.madsequenceproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GameOverScreen extends AppCompatActivity
{
    TextView score, round;
    int scoreValue, roundValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_screen);

        score = findViewById(R.id.tvScoreValue);
        round = findViewById(R.id.tvRoundValue);

        scoreValue = getIntent().getIntExtra("score", -1);
        roundValue = getIntent().getIntExtra("round", -1);

        score.setText(String.valueOf(scoreValue));
        round.setText(String.valueOf(roundValue));
    }

    public void doHighScores(View view)
    {
        Intent highScoreActivity = new Intent(view.getContext(), HighScoresScreen.class);

        highScoreActivity.putExtra("score", scoreValue);

        startActivity(highScoreActivity);
        finish();
    }

    public void doRestart(View view)
    {
        Intent mainActivity = new Intent(view.getContext(), MainActivity.class);
        MainActivity.roundValue = 1;
        MainActivity.scoreValue = 0;
        MainActivity.sequenceCount = 4;
        startActivity(mainActivity);
        finish();
    }
}