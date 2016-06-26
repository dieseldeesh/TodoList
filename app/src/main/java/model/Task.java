package model;

/**
 * Created by aramkumar on 6/26/16.
 */
public class Task {
    private String id, name, priority, status, dueDate, notes;

    public Task(String id, String name, String priority, String status, String dueDate, String notes) {
        this.id  = id;
        this.name = name;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.notes = notes;
    }

    public String getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getPriority() {
        return this.priority;
    }

    public String getStatus() {
        return this.status;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public String getNotes() {
        return this.notes;
    }

}
