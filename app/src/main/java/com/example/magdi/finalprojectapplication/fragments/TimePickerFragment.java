package com.example.magdi.finalprojectapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.doomonafireball.betterpickers.timepicker.TimePickerBuilder;
import com.doomonafireball.betterpickers.timepicker.TimePickerDialogFragment;
import com.example.magdi.finalprojectapplication.R;


/**
 * Created by magdi on 2/16/15.
 */

public class TimePickerFragment extends TodoListFragment
        implements TimePickerDialogFragment.TimePickerDialogHandler {
    private TextView text;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_and_button, container, false);

        text = (TextView) view.findViewById(R.id.text);
        button = (Button) view.findViewById(R.id.button);
        text.setText("--");
        button.setText("Set Time");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerBuilder tpb = new TimePickerBuilder()
                        .setFragmentManager(getChildFragmentManager())
                        .setStyleResId(R.style.MyCustomBetterPickerTheme)
                        .setTargetFragment(TimePickerFragment.this);
                tpb.show();
            }
        });
        return view;
    }

    @Override
    public void onDialogTimeSet(int reference, int hourOfDay, int minute) {
        text.setText("" + hourOfDay + " " + minute);
    }
}