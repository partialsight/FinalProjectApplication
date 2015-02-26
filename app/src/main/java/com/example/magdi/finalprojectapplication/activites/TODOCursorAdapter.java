package com.example.magdi.finalprojectapplication.activites;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.magdi.finalprojectapplication.R;

/**
 * Created by magdi on 2/23/15.
 */
public class TODOCursorAdapter extends SimpleCursorAdapter {

    private Cursor c;
    private Context context;

    public TODOCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.c = c;
        this.context = context;
    }

    public View getView(int pos, View inView, ViewGroup parent) {
        View v = inView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.task_detail_view, null);
        }
        this.c.moveToPosition(pos);
        String type = this.c.getString(this.c.getColumnIndex("task"));
        String Description = this.c.getString(this.c.getColumnIndex("Description"));

        String date = this.c.getString(this.c.getColumnIndex("date"));
        String time = this.c.getString(this.c.getColumnIndex("time"));

        ImageButton iv = (ImageButton) v.findViewById(R.id.buttonCall);
        if (type.equals("Call")) {
            iv.setImageResource(R.drawable.ic_phone);
        } else if (type.equals("Email")) {
            iv.setImageResource(R.drawable.ic_email);
        }else if (type.equals("Visit")) {
            iv.setImageResource(R.drawable.ic_map);
        }else if (type.equals("Travel")) {
            iv.setImageResource(R.drawable.ic_travel);
        }else if (type.equals("Pay Bill")) {
            iv.setImageResource(R.drawable.ic_pay);
        }else if (type.equals("Special Event")) {
            iv.setImageResource(R.drawable.ic_special);
        }



        TextView fname = (TextView) v.findViewById(R.id.tvTitle);
        fname.setText(type);

        TextView lname = (TextView) v.findViewById(R.id.tvCriticsScore);
        lname.setText(Description);

        TextView title = (TextView) v.findViewById(R.id.tvCast);
        title.setText(date);
        TextView tvtime = (TextView) v.findViewById(R.id.tvtime);
        tvtime.setText(time);

        return (v);
    }

}