package com.example.magdi.finalprojectapplication.db;

/**
 * Created by magdi on 2/22/15.
 */
import android.provider.BaseColumns;

public class TaskContract {
    public static final String DB_NAME = "com.example. magdi.TodoList.db.tasks";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "tasks";

    public class Columns {
        public static final String TASK_Type = "task";
        public static final String TASK_Date = "date";
        public static final String TASK_Time = "time";
        public static final String TASK_Description = "Description";
        public static final String _ID = BaseColumns._ID;
    }
}
