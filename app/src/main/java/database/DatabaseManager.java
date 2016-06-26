package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import model.Task;

/**
 * Created by aramkumar on 6/26/16.
 */
public class DatabaseManager extends SQLiteOpenHelper {
    public static final String
            DATABASE_NAME   = "tasks.db",
            TABLE_NAME      = "task_table",
            ID              = "id",
            NAME            = "name",
            NOTES           = "notes",
            DUE_DATE        = "due_date",
            STATUS          = "status",
            PRIORITY        = "priority";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + ID        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME      + " VARCHAR(30) NOT NULL, "
                + NOTES     + " VARCHAR(15), "
                + DUE_DATE  + " VARCHAR(30) NOT NULL, "
                + STATUS    + " VARCHAR(15) NOT NULL, "
                + PRIORITY  + " VARCHAR(10) NOT NULL)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean createTask(
            String name, String notes, String dueDate, String status, String priority) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(NOTES, notes);
        values.put(DUE_DATE, dueDate);
        values.put(STATUS, status);
        values.put(PRIORITY, priority);
        return db.insert(TABLE_NAME, null, values) != -1;
    }

    public List<Task> getTasks() {
        SQLiteDatabase db = getWritableDatabase();
        List<Task> tasks = new ArrayList<Task>();
        Task task;
        Cursor cursor  = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while(cursor.moveToNext()) {
            task = new Task(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(5),
                    cursor.getString(4),
                    cursor.getString(3),
                    cursor.getString(2));
            tasks.add(task);
        }
        return tasks;
    }

    public boolean updateTask(
            String id, String name, String notes, String dueDate, String status, String priority) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(NAME, name);
        values.put(NOTES, notes);
        values.put(DUE_DATE, dueDate);
        values.put(STATUS, status);
        values.put(PRIORITY, priority);
        db.update(TABLE_NAME, values, "id = ?", new String[] {id});
        return true;
    }

    public void deleteTask(String id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,"id = ?", new String[] {id});
    }
}
