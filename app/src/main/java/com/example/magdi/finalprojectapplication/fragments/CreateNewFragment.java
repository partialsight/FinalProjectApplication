package com.example.magdi.finalprojectapplication.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magdi.finalprojectapplication.R;
import com.example.magdi.finalprojectapplication.db.TaskContract;
import com.example.magdi.finalprojectapplication.db.TaskDBHelper;

/**
 * Created by magdi on 2/19/15.
 */
public class CreateNewFragment extends TodoListFragment  {
    View view;
    static Spinner eventSpinner;
    Button button;
    TextView ttv;
    TextView dtv;
    EditText taskDesrp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_new_item, container, false);



        ttv = (TextView) view.findViewById(R.id.text);
        dtv = (TextView) view.findViewById(R.id.text2);
        taskDesrp = (EditText)view.findViewById(R.id.task_input);
        button = (Button) view.findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String task_type = eventSpinner.getSelectedItem().toString();
                String task_date = dtv.getText().toString();
                String task_time = ttv.getText().toString();
                String task_desc = taskDesrp.getText().toString();

                TaskDBHelper helper = new TaskDBHelper(view.getContext());
                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.clear();
                values.put(TaskContract.Columns.TASK_Type,task_type);
                values.put(TaskContract.Columns.TASK_Date,task_date);
                values.put(TaskContract.Columns.TASK_Time,task_time);
                values.put(TaskContract.Columns.TASK_Description,task_desc);

                db.insertWithOnConflict(TaskContract.TABLE,null,values,
                        SQLiteDatabase.CONFLICT_IGNORE);

                StringBuilder body = new StringBuilder();
                body.append("Description: "+taskDesrp.getText().toString());
                body.append("\nDate: "+dtv.getText().toString());
                body.append("\nTime: "+ttv.getText().toString());
                body.append("\nType: "+eventSpinner.getSelectedItem().toString());
             Toast.makeText(view.getContext(), body.toString(), Toast.LENGTH_LONG ).show();



            }
        });
        eventSpinner = (Spinner) view.findViewById(R.id.spinner);

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                if (parent.getItemAtPosition(pos).toString()
                        .equals("Call")) {
                    //taskDesrp.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
                    taskDesrp.setInputType(InputType.TYPE_CLASS_PHONE | InputType.TYPE_CLASS_PHONE);


                }

                else {
                    taskDesrp.setInputType(InputType.TYPE_CLASS_TEXT| InputType.TYPE_TEXT_FLAG_AUTO_CORRECT|InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);


                }
            }


            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get back arguments

       // String someTitle = getArguments().getString("someTitle", "");
       // Log.i("DEBUG",someTitle);
    }

    //String screenName = getArguments().getString("screen_name");





}

