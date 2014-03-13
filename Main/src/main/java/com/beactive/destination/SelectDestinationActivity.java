package com.beactive.destination;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import android.view.View;

import com.beactive.R;
import com.beactive.network.ConnectionMock;
import com.beactive.network.ServerConnection;
import com.beactive.schedule.ScheduleActivity;

public class SelectDestinationActivity extends FragmentActivity {
    private ServerConnection mServerConnection;
    private DestinationsTree mDestinationsTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_destination);

        mServerConnection = new ConnectionMock(this);
        mDestinationsTree = mServerConnection.getDestinationsTree();

        Pair<DestinationsTree.DestinationType, Integer> level;
        String title;
        int style;
        for (int i = 0; i < mDestinationsTree.getStructure().size(); i++) {
            level = mDestinationsTree.getStructure().get(i);
            title = getTitleFromResources(level.first);
            style = level.second;
        }
    }

    public void onFinishActivity(View view) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
        finish();
    }

    private String getTitleFromResources(DestinationsTree.DestinationType type) {
        switch (type) {
            case faculties: {
                return getString(R.string.title_faculties);
            }
            case courses: {
                return getString(R.string.title_courses);
            }
            case groups: {
                return getString(R.string.title_groups);
            }
            case departments: {
                return getString(R.string.title_departments);
            }
            default: {
                return null;
            }
        }
    }
}