/**
 * Task class represents a single task within a project
 * Contains attributes for task identification, description, completion status, type, and duration
 */
public class Task {
    private int taskId;
    private String description;
    private boolean completed;
    private char taskType;
    private int taskDuration;

    /**
     * Full constructor to initialize all task attributes.
     *
     * @param taskId Unique identifier for the task
     * @param description Description of the task
     * @param taskType Task type: A = Admin, S = Support, L = Logistics
     * @param taskDuration Duration in hours
     * @param completed Completion status (true if completed)
     */
    public Task(int taskId, String description, char taskType, int taskDuration, boolean completed) {
        this.taskId = taskId;
        this.description = description;
        this.taskType = taskType;
        this.taskDuration = taskDuration;
        this.completed = completed;
    }

    public Task() {
        // Default constructor for flexibility
    }


    /**
     * Gets the task ID.
     * @return taskId as int
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * Sets the task ID.
     * @param taskId int (must be between 1-99)
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
    
 /**
     * Gets the task description.
     * @return description as String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the task description.
     * @param description String describing the task
     */
    public void setDescription(String description) {
        this.description = description;
    }

 /**
     * Gets the completion status of the task.
     * @return true if completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Sets the completion status of the task.
     * @param completed boolean
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Gets the task type.
     * @return char: A, S, or L
     */
    public char getTaskType() {
        return taskType;
    }

    /**
     * Sets the task type.
     * @param taskType char: A = Admin, S = Support, L = Logistics
     */
    public void setTaskType(char taskType) {
        this.taskType = taskType;
    }

    /**
     * Gets the task duration in hours.
     * @return taskDuration as int
     */
    public int getTaskDuration() {
        return taskDuration;
    }

    /**
     * Sets the task duration in hours.
     * @param taskDuration int
     */
    public void setTaskDuration(int taskDuration) {
        this.taskDuration = taskDuration;
    } 
}