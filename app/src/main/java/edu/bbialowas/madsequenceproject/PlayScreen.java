package edu.bbialowas.madsequenceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PlayScreen extends AppCompatActivity implements SensorEventListener
{
    int sequenceCount = 4, n = 0;

    int[] gameSequence = new int[120];

    // experimental values for hi and lo magnitude limits
    private final double DIRECTION_MOVE_FORWARD = 9.0;     // upper mag limit
    private final double DIRECTION_MOVE_BACKWARD = 6.0;      // lower mag limit
    boolean highLimit = false;      // detect high limit

    private SensorManager mSensorManager;
    private Sensor mSensor;

    TextView score;
    int scoreValue, roundValue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        score = findViewById(R.id.tvScoreValue);
    }

    /*
     * When the app is brought to the foreground - using app on screen
     */
    protected void onResume()
    {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
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

        // Can we get a north movement
        // you need to do your own mag calculating
        if ((x > DIRECTION_MOVE_FORWARD) && (highLimit == false))
        {
            highLimit = true;
        }
        if ((x < DIRECTION_MOVE_BACKWARD) && (highLimit == true))
        {
            // we have a tilt to the north
            highLimit = false;
        }

        if ((x > DIRECTION_MOVE_BACKWARD) && (highLimit == false))
        {
            highLimit = true;
        }
        if ((x < DIRECTION_MOVE_FORWARD) && (highLimit == true))
        {
            // we have a tilt to the south
            highLimit = false;
        }

        if ((y > DIRECTION_MOVE_FORWARD) && (highLimit == false))
        {
            highLimit = true;
        }
        if ((y < DIRECTION_MOVE_BACKWARD) && (highLimit == true))
        {
            // we have a tilt to the east
            highLimit = false;
        }

        if ((y > DIRECTION_MOVE_BACKWARD) && (highLimit == false))
        {
            highLimit = true;
        }
        if ((y < DIRECTION_MOVE_FORWARD) && (highLimit == true))
        {
            // we have a tilt to the west
            highLimit = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // not used
    }

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
        Toast.makeText(this,"Number = "+value,Toast.LENGTH_SHORT).show();
        if(n < sequenceCount)
        {
            if(gameSequence[n] == value)
            {
                scoreValue++;
                score.setText(String.valueOf(scoreValue));
                n++;
            }
            else
            {
                //game over
            }
        }
        else if(n == sequenceCount)
        {
            if(gameSequence[n] == value)
            {
                scoreValue++;
                score.setText(String.valueOf(scoreValue));
                roundValue++;

                //go to main activity and increase values and sequence count
            }
            else
            {
                //game over
            }
        }
    }

}
