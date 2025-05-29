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
    private Task[] tasks;  
    
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
        setTasksArraySize();
    }

    /**
     * Initialises the tasks array based on the project type. 
     */
    private void setTasksArraySize() {
        if (projectType.equalsIgnoreCase("Small")) {
            tasks = new Task[1];
        } else if (projectType.equalsIgnoreCase("Medium")) {
            tasks = new Task [2];
        } else if (projectType.equalsIgnoreCase("Large")) {
            tasks = new Task[3];
        } else {
            tasks = new Task[0]; 
        }
    }
    
    /** 
     * Gets the array of the tasks.
     * @return Task array
     */
    public Task[] getTasks() {
        return tasks;
    }


}
