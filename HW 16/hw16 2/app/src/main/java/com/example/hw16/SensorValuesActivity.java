package com.example.hw16;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.PointsGraphSeries;

public class SensorValuesActivity extends AppCompatActivity implements SensorEventListener {

    private static int RANGE = 4;
    private SensorManager mSensorManager;

    private PointsGraphSeries<DataPoint> mSeries1;
    private long graph2LastXValue = 0;
    GraphView graph;
    private int mSensorType;
    private Sensor mSensor;
    private TextView mEventValue_0;
    private TextView mEventValue_1;
    private TextView mEventValue_2;
    private TextView mEventValue_3;
    private TextView mEventValue_4;
    private TextView mEventValue_5;
    private TextView mEventValue_6;
    private TextView mTime;
    private TextView mAccuracy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSensorData();
        setContentView(R.layout.activity_sensor_values);
        graph = (GraphView) findViewById(R.id.graph);
        mSeries1 = new PointsGraphSeries<DataPoint>();
        graph.addSeries(mSeries1);

        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScrollableY(true);
        graph.getViewport().setScalableY(true);

        mSeries1.setCustomShape(new PointsGraphSeries.CustomShape() {
            @Override
            public void draw(Canvas canvas, Paint paint, float x, float y, DataPointInterface dataPoint) {
                paint.setStrokeWidth(5);
                canvas.drawLine(x - 20, y - 20, x + 20, y + 20, paint);
                canvas.drawLine(x + 20, y - 20, x - 20, y + 20, paint);
            }
        });

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(RANGE);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(10);
        graph.getViewport().setMinY(0);

        Intent intent = getIntent();
        mSensorType = intent.getIntExtra(getResources().getResourceName
                (R.string.sensor_type), 0);
        mSensorManager = (SensorManager) this.getSystemService
                (Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(mSensorType);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        mEventValue_0.setText(String.valueOf(event.values[0]));
       // if (event.values.length)
        float x = event.values[0];
        float y = event.values[0];
        float z = event.values[0];
        long ts = event.timestamp;

        if(0 == graph2LastXValue)
            graph2LastXValue = ts;
        float nts = (ts-graph2LastXValue)*0.000000001f;
        System.out.println("ts = "+nts);

        if(nts > RANGE) {
            graph2LastXValue = ts;
            DataPoint [] dps = new DataPoint[1];
            dps[0] = new DataPoint(0,0);
            mSeries1.resetData(dps);
            nts = 0;
        }

        DataPoint dataPoint = new DataPoint(nts, y);
        mSeries1.appendData(dataPoint, false, 100);

//        mAccuracy.setText(String.valueOf(event.accuracy));
//        mTime.setText(String.valueOf(event.timestamp));
//        if (event.values.length > 1) {
//            mEventValue_1.setText(String.valueOf(event.values[1]));
//        }
//        if (event.values.length > 2) {
//            mEventValue_2.setText(String.valueOf(event.values[2]));
//        }
//        if (event.values.length > 3) {
//            mEventValue_3.setText(String.valueOf(event.values[3]));
//        }
//        if (event.values.length > 4) {
//            mEventValue_4.setText(String.valueOf(event.values[4]));
//        }
//        if (event.values.length > 5) {
//            mEventValue_5.setText(String.valueOf(event.values[5]));
//        }
//        if (event.values.length > 6) {
//            mEventValue_6.setText(String.valueOf(event.values[6]));
//        }
    }
}