package com.example.hw14;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class StepsHistoryActivity extends AppCompatActivity {
    private ListView mSensorListView;
    private ListAdapter mListAdapter;
    private StepsDBHelper mStepsDBHelper;
    private ArrayList<DateStepsModel> mStepCountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_history);
        mSensorListView = (ListView) findViewById(R.id.steps_list);
        getDataForList();
        mListAdapter = new ListAdapter(mStepCountList, this);
        mSensorListView.setAdapter(mListAdapter);
        Intent stepsIntent = new Intent(getApplicationContext(), StepsService.class);
        startService(stepsIntent);
    }

    public void getDataForList() {
        mStepsDBHelper = new StepsDBHelper(this);
        mStepCountList = mStepsDBHelper.readStepsEntries();
    }
}
class ListAdapter extends BaseAdapter {
    TextView mDateStepCountText;
    ArrayList<DateStepsModel> mStepCountList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public ListAdapter(ArrayList<DateStepsModel> mStepCountList, Context mContext) {
        this.mStepCountList = mStepCountList;
        this.mContext = mContext;
        this.mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mStepCountList.size();
    }

    @Override
    public Object getItem(int position) {
        return mStepCountList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_rows, parent, false);
        }
        mDateStepCountText = (TextView) convertView.findViewById(R.id.sensor_name);
        mDateStepCountText.setText(mStepCountList.get(position).mDate + " – Total Steps: " + String.valueOf(mStepCountList.get(position).mStepCount));
        return convertView;
    }


}
