package com.beactive.destination;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.beactive.R;
import com.beactive.core.BeActiveActivity;
import com.beactive.network.command.GetDestinationsRootCommand;
import com.beactive.network.command.GetDestinationsTreeCommand;
import com.beactive.schedule.ScheduleActivity;
import com.beactive.util.PrefUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SelectDestinationActivity extends BeActiveActivity implements OnSelectionFinishedListener {
    private DestinationsTree mDestinationsTree;
    private TreeFragmentManager mTreeFragmentManager;
    private SharedPreferences mPrefs;

    private int mDestinationsRootRequestId = -1;
    private int mDestinationsTreeRequestId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(PrefUtils.KEY_ROOT_ID);
        editor.remove(PrefUtils.KEY_DESTINATIONS_PATH);
        editor.remove(PrefUtils.KEY_SCHEDULE);
        editor.commit();

        setContentView(R.layout.activity_select_destination);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mPrefs.contains(PrefUtils.KEY_ROOT_ID)) {
            if (mDestinationsRootRequestId == -1) {
                // Download destinations root
                LoadingDialogFragment loading = new LoadingDialogFragment();
                loading.show(getSupportFragmentManager(), LoadingDialogFragment.class.getSimpleName());
                mDestinationsRootRequestId = getNetworkServiceHelper().getDestinationsRoot();
            } else if (!getNetworkServiceHelper().isPending(mDestinationsRootRequestId)) {
                dismissLoadingDialog();
            }
        }

        if (mDestinationsTreeRequestId != -1 && !getNetworkServiceHelper().isPending(mDestinationsTreeRequestId)) {
            dismissLoadingDialog();
        }
    }

    @Override
    public void onSelectionFinished(LinkedHashMap<Integer, DestinationItem> selection) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PrefUtils.KEY_DESTINATIONS_PATH, selectionToDestinationsPath(selection));
        editor.commit();

        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
        finish();
    }

    // FIXME Change format
    private String selectionToDestinationsPath(LinkedHashMap<Integer, DestinationItem> selectionResult) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, DestinationItem> entry : selectionResult.entrySet()) {
            builder.append(DestinationsTree.getTypeDescription(entry.getKey()))
                    .append(":")
                    .append(entry.getValue().getTitle())
                    .append(";");
        }
        return builder.toString();
    }

    private void dismissLoadingDialog() {
        LoadingDialogFragment loading =
                (LoadingDialogFragment) getSupportFragmentManager()
                        .findFragmentByTag(LoadingDialogFragment.class.getSimpleName());
        if (loading != null) {
            loading.dismiss();
        }
    }

    @Override
    public void onServiceCallback(int requestId, Intent requestIntent, int resultCode, Bundle data) {
        if (getNetworkServiceHelper().checkCommandClass(requestIntent, GetDestinationsRootCommand.class)) {
            if (resultCode == GetDestinationsRootCommand.RESPONSE_SUCCESS) {
                dismissLoadingDialog();

                ArrayList<DestinationRootItem> root = data.getParcelableArrayList("destinations_root");
                if (root != null) {
                    // FIXME This is implementation for testing
                    int testRootId = root.get(0).getId();

                    SharedPreferences.Editor editor = mPrefs.edit();
                    editor.putInt(PrefUtils.KEY_ROOT_ID, testRootId);
                    editor.commit();

                    // Download destinations tree
                    LoadingDialogFragment loading = new LoadingDialogFragment();
                    loading.show(getSupportFragmentManager(), LoadingDialogFragment.class.getSimpleName());
                    mDestinationsTreeRequestId = getNetworkServiceHelper().getDestinationsTree(testRootId);
                } else {
                    // FIXME Fix message
                    Toast.makeText(getApplicationContext(), "Can't load destinations root.", Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == GetDestinationsRootCommand.RESPONSE_PROGRESS) {
                // TODO For the future
            } else if (resultCode == GetDestinationsRootCommand.RESPONSE_FAILURE) {
                dismissLoadingDialog();
                // FIXME Fix message
                Toast.makeText(getApplicationContext(), "Can't load destinations root.", Toast.LENGTH_LONG).show();
            }
        }

        if (getNetworkServiceHelper().checkCommandClass(requestIntent, GetDestinationsTreeCommand.class)) {
            if (resultCode == GetDestinationsTreeCommand.RESPONSE_SUCCESS) {
                dismissLoadingDialog();

                mDestinationsTree = data.getParcelable("destinations_tree");
                if (mDestinationsTree != null) {
                    mTreeFragmentManager = new TreeFragmentManager(getSupportFragmentManager(),
                            R.id.select_frame, mDestinationsTree, SelectDestinationActivity.this);
                    mTreeFragmentManager.startSelection();
                } else {
                    // FIXME Fix message
                    Toast.makeText(getApplicationContext(), "Can't load destinations tree.", Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == GetDestinationsTreeCommand.RESPONSE_PROGRESS) {
                // TODO For the future
            } else if (resultCode == GetDestinationsTreeCommand.RESPONSE_FAILURE) {
                dismissLoadingDialog();
                // FIXME Fix message
                Toast.makeText(getApplicationContext(), "Can't load destinations tree.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // TODO Move this class to BeActiveActivity?
    public static class LoadingDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setCancelable(false);
            return progressDialog;
        }
    }
}