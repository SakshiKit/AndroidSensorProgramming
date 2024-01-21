package com.example.hw12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private boolean isSensorPresent;
    private float distanceFromPhone;
    private TextView myLabel;
    private float maxRange;

    private static final int DEFAULT_COLOR = Color.GREEN;

    private static final float[] DISTANCE_THRESHOLDS = {0.9f, 0.7f, 0.5f, 0.3f, 0.1f};

    private static final int[] COLORS = {Color.parseColor("#964B00"), Color.RED, Color.parseColor("#FFC0CB"), Color.YELLOW, Color.BLUE};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            maxRange = mSensor.getMaximumRange();
            isSensorPresent = true;
        } else {
            isSensorPresent = false;
        }

        myLabel = (TextView) findViewById(R.id.label);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isSensorPresent) {
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorPresent) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSensorManager = null;
        mSensor = null;
    }

    private float[] calculateDistanceThresholds() {
        float[] dynamicDistanceThresholds = new float[DISTANCE_THRESHOLDS.length];
        for (int i = 0; i < DISTANCE_THRESHOLDS.length; i++) {
            dynamicDistanceThresholds[i] = maxRange * DISTANCE_THRESHOLDS[i];
        }
        return dynamicDistanceThresholds;
    }

    private void updateBackgroundColor(float distance, float[] distanceThresholds) {
        int color = DEFAULT_COLOR;

        for (int i = 0; i < distanceThresholds.length; i++) {
            if (distance <= distanceThresholds[i]) {
                color = COLORS[i];
                break;
            }
        }

        myLabel.setBackgroundColor(color);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            distanceFromPhone = event.values[0];

            // Calculate distance thresholds based on the dynamic max range
            float[] distanceThresholds = calculateDistanceThresholds();

            // Update the background color based on dynamic distance thresholds
            updateBackgroundColor(distanceFromPhone, distanceThresholds);
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}


