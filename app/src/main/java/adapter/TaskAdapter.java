package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import database.DatabaseManager;
import model.Task;
import ramkumar.adhish.noted.R;

/**
 * Created by aramkumar on 6/26/16.
 */
public class TaskAdapter extends ArrayAdapter {

    private List<Task> tasks;
    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public TaskAdapter(Context context, int resource, List<Task> tasks) {
        super(context, resource, tasks);
        this.tasks = tasks;
        this.resource = resource;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.task, null);
        }

        TextView
                taskName = (TextView) convertView.findViewById(R.id.task_name_template),
                taskPriority = (TextView) convertView.findViewById(R.id.task_priority_template),
                taskStatus = (TextView) convertView.findViewById(R.id.task_status_template),
                taskDueDate = (TextView) convertView.findViewById(R.id.task_due_date_template);

        Task task = tasks.get(position);
        taskName.setText(task.getName());
        String priority = task.getPriority();
        taskPriority.setText(priority);
        taskPriority.setTextColor(this.context.getResources().getColor(getColorCode(priority)));
        taskStatus.setText(task.getStatus());
        taskDueDate.setText(task.getDueDate());

        return convertView;
    }

    public void remove(int position) {
        new DatabaseManager(this.context).deleteTask(this.tasks.get(position).getID());
        remove(getItem(position));
    }

    public Task getTask(int position) {
        return this.tasks.get(position);
    }

    private int getColorCode(String status) {
        switch(status) {
            case "LOW":
                return R.color.colorLow;
            case "MEDIUM":
                return R.color.colorMedium;
            case "HIGH":
                return R.color.colorHigh;
        }
        return R.color.colorHigh;
    }
}
