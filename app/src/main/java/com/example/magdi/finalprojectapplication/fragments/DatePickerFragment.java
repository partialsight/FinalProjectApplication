package com.example.magdi.finalprojectapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.doomonafireball.betterpickers.datepicker.DatePickerBuilder;
import com.doomonafireball.betterpickers.datepicker.DatePickerDialogFragment;
import com.example.magdi.finalprojectapplication.R;


/**
 * Created by magdi on 2/16/15.
 */
public class DatePickerFragment extends TodoListFragment
        implements DatePickerDialogFragment.DatePickerDialogHandler {
    private TextView text;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_and_button_date, container, false);
        text = (TextView) view.findViewById(R.id.text2);
        button = (Button) view.findViewById(R.id.button2);
        text.setText("--");
        button.setText("Set Date");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerBuilder dpb = new DatePickerBuilder()
                        .setFragmentManager(getChildFragmentManager())
                        .setStyleResId(R.style.MyCustomBetterPickerTheme)
                        .setTargetFragment(DatePickerFragment.this);
                dpb.show();
            }
        });
        return view;
    }

    @Override
    public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {
        text.setText("Date: " + year + "/" + monthOfYear + "/" + dayOfMonth);
    }
}
