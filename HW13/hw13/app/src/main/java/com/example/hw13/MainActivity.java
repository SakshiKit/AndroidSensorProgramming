package com.example.hw13;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private TextView textView;
    private float x, y, z, last_x, last_y, last_z;
    private boolean isFirstValue;
    private float shakeThreshold = 3f;
    private int[] songs_array = {R.raw.ay_corazon, R.raw.bankai_byakuya, R.raw.omae_wa_mou_shindeiru};
    private MediaPlayer[] mMediaPlayer;
    private int current_media = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMediaPlayer = new MediaPlayer[songs_array.length];
        textView = findViewById(R.id.textView);

        for (int i = 0; i < mMediaPlayer.length; i++) {
            mMediaPlayer[i] = MediaPlayer.create(getApplicationContext(), songs_array[i]);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometer);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        x = sensorEvent.values[0];
        y = sensorEvent.values[1];
        z = sensorEvent.values[2];
        if (isFirstValue) {
            float deltaX = Math.abs(last_x - x);
            float deltaY = Math.abs(last_y - y);
            float deltaZ = Math.abs(last_z - z);
            // If the values of acceleration have changed on at least two axes, then we assume that we are in
            // a shake motion
            if ((deltaX > shakeThreshold && deltaY > shakeThreshold)
                    || (deltaX > shakeThreshold && deltaZ > shakeThreshold)
                    || (deltaY > shakeThreshold && deltaZ > shakeThreshold)) {

                boolean playing = Arrays.stream(mMediaPlayer).anyMatch(MediaPlayer::isPlaying);

                if (!playing) {
                    mMediaPlayer[current_media].start();
                    textView.setText("PLaying track: " + current_media);

                    current_media = (current_media + 1) % songs_array.length;


                }

            }
        }
        last_x = x;
        last_y = y;
        last_z = z;
        isFirstValue = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}