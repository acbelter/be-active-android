package com.beactive.destination;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.beactive.R;
import com.beactive.network.ConnectionMock;
import com.beactive.network.ServerConnection;
import com.beactive.schedule.ScheduleActivity;

import java.util.LinkedHashMap;
import java.util.Map;

public class SelectDestinationActivity extends ActionBarActivity implements OnSelectionFinishedListener {
    private ServerConnection mServerConnection;
    private DestinationsTree mDestinationsTree;
    private TreeFragmentManager mTreeFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_destination);

        mServerConnection = new ConnectionMock(this);
        mDestinationsTree = mServerConnection.getDestinationsTree();

        mTreeFragmentManager = new TreeFragmentManager(getSupportFragmentManager(),
                R.id.select_frame, mDestinationsTree, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTreeFragmentManager.startSelection();
    }

    @Override
    public void onSelectionFinished(LinkedHashMap<Integer, DestinationItem> selectionResult) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra("schedule", selectionResultToString(selectionResult));
        startActivity(intent);
        finish();
    }

    // FIXME Method for testing
    private String selectionResultToString(LinkedHashMap<Integer, DestinationItem> selectionResult) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, DestinationItem> entry : selectionResult.entrySet()) {
            builder.append(DestinationsTree.getTypeDescription(entry.getKey()))
                    .append(":")
                    .append(entry.getValue().getTitle())
                    .append(";");
        }
        return builder.toString();
    }
}