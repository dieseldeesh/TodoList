package ramkumar.adhish.noted;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import adapter.TaskAdapter;
import database.DatabaseManager;
import model.Task;

public class ToDoListActivity extends AppCompatActivity {

    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView tasks = (ListView) findViewById(R.id.tasks);
        adapter = new TaskAdapter(this, R.layout.task, new DatabaseManager(this).getTasks());
        tasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ToDoListActivity.this, NewTaskActivity.class);
                intent.putExtra("update", true);
                Task task = adapter.getTask(i);
                Bundle bundle = new Bundle();
                bundle.putString(DatabaseManager.NAME, task.getName());
                bundle.putString(DatabaseManager.NOTES, task.getNotes());
                bundle.putString(DatabaseManager.DUE_DATE, task.getDueDate());
                bundle.putString(DatabaseManager.PRIORITY, task.getPriority());
                bundle.putString(DatabaseManager.STATUS, task.getStatus());
                bundle.putString(DatabaseManager.ID, task.getID());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        tasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(
                    AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ToDoListActivity.this);
                builder.setMessage("Do you want to delete this task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                adapter.remove(i);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int j) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                return true;
            }
        });
        tasks.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new_task) {
            Intent intent = new Intent(ToDoListActivity.this, NewTaskActivity.class);
            intent.putExtra("update", false);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
