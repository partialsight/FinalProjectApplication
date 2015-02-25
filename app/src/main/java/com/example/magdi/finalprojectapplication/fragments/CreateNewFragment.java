package com.example.magdi.finalprojectapplication.fragments;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;
import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment;
import com.example.magdi.finalprojectapplication.R;
import com.example.magdi.finalprojectapplication.db.TaskContract;
import com.example.magdi.finalprojectapplication.db.TaskDBHelper;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by magdi on 2/19/15.
 */
public class CreateNewFragment extends TodoListFragment implements
        DatePickerDialogFragment.DatePickerDialogHandler, TimePickerDialogFragment.TimePickerDialogHandler {
    View view;
    static Spinner eventSpinner;
    Button button;
    TextView ttv;
    TextView dtv;
    EditText taskDesrp;
    String dateString;
    String timeString;
    Stack<PickerResult> handlerQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_new_item, container, false);

        handlerQueue = new Stack<PickerResult>();

        ttv = (TextView) view.findViewById(R.id.text);
        dtv = (TextView) view.findViewById(R.id.text2);
        taskDesrp = (EditText)view.findViewById(R.id.task_input);
        button = (Button) view.findViewById(R.id.submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int task_type_index = eventSpinner.getSelectedItemPosition();
                String task_desc = taskDesrp.getText().toString();

                if (task_type_index == 0) {
                    Toast.makeText(view.getContext(), "Please select a category", Toast.LENGTH_SHORT ).show();
                    return;
                }
                if(task_desc == null || task_desc.length() == 0){
                    Toast.makeText(view.getContext(), "Please enter a task label", Toast.LENGTH_SHORT ).show();
                    return;
                }
                getDateFromPicker(new PickerResult() {
                    @Override
                    public void Result(PickerMode mode, String result) {
                        if (result != null) {
                            dateString = result;
                            getTimeFromPicker(new PickerResult() {
                                @Override
                                public void Result(PickerMode mode, String result) {
                                    if (result != null) {
                                        timeString = result;
                                        submit();
                                    }
                                }
                            });
                        }
                    }
                });

            }
        });
        eventSpinner = (Spinner) view.findViewById(R.id.spinner);

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                if (parent.getItemAtPosition(pos).toString()
                        .equals("Call")) {
                    taskDesrp.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
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

    void getDateFromPicker(PickerResult handler){
        handlerQueue.push(handler);
        DatePickerBuilder dpb = new DatePickerBuilder()
                .setFragmentManager(getChildFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setTargetFragment(CreateNewFragment.this);
        dpb.show();
    }

    void getTimeFromPicker(PickerResult handler){
        handlerQueue.push(handler);
        TimePickerBuilder tpb = new TimePickerBuilder()
                .setFragmentManager(getChildFragmentManager())
                .setStyleResId(R.style.BetterPickersDialogFragment)
                .setTargetFragment(CreateNewFragment.this);
        tpb.show();
    }


    void submit(){

        String task_type = eventSpinner.getSelectedItem().toString();
        String task_desc = taskDesrp.getText().toString();

        String task_date = dateString;
        String task_time = timeString;


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
        body.append("\nDate: "+ task_date);
        body.append("\nTime: "+ task_time);
        body.append("\nType: "+eventSpinner.getSelectedItem().toString());
        Toast.makeText(view.getContext(), body.toString(), Toast.LENGTH_LONG ).show();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get back arguments

       // String someTitle = getArguments().getString("someTitle", "");
       // Log.i("DEBUG",someTitle);
    }

    int PICKER_DIALOG_WAIT = 300;

    @Override
    public void onDialogDateSet(int result_code, final int year, final int month, final int day) {
        if(handlerQueue.size() == 0){
            throw new Error("Handler Queue is Empty");
        }
        final PickerResult handler = handlerQueue.pop();
        Log.d("DATE_SET","Date: " + year + "/" + month + "/" + day);

        // have to wait for dialog to close
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        handler.Result(PickerMode.DATE, "Date: " + year + "/" + month + "/" + day);
                    }
                },
                PICKER_DIALOG_WAIT);
    }

    @Override
    public void onDialogTimeSet(int reference, final int hourOfDay, final int minute) {
        if(handlerQueue.size() == 0){
            throw new Error("Handler Queue is Empty");
        }
        final PickerResult handler = handlerQueue.pop();
        Log.d("TIME_SET","Time: " + hourOfDay + ":" + minute);

        // have to wait for dialog to close
        new Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        handler.Result(PickerMode.TIME, "Time: " + hourOfDay + ":" + minute);
                    }
                },
                PICKER_DIALOG_WAIT);
    }

    //String screenName = getArguments().getString("screen_name");


    enum PickerMode{
        DATE,
        TIME
    }

    interface PickerResult{
        void Result(PickerMode mode, String result);
    }


}

