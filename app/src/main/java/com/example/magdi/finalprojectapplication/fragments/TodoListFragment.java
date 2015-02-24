package com.example.magdi.finalprojectapplication.fragments;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magdi.finalprojectapplication.R;
import com.example.magdi.finalprojectapplication.activites.TODOCursorAdapter;
import com.example.magdi.finalprojectapplication.db.TaskContract;
import com.example.magdi.finalprojectapplication.db.TaskDBHelper;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;

/**
 * Created by magdi on 2/14/15.
 */
public class TodoListFragment extends Fragment {


    private ImageButton button;
    private Button delbutton;
    View view;
    private TaskDBHelper helper;
    //SimpleCursorAdapter listAdapter;
    ListView listContent;
    TODOCursorAdapter listAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.todo_list_fragment, container, false);
        listContent = (ListView) view.findViewById(R.id.listContent);

        updateUI();



        return view;
    }


    private void updateUI() {

        helper = new TaskDBHelper(view.getContext());
        SQLiteDatabase sqlDB = helper.getReadableDatabase();
        Cursor cursor = sqlDB.query(TaskContract.TABLE,
                new String[]{TaskContract.Columns._ID, TaskContract.Columns.TASK_Type, TaskContract.Columns.TASK_Date, TaskContract.Columns.TASK_Time, TaskContract.Columns.TASK_Description},
                null, null, null, null, null);
        TODOCursorAdapter listAdapter = new TODOCursorAdapter
                (view.getContext(),
                R.layout.task_detail_view,
                cursor,
                new String[]{TaskContract.Columns.TASK_Description, TaskContract.Columns.TASK_Type, TaskContract.Columns.TASK_Date, TaskContract.Columns.TASK_Time},
                        new int[]{R.id.tvTitle, R.id.tvCriticsScore, R.id.tvCast, R.id.tvtime}       );
        listContent.setAdapter(listAdapter);
    }

private void makeCall(){

    View v = (View) view.getParent();
    button = (ImageButton) view.findViewById(R.id.buttonCall);
    PhoneCallListener phoneListener = new PhoneCallListener();
    TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
    telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

    // add button listener
    button.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {

            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:0377778888"));
            startActivity(callIntent);

        }

    });
}
    //monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener {

        String LOG_TAG = "LOGGING 123";
        private boolean isPhoneCalling = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    Intent i = getActivity().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getActivity().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    isPhoneCalling = false;
                }

            }
        }
    }

    public void onDoneButtonClick(View view) {


        TextView taskTextView = (TextView) view.findViewById(R.id.tvTitle);
        String task = taskTextView.getText().toString();

        String sql = String.format("DELETE FROM %s WHERE %s = '%s'",
                TaskContract.TABLE,
                TaskContract.Columns.TASK_Type,
                task);


        helper = new TaskDBHelper(view.getContext());
        SQLiteDatabase sqlDB = helper.getWritableDatabase();
        sqlDB.execSQL(sql);
        updateUI();
    }

}