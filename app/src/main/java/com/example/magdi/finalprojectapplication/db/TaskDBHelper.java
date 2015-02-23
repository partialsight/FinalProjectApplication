package com.example.magdi.finalprojectapplication.db;

/**
 * Created by magdi on 2/22/15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TaskDBHelper extends SQLiteOpenHelper {

    public TaskDBHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqlDB) {
        String sqlQuery =
                String.format("CREATE TABLE %s (" +
                                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "%s TEXT," + "%s TEXT, " + "%s TEXT," + "%s TEXT)", TaskContract.TABLE,
                        TaskContract.Columns.TASK_Type, TaskContract.Columns.TASK_Date, TaskContract.Columns.TASK_Time, TaskContract.Columns.TASK_Description);

        Log.d("TaskDBHelper", "Query to form table: " + sqlQuery);
        sqlDB.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqlDB, int i, int i2) {
        sqlDB.execSQL("DROP TABLE IF EXISTS " + TaskContract.TABLE);
        onCreate(sqlDB);
    }
}