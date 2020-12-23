package edu.bbialowas.madsequenceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlayScreen extends AppCompatActivity implements SensorEventListener
{
    int sequenceCount = 4, n = 0;
    int[] gameSequence = new int[120];

    // experimental values for hi and lo magnitude limits
    private final double PValue = 5;
    private final double NValue = -5;
    boolean highLimit = false;      // detect high limit

    private SensorManager mSensorManager;
    private Sensor mSensor;

    TextView score;
    int scoreValue, roundValue;

    Button btnNorth, btnSouth, btnEast, btnWest;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        score = findViewById(R.id.tvScoreValue);

        btnNorth = findViewById(R.id.btnNorth);
        btnSouth = findViewById(R.id.btnSouth);
        btnEast = findViewById(R.id.btnEast);
        btnWest = findViewById(R.id.btnWest);

        scoreValue = getIntent().getIntExtra("score", -1);
        roundValue = getIntent().getIntExtra("round", -1);

        sequenceCount = getIntent().getIntExtra("sequenceCount", -1);
        gameSequence = getIntent().getIntArrayExtra("sequenceArray");

        view = new View(this);
    }

    /*
     * When the app is brought to the foreground - using app on screen
     */
    protected void onResume()
    {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /*
     * App running but not on screen - in the background
     */
    protected void onPause()
    {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if(z >= PValue && !highLimit)
        {
            highLimit = true;
            btnNorth.setPressed(true);
            doNorth(view);
        }
        else if( z < PValue)
        {
            btnNorth.setPressed(false);
        }

        if(z <= NValue && !highLimit)
        {
            highLimit = true;
            btnSouth.setPressed(true);
            doSouth(view);
        }
        else if(z > NValue)
        {
            btnSouth.setPressed(false);
        }

        if(y >= PValue && !highLimit)
        {
            highLimit = true;
            btnEast.setPressed(true);
            doEast(view);
        }
        else if(y < PValue)
        {
            btnEast.setPressed(false);
        }

        if(y <= NValue && !highLimit)
        {
            highLimit = true;
            btnWest.setPressed(true);
            doWest(view);
        }
        else if(y > NValue)
        {
            btnWest.setPressed(false);
        }

        if(z < PValue && z > NValue && y < PValue && y > NValue)
        {
            highLimit = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    public void doSouth(View view)
    {
        //value is 1
        HandleInput(1);
    }

    public void doNorth(View view)
    {
        //value is 2
        HandleInput(2);
    }

    public void doEast(View view)
    {
        //value is 3
        HandleInput(3);
    }

    public void doWest(View view)
    {
        //value is 4
        HandleInput(4);
    }

    public void HandleInput(int value)
    {
        //test to see if correct value is outputted

        if(n+1 < sequenceCount)
        {
            if(gameSequence[n] == value)
            {
                scoreValue++;
                score.setText(String.valueOf(scoreValue));
                n++;
            }
            else
            {
                Intent gameOverActivity = new Intent(view.getContext(), GameOverScreen.class);

                gameOverActivity.putExtra("score", scoreValue);
                gameOverActivity.putExtra("round", roundValue);

                startActivity(gameOverActivity);
            }
        }
        else if(n+1 >= sequenceCount)
        {
            if(gameSequence[n] == value)
            {
                scoreValue++;
                score.setText(String.valueOf(scoreValue));
                roundValue++;

                Intent mainActivity = new Intent(view.getContext(), MainActivity.class);

                MainActivity.scoreValue = scoreValue;
                MainActivity.roundValue = roundValue;
                MainActivity.sequenceCount = sequenceCount + 2;

                startActivity(mainActivity);
                finish();
            }
            else
            {
                Intent gameOverActivity = new Intent(view.getContext(), GameOverScreen.class);

                gameOverActivity.putExtra("score", scoreValue);
                gameOverActivity.putExtra("round", roundValue);

                startActivity(gameOverActivity);
            }
        }
    }

}
