package com.example.magdi.finalprojectapplication.activites;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.magdi.finalprojectapplication.db.TaskContract;
import com.example.magdi.finalprojectapplication.db.TaskDBHelper;
import com.astuetz.PagerSlidingTabStrip;
import com.example.magdi.finalprojectapplication.R;
import com.example.magdi.finalprojectapplication.fragments.CreateNewFragment;
import com.example.magdi.finalprojectapplication.fragments.TimePickerFragment;
import com.example.magdi.finalprojectapplication.fragments.TodoListFragment;


public class Todo_list_Activity extends ActionBarActivity {
//AIzaSyAvILDDfhplmtWTVQ8nhXOha2UDEtG29Jg
EditText text1;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_);

        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);
        ab.setIcon(R.drawable.ic_logo2);

        ViewPager vpPager = (ViewPager)findViewById(R.id.viewpager);
        //set viewpager adapter
        vpPager.setAdapter(new TodoPagerAdapter(getSupportFragmentManager()));
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        tabStrip.setViewPager(vpPager);
        tabStrip.setIndicatorColor(getResources().getColor(R.color.darker_blue));
        tabStrip.setTextColor(getResources().getColor(R.color.darker_blue));
        tabStrip.notifyDataSetChanged();
        tabStrip.setBackgroundColor(Color.argb(0xFF, 0xFF, 0xFF, 0xFF));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //return order of the fregment in view pager
    public class TodoPagerAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = {"Today","To Do List","Create New"};

        //adapter
        public TodoPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        //order of the fragments
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new TimePickerFragment();
            } else if (position == 1) {
                return new TodoListFragment();
            } else if (position == 2) {
                return new CreateNewFragment();
            } else {
                return null;
            }
        }
        // return tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
        //return how many fragments
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

}
