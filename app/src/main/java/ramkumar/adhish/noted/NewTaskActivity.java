package ramkumar.adhish.noted;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import database.DatabaseManager;
import model.Task;

public class NewTaskActivity extends AppCompatActivity {

    private static final DateFormat formatter = SimpleDateFormat.getDateInstance();

    private int year, month, day;
    private boolean update;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        setDueDate(Calendar.getInstance());
        setDateClickAction();
        createSpinner(R.id.priority, R.array.importance);
        createSpinner(R.id.status, R.array.status);

        Bundle bundle = getIntent().getExtras();
        this.update = bundle.getBoolean("update");
        if (update) {
            this.task = new Task(
                    bundle.getString(DatabaseManager.ID),
                    bundle.getString(DatabaseManager.NAME),
                    bundle.getString(DatabaseManager.PRIORITY),
                    bundle.getString(DatabaseManager.STATUS),
                    bundle.getString(DatabaseManager.DUE_DATE),
                    bundle.getString(DatabaseManager.NOTES)
            );

            String date = bundle.getString(DatabaseManager.DUE_DATE);
            if (date != null) {
                String[] parts = date.split(", ");
                this.year = Integer.parseInt(parts[2]);
                String[] subParts = parts[1].split(" ");
                this.day = Integer.parseInt(subParts[1]);
                this.month = getMonth(subParts[0]);
            }

            EditText taskName = (EditText) findViewById(R.id.task_name);
            taskName.setText(bundle.getString(DatabaseManager.NAME));

            EditText taskNotes = (EditText) findViewById(R.id.task_notes);
            taskNotes.setText(bundle.getString(DatabaseManager.NOTES));

            Button dueDate = (Button) findViewById(R.id.dueDate);
            dueDate.setText(bundle.getString(DatabaseManager.DUE_DATE));

            Spinner status = (Spinner) findViewById(R.id.status);
            status.setSelection(getStatusIndex(bundle.getString(DatabaseManager.STATUS)));

            Spinner priority = (Spinner) findViewById(R.id.priority);
            priority.setSelection(getPriorityIndex(bundle.getString(DatabaseManager.PRIORITY)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cancel) {
            startActivity(new Intent(NewTaskActivity.this, ToDoListActivity.class));
        } else if (id == R.id.action_save) {
            if(validateInput()) {
                startActivity(new Intent(NewTaskActivity.this, ToDoListActivity.class));
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(i, i1, i2);
                setDueDate(calendar);
            }
        }, this.year, this.month, this.day);
    }

    private void createSpinner(int spinnerID, int itemsResourceID) {
        Spinner spinner = (Spinner) findViewById(spinnerID);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this, itemsResourceID, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setDueDate(Calendar calendar) {
        Button dueDate = (Button) findViewById(R.id.dueDate);

        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        String dayOfWeek = getDayFromInteger(calendar.get(Calendar.DAY_OF_WEEK));

        dueDate.setText(
                String.format("%s, %s", dayOfWeek, formatter.format(calendar.getTime())));
    }

    private String getDayFromInteger(int day) {
        switch (day) {
            case 1:
                return "Sun";
            case 2:
                return "Mon";
            case 3:
                return "Tues";
            case 4:
                return "Wed";
            case 5:
                return "Thurs";
            case 6:
                return "Fri";
            case 7:
                return "Sat";
        }
        throw new IllegalArgumentException();
    }

    private void setDateClickAction() {
        findViewById(R.id.dueDate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);
            }
        });
    }

    private boolean validateInput() {
        boolean valid = true;

        EditText taskName = (EditText) findViewById(R.id.task_name);
        if (taskName.getText() == null || taskName.getText().length() == 0
                || taskName.getText().length() > 30) {
            valid = false;
            findViewById(R.id.name_error).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.name_error).setVisibility(View.INVISIBLE);
        }

        EditText taskNotes = (EditText) findViewById(R.id.task_notes);
        if (taskNotes.getText() == null || taskNotes.getText().length() > 150) {
            valid = false;
            findViewById(R.id.notes_error).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.notes_error).setVisibility(View.INVISIBLE);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(this.year, this.month, this.day);
        if(!Calendar.getInstance().getTime().before(calendar.getTime())) {
            findViewById(R.id.date_error).setVisibility(View.VISIBLE);
            valid = false;
        } else {
            findViewById(R.id.date_error).setVisibility(View.INVISIBLE);
        }

        if (valid) {

            DatabaseManager databaseManager = new DatabaseManager(this);

            Button dueDate = (Button) findViewById(R.id.dueDate);
            Spinner status = (Spinner) findViewById(R.id.status);
            Spinner priority = (Spinner) findViewById(R.id.priority);

            if (this.update) {
                valid = databaseManager.updateTask(
                        task.getID(),
                        taskName.getText().toString(),
                        taskNotes.getText().toString(),
                        dueDate.getText().toString(),
                        status.getSelectedItem().toString(),
                        priority.getSelectedItem().toString()
                );
            } else {
                valid = databaseManager.createTask(
                        taskName.getText().toString(),
                        taskNotes.getText().toString(),
                        dueDate.getText().toString(),
                        status.getSelectedItem().toString(),
                        priority.getSelectedItem().toString());
            }
        }
        return valid;
    }

    private int getMonth(String month) {
        switch (month.toUpperCase()) {
            case "JAN":
                return 1;
            case "FEB":
                return 2;
            case "MAR":
                return 3;
            case "APR":
                return 4;
            case "MAY":
                return 5;
            case "JUN":
                return 6;
            case "JUL":
                return 7;
            case "AUG":
                return 8;
            case "SEP":
                return 9;
            case "OCT":
                return 10;
            case "NOV":
                return 11;
            case "DEC":
                return 12;
            default:
                throw new IllegalArgumentException();
        }
    }

    private int getStatusIndex(String status) {
        switch (status) {
            case "TODO":
                return 0;
            case "IN PROGRESS":
                return 1;
            case "COMPLETED":
                return 2;
        }
        throw new IllegalArgumentException();
    }

    private int getPriorityIndex(String priority) {
        switch (priority) {
            case "LOW":
                return 0;
            case "MEDIUM":
                return 1;
            case "HIGH":
                return 2;
        }
        throw new IllegalArgumentException();
    }
}
