/** 
 * Project class represents a project containing multiple tasks
 * Manages tasks based on project type constraints
 */
public class Project {
    
    // Unique identifier for the project
    private int projectId;
    
    // Name of the project
    private String projectName;
    
    // Type of project: Small, Medium, or Large
    private String projectType;
    
    // Up to 3 tasks associated with this project
    private Task task1;
    private Task task2;
    private Task task3;
    
    /**
     * Gets the project ID.
     * @return projectId as int
     */
    public int getProjectId() {
        return projectId;
    }
    
    /**
     * Sets the project ID.
     * @param projectId int
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    
    /**
     * Gets the project name.
     * @return projectName as String
     */
    public String getProjectName() {
        return projectName;
    }
    
    /**
     * Sets the project name.
     * @param projectName String
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    /**
     * Gets the project type.
     * @return projectType as String
     */
    public String getProjectType() {
        return projectType;
    }
    
    /**
     * Sets the project type.
     * @param projectType String (Small, Medium, Large)
     */
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
    
    /**
     * Gets Task 1.
     * @return task1 as Task
     */
    public Task getTask1() {
        return task1;
    }
    
    /**
     * Sets Task 1.
     * @param task1 Task
     */
    public void setTask1(Task task1) {
        this.task1 = task1;
    }
    
    /**
     * Gets Task 2.
     * @return task2 as Task
     */
    public Task getTask2() {
        return task2;
    }
    
    /**
     * Sets Task 2.
     * @param task2 Task
     */
    public void setTask2(Task task2) {
        this.task2 = task2;
    }
    
    /**
     * Gets Task 3.
     * @return task3 as Task
     */
    public Task getTask3() {
        return task3;
    }
    
    /**
     * Sets Task 3.
     * @param task3 Task
     */
    public void setTask3(Task task3) {
        this.task3 = task3;
    }
}
