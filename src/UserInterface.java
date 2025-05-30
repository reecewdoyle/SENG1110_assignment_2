/**
 * UserInterface class handles all user interactions for the Project Management System
 * Provides a menu-driven interface for managing projects and tasks
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class UserInterface {
    
    // -------------------------------------------------------------------------
    // Instance Variables
    // -------------------------------------------------------------------------
    
    private Project[] projects = new Project[10];
    private Scanner scannerInput;
    
    // -------------------------------------------------------------------------
    // Main Method - Entry Point
    // -------------------------------------------------------------------------

    /**
     * Entry point for the program. Creates a UserInterface object and runs the program.
     */
    public static void main(String[] args) {
        new UserInterface().run();
    }

    // -------------------------------------------------------------------------
    // Main Program Loop
    // -------------------------------------------------------------------------

    /**
     * Initialises projects and scanner, loads seed data, and handles user menu input.
     */
    public void run() {
    for (int i = 0; i < projects.length; i++) {
        projects[i] = null;
    }


    scannerInput = new Scanner(System.in);

    int choice;

        // Loop until user selects -1 to exit
        do {
            choice = displayMenu();
            switch (choice) {
                case 1: createProject(); break;
                case 2: removeProject(); break;
                case 3: addTask(); break;
                case 4: markTaskAsCompleted(); break;
                case 5: removeTask(); break;
                case 6: displayProjectDetails(); break;
                case 7: displayCompletedTasks(); break;
                case 8: filterTasksByType(); break;
                case 9: displayProjectSummary(); break;
                case 10: loadFromFile(); break;
                case 11: saveToFile(); break;
                case -1: 
                    System.out.println("Thank you for using Project Managment System. Goodbye!"); break;
                default: 
                    System.out.println("Invalid choice. Please try again.");
                }
        } while (choice != -1);
    }

    // -------------------------------------------------------------------------
    // Menu Display Method
    // -------------------------------------------------------------------------

    /**
     * Displays the main menu and returns the user's selected option.
     * Prevents crashing from invalid or empty input
     * @return The menu option selected by the user.
     */
    private int displayMenu() {
        System.out.println("\n========== PROJECT MANAGEMENT SYSTEM ==========");
        System.out.println("1. Create a new project");
        System.out.println("2. Remove a project");
        System.out.println("3. Add a task to a project");
        System.out.println("4. Mark a task as completed");
        System.out.println("5. Remove a task from a project");
        System.out.println("6. Display all projects details");
        System.out.println("7. Display completed tasks");
        System.out.println("8. Filter tasks by type");
        System.out.println("9. Display project summary");
        System.out.println("10. Load from file");
        System.out.println("11. Save to file");
        System.out.println("-1 Exit");
        System.out.print("Enter your choice: ");
        
        if (scannerInput.hasNextLine()) {
            String input = scannerInput.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Error!");
                return 0;
            }

            boolean allDigits = input.matches("-?\\d+");
            if (allDigits) {
                return Integer.parseInt(input);
            } else {
                System.out.println("Error!");
                return 0;
            }
        }

        // fallback
        return 0;
    }
    
// -------------------------------------------------------------------------
// CREATE NEW PROJECT
// -------------------------------------------------------------------------

/**
 * Prompts the user to create a new project with a unique ID, name, and type.
 * Validates input and assigns the project to the next available project slot.
 * Allows a maximum of three projects in the system at any time.
 */
    private void createProject() {
        // Check if there's room for a new project
        if (!hasAvailableProjectSlot()) {
            System.out.println("\nMaximum projects reached!");
            return;
        }

        Project p = new Project();

        // Get validated project ID
        int id = promptValidProjectId(scannerInput, true);

        if (id == -1) {
            System.out.println("Project creation cancelled.");
            return;
        }

        // Check for duplicate ID and generate new one if necessary
        while (isProjectIdTaken(id)) {
            System.out.println("Project ID " + " already exists. Generating a new unique ID...");
            id = generateUniqueProjectId();
            
        }

        p.setProjectId(id);
        System.out.println("\nAssigned Project ID: " + p.getProjectId());

        
        // ----------------- Project Name Input -----------------
        System.out.print("\nEnter Project Name: ");
        String projectName = scannerInput.nextLine().trim();
        while (projectName.isEmpty()) {
            System.out.print("Project name cannot be empty. \nPlease enter a valid name: ");
            projectName = scannerInput.nextLine().trim();
        }
        p.setProjectName(projectName);
        System.out.println("Project Name " + p.getProjectName());        

        // ----------------- Project Type Input -----------------
        System.out.print("\nEnter Project Type (Small, Medium or Large): ");
        String projectType = scannerInput.nextLine().trim();

        // Ensure project type input is not empty before normalising and validating
        while (projectType.isEmpty()) {
            System.out.print("Project type cannot be empty. Please enter Small, Medium, or Large: ");
            projectType = scannerInput.nextLine().trim();
        } 

        // Normalize case
        projectType = projectType.toLowerCase();
        projectType = projectType.substring(0, 1).toUpperCase() + projectType.substring(1);

        // Validate type
        while (!projectType.equals("Small") && !projectType.equals("Medium") && !projectType.equals("Large")) {
            System.out.print("Invalid project type. Please enter Small, Medium, or Large: ");
            projectType = scannerInput.nextLine().trim();
            projectType = projectType.toLowerCase();
            projectType = projectType.substring(0, 1).toUpperCase() + projectType.substring(1);
        }
        
        p.setProjectType(projectType);
        System.out.println("Project Type " + p.getProjectType());

        // ----------------- Assign Project -----------------
        for (int i = 0; i < projects.length; i++) {
            if (projects[i] == null) {
                projects[i] = p;
                System.out.println("\nProject successfully created!");
                return;
            }
        }
    }


// -------------------------------------------------------------------------
// REMOVE PROJECT
// -------------------------------------------------------------------------

/**
 * Removes a project from the system based on user-provided ID.
 * Validates user input and only removes if the ID matches an existing project.
 * Entering -1 will return to the main menu without deleting anything.
 */
    private void removeProject() {
        // Check if there are any projects to remove
        if (noProjectsExist()) {
            System.out.println("\nNo projects exist to remove.");
            return;
        }
        
        // Display all existing project
        displayExistingProjects();

        while (true) {
            // Prompt for valid ID (allows -1 to cancel)
            int projectIdToRemove = promptValidProjectId(scannerInput, true);

            if (projectIdToRemove == -1) {
                System.out.println("Removal cancelled.");
                return;
            }

            // Try to find and remove the project
            for (int i = 0; i < projects.length; i++) {
                if (projects[i] != null && projects[i].getProjectId() == projectIdToRemove) {
                    projects[i] = null;
                    System.out.println("Project ID " + projectIdToRemove + " successfully removed.");
                    return;
                }
            }

            // Loop back if no matching project was found
            System.out.println("No project found with ID: " + projectIdToRemove + ". Please try again or enter -1 to cancel.");
        }
    }

// -------------------------------------------------------------------------
// ADD TASK TO PROJECT
// -------------------------------------------------------------------------

/**
 * Adds a new task to a selected project.
 * Ensures the project exists, has space for tasks, and all inputs are valid.
 */
    private void addTask() {
        if (noProjectsExist()) {
            System.out.println("There are no projects to add a task to.");
            return;
        }

        // Display existing projects
        displayExistingProjects();

        // Prompt user to select a project by ID
        Project workProject = selectProjectById(scannerInput, "Enter the Project ID to mark a task as completed (or -1 to cancel): ");
        if (workProject == null) {
            System.out.println("Task creation cancelled.");
            return;
        }

        // Display the Selected Project
        System.out.println("Selected Project: " + workProject.getProjectName());

        // Display how many tasks this project can have
        System.out.println("Project Type: " + workProject.getProjectType());
        displayTaskLimitByProjectType(workProject.getProjectType());

        // Check to see if there's room to add a task
        if (!projectHasRoomForTask(workProject)) {
            System.out.println("This project already has the maximum number of tasks allowed.");
            return;
        }

        // Get intial task ID from user
        int taskId = promptValidTaskId(scannerInput);

        // Check if the ID is already taken within the selected project
        if (isTaskIdTakenInProject(workProject, taskId)) {
            System.out.println("Task ID " + taskId + " already exists in this project. Generating a new unique ID...");
            taskId = generateUniqueTaskId(workProject);
            System.out.println("Assigned new unique Task ID: " + taskId);
        }
           
        // Task description input
        String description;
        do {
            System.out.print("\nEnter task description: ");
            description = scannerInput.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("Description cannot be empty.");
            }

        } while (description.isEmpty());

        // Task type input and validation
        char taskType = ' ';
        boolean validTaskType = false;
        do {
            System.out.print("\nEnter task type (A = Admin, S = Support, L = Logistics): ");
            String input = scannerInput.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Task type cannot be empty. Please enter A, S, or L.");
                continue;
            }

            input = input.toUpperCase();
            
            if (input.length() == 1) {
                taskType = input.charAt(0);
                if (taskType == 'A' || taskType == 'S' || taskType == 'L') {
                    validTaskType = true;
                } else {
                    System.out.println("Invalid task type. Please enter A, S or L.");
                }
            } else {
                System.out.println("Please enter a single letter: A, S, or L.");
            }
        } while (!validTaskType);

        // Task duration input
        int duration = 0;
        boolean validDuration = false;

        do {
            System.out.print("Enter Task Duration (1-100 hours): ");
            String input = scannerInput.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter a number between 1 and 100.");
                continue;
            }

            try {
                duration = Integer.parseInt(input);

                if (duration >=1 && duration <= 100) {
                    validDuration = true;
                } else {
                    System.out.println(("Duration must be between 1 and 100."));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number between 1 and 100.");
            }

        } while (!validDuration);


        // Create and assign task
        Task newTask = new Task();
        newTask.setTaskId(taskId);
        newTask.setDescription(description);
        newTask.setTaskType(taskType);
        newTask.setTaskDuration(duration);
        newTask.setCompleted(false);

        boolean taskAdded = false;

        for (int i =0; i < workProject.getTasks().length; i++) {
            if (workProject.getTasks()[i] == null) {
                workProject.getTasks()[i] = newTask;
                taskAdded = true;
                System.out.println("\nTask successfully added to project.");
                break;
            }
        }

        if (!taskAdded) {
            System.out.println("\nError: No available task slots in this project.");
        }
    }

// -------------------------------------------------------------------------
// MARK TASK AS COMPLETED
// -------------------------------------------------------------------------

/**
 * Marks a task as completed within a selected project.
 * Prompts the user to select a project by ID, then a task within that project.
 * Validates all input to ensure only valid IDs are accepted.
 * User can enter -1 at the task ID prompt to return to the main menu.
 */
    private void markTaskAsCompleted() {
        if (noProjectsExist()) {
            System.out.println("There are no projects to add a task to.");
            return;
        }

        // Display existing projects
        displayExistingProjects();
        // Prompt user to select a project by ID
        Project workProject = selectProjectById(scannerInput, "Enter the Project ID to mark a task as completed (or -1 to cancel): ");
        if (workProject == null) {
            System.out.println("Task creation cancelled.");
            return;
        }

        // Display the Selected Project
        System.out.println("Selected Project: " + workProject.getProjectName());

        // Display tasks in the selected project
        displayTasksForProject(workProject);

        // Prompt user to select a task by ID
        Task selectedTask = selectTaskById(workProject, scannerInput);
        if (selectedTask == null) {
            System.out.println("Task selection cancelled.");
            return;
        }

        if (selectedTask.isCompleted()) {
            System.out.println("Task is already marked as completed.");
        } else {
            selectedTask.setCompleted(true);
            System.out.println("Task marked as completed.");
        }

    }

// -------------------------------------------------------------------------
// REMOVE TASK
// -------------------------------------------------------------------------

/**
 * Removes a task from a selected project based on task ID.
 * Prompts the user to choose a project and task to delete.
 * Validates all user inputs and ensures the task exists before removing it.
 * User can enter -1 at the Task ID prompt to return to the menu.
 */
    private void removeTask() {
        if (noProjectsExist()) {
            System.out.println("There are no projects to remove a task from.");
            return;
        }

        // Display existing projects
        displayExistingProjects();
        
        // Select a project
        Project workProject = selectProjectById(scannerInput, "Enter the Project ID to mark a task as completed (or -1 to cancel): ");
        if (workProject == null) {
            System.out.println("Task removal cancelled.");
            return;
        }

        // Display tasks in the project
        displayTasksForProject(workProject);

        boolean hasTasks = false;
        for (Task t :  workProject.getTasks()) {
            if (t != null) {
                hasTasks = true;
                break;
            }
        }

        if (!hasTasks) {
            System.out.println("There are no tasks to remove in this project.");
            return;
        }

        // Prompt for the task to remove
        Task taskToRemove = selectTaskById(workProject, scannerInput);
        if (taskToRemove == null) {
            System.out.println("Task removal cancelled.");
            return;
        }

        // Remove the task by finding and nulling its slot
        Task[] tasks = workProject.getTasks();
        for (int i = 0; i < tasks.length; i++) {
            if (tasks[i] != null && tasks[i].getTaskId() == taskToRemove.getTaskId()) {
                tasks[i] = null;
                System.out.println("Task ID " + taskToRemove.getTaskId() + " successfully removed.");
                return;
            }
        }

        // Shouldn't reach here, but fallback just in case
        System.out.println("Unexpected error: Task could not be removed.");
    }

// -------------------------------------------------------------------------
// DISPLAY PROJECT DETAILS
// -------------------------------------------------------------------------

/**
 * Displays all saved projects and their associated task details.
 * If no projects are saved, a message is shown instead.
 * For each project, tasks are listed with ID, description, type, duration, and status.
 */
    private void displayProjectDetails() {
        if (noProjectsExist()) {
            System.out.println("\nThere are no saved projects to display.");
            return;
        }

        for (Project p : projects) {
            if (p != null) {
                System.out.println("\n---------------------------------------------------");
                System.out.println("Project ID: " + p.getProjectId());
                System.out.println("Project Name: " + p.getProjectName());
                System.out.println("Project Type: " + p.getProjectType());
                displayTasksForProject(p);
            }
        }
        System.out.println("---------------------------------------------------\n");
    }
// -------------------------------------------------------------------------
// DISPLAY COMPLETED TASKS
// -------------------------------------------------------------------------

/**
 * Displays only completed tasks for a specific project by projectId.
 * Prompts the user to select a project, then lists completed tasks.
 * If none are found, an appropriate message is shown.
 */
    private void displayCompletedTasks() {
        if (noProjectsExist()) {
            System.out.println("\nThere are no saved projects to check for completed tasks.");
            return;
        }

        displayExistingProjects();
        Project workProject = selectProjectById(scannerInput, "Enter the Project ID to mark a task as completed (or -1 to cancel): ");
        if (workProject == null) {
            System.out.println("Returning to main menu...");
            return;
        }

        System.out.println("\nCompleted Tasks in Project: " + workProject.getProjectName());
        boolean foundTask = false;

        for (Task t : workProject.getTasks()) {
            if (t != null && t.isCompleted()) {
                System.out.println("* Task ID: " + t.getTaskId()
                    + ", Description: " + t.getDescription()
                    + ", Type: " + t.getTaskType()
                    + ", Duration: " + t.getTaskDuration() + "h");
                foundTask = true;
            }
        }

        if (!foundTask) {
            System.out.println("No completed tasks found in this project.");
        }
    }
// -------------------------------------------------------------------------
// FILTER TASKS BY TYPE
// -------------------------------------------------------------------------

/**
 * Prompts the user to enter a task type (A, S, or L),
 * then displays all matching tasks from all projects.
 * If no tasks match, informs the user.
 */
    private void filterTasksByType() {
        if (noProjectsExist()) {
            System.out.println("\nThere are no saved projects to filter tasks from.");
            return;
        }

        // Prompt for task type
        char type = ' ';
        boolean validType = false;
        do {
            System.out.print("\nEnter task type to filter by (A = Admin, S = Support, L = Logistics): ");
            String input = scannerInput.nextLine().trim().toUpperCase();
            if (input.length() == 1 && "ASL".contains(input)) {
                type = input.charAt(0);
                validType = true;
            } else {
                System.out.println("Invalid input. Please enter a single character: A, S, or L.");
            }
        } while (!validType);

        boolean foundType = false;
        System.out.println("\nMatching tasks:");

        for (Project p : projects) {
            if (p != null) {
                for (Task t : p.getTasks()) {
                    if (t != null && t.getTaskType() == type) {
                        foundType = true;
                        String status = t.isCompleted() ? "Completed" : "Incomplete";
                        System.out.println("- Project: " + p.getProjectName() +
                                        " | Task ID: " + t.getTaskId() +
                                        " | Desc: " + t.getDescription() +
                                        " | Duration: " + t.getTaskDuration() + "h" +
                                        " | Status: " + status);
                    }
                }
            }
        }

        if (!foundType) {
            System.out.println("No tasks of type " + type + " were found.");
        }
    }
// -------------------------------------------------------------------------
// DISPLAY PROJECT SUMMARY
// -------------------------------------------------------------------------

/**
 * Displays a summary report of task durations across all saved projects.
 *
 * Report includes:
 * - Average duration of each task type (Admin, Support, Logistics) across all projects.
 * - A breakdown of task durations by individual project.
 */
    private void displayProjectSummary() {
        if (noProjectsExist()) {
            System.out.println("No created tasks to report.");
            return;
        }

        // Display combined averages across all projects
        displayAverageDurationsAcrossAllProjects(projects);

        // Display task breakdowns per individual project
        for (Project p : projects) {
            if (p != null) {
                displayTaskDurationBreakdown(p);
            }
        }
    }
// -------------------------------------------------------------------------
// LOAD PROJECTS FROM FILE
// -------------------------------------------------------------------------

/**
 * Loads projects and tasks from an external file (e.g., ProjectData.txt).
 * Accepts mixed, out-of-order, or malformed input with graceful error handling.
 *
 * Expected project line format: ID,Name,Type
 * Expected task line format: ID,Type,Duration,Completed
 */
private void loadFromFile() {
    // Load file
    System.out.print("Enter filename to load from (e.g., ProjectData.txt): ");
    String filename = scannerInput.nextLine().trim();

    // Scanner to read file
    try (Scanner fileScanner = new Scanner(new File(filename))) {
        Project[] loadedProjects = new Project[10];
        int projectIndex = -1;
        Project currentProject = null;

        while (fileScanner.hasNextLine()) {
            // Read each line and trime whitespace
            String line = fileScanner.nextLine().trim();
            // Splits line into tokens at the commas
            String[] tokens = line.split(",");

            // Skip blank lines
            if (tokens.length == 0 || line.isEmpty()) continue;

            try {
                // Lines with only 3 tokens are Projects
                if (tokens.length == 3) {
                    // Checks the projectID is valid
                    int projectId = Integer.parseInt(tokens[0].trim());
                    if (projectId < 1 || projectId > 999) {
                        System.out.println("[WARNING] Invalid project ID: " + projectId + ". Skipping line: " + line);
                        continue;
                    }


                    String name = tokens[1].trim();
                    String type = tokens[2].trim();

                    // Limits to 10 projects
                    if (++projectIndex >= 10) {
                        System.out.println("[WARNING] Max project limit reached. Skipping extra project: " + line);
                    }

                    // Validate project type
                    if (!type.equals("Small") && !type.equals("Medium") && !type.equals("Large")) {
                        System.out.println("[WARNING] Invalid project type: " + type + ". Skipping line: " + line);
                        continue;
                    }


                    currentProject = new Project();
                    currentProject.setProjectId(projectId);
                    currentProject.setProjectName(name);
                    currentProject.setProjectType(type);
                    loadedProjects[projectIndex] = currentProject;


                    // If there are 5 tokens, it's a task, and it belongs to the most recent project above.
                } else if (tokens.length == 5 && currentProject != null) {
                    // Task line: ID, Description, Type, Duration, Completed
                    if (!projectHasRoomForTask(currentProject)) {
                        System.out.println("[WARNING] Project ID " + currentProject.getProjectId() + " full. Skipping task: " + line);
                        continue;
                    }

                    int taskId = Integer.parseInt(tokens[0].trim());
                    String description = tokens[1].trim();
                    char taskType = tokens[2].trim().toUpperCase().charAt(0);
                    int duration = Integer.parseInt(tokens[3].trim());
                    boolean completed = Boolean.parseBoolean(tokens[4].trim().toLowerCase());
                    
                    // Checks for empty description
                    if (description.isEmpty()) {
                        System.out.println("[WARNING] Task description is empty: Skipping line: " +line);
                        continue;
                    }

                    // Checks for valid task type
                    if (taskType != 'A' && taskType != 'S' && taskType != 'L') {
                        System.out.println("[WARNING] Invalid task type: " + taskType + ". Skipping: " + line);
                        continue;
                    }

                    // Checks for valid duration
                    if (duration <= 0 || duration > 100) {
                        System.out.println("[WARNING] Invalid task duration : " + duration + ". Skipping: " + line);
                        continue;
                    }

                    // Checks for completed boolean
                    if (!projectHasRoomForTask(currentProject)) {
                        System.out.println("[WARNING] Project ID " + currentProject.getProjectId() + " full. Skipping task: " +line);
                        continue;
                    }

                    Task task = new Task();
                    task.setTaskId(taskId);
                    task.setDescription(description);
                    task.setTaskType(taskType);
                    task.setTaskDuration(duration);
                    task.setCompleted(completed);

                    for (int i = 0; i < currentProject.getTasks().length; i++) {
                        if (currentProject.getTasks()[i] == null) {
                            currentProject.getTasks()[i] = task;
                            break;
                        }
                    }

                } else {
                    System.out.println("[WARNING] Malformed line or out-of-place task: " + line);
                }

            } catch (Exception e) {
                System.out.println("[ERROR] Failed to parse line: " + line + " — " + e.getMessage());
            }
        }

        // Load parsed projects into main array
        for (int i = 0; i < projects.length; i++) {
            projects[i] = (i <= projectIndex) ? loadedProjects[i] : null;
        }

        System.out.println("Projects loaded successfully.");

    // Catch for no file found
    } catch (FileNotFoundException e) {
        System.out.println("[ERROR] File not found: " + filename);
    }
}
// -------------------------------------------------------------------------
// SAVE PROJECTS TO FILE
// -------------------------------------------------------------------------

/**
 * Saves all current projects and their tasks to an external text file.
 *
 * Output format matches the assignment spec:
 * - Project line: projectId,projectName,projectType
 * - Followed by task lines: taskId,description,taskType,duration,completed
 *
 * This method:
 * - Iterates through all non-null projects
 * - Writes each project and its associated tasks to the file
 * - Handles exceptions to ensure data integrity
 */
    private void saveToFile() {
        System.out.print("Enter filename to save to (e.g., ProjectData.txt): ");
        String filename = scannerInput.nextLine().trim();

        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            for (Project p : projects) {
                if (p != null) {
                    // Write project line
                    writer.println(p.getProjectId() + "," + p.getProjectName() + "," + p.getProjectType());

                    // Write task lines
                    for (Task t : p.getTasks()) {
                        if (t != null) {
                            writer.println(t.getTaskId() + "," +
                                        t.getDescription() + "," +
                                        t.getTaskType() + "," +
                                        t.getTaskDuration() + "," +
                                        t.isCompleted());
                        }
                    }
                }
            }
            System.out.println("Projects saved successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

// -------------------------------------------------------------------------
// HELPER METHOD 1: Check if No Projects Exist
// -------------------------------------------------------------------------

/**
 * Checks if all project slots in the array are currently empty.
 * This is useful for preventing actions like displaying or removing projects when none exist.
 * 
 * @return true if all project slots are null (i.e. no projects exist), false otherwise.
 */
private boolean noProjectsExist() {
    for (Project p : projects) {
        if (p != null) return false;
    }
    return true;
}


// -------------------------------------------------------------------------
// HELPER METHOD 2: Check for Available Project Slot
// -------------------------------------------------------------------------
/**
 * Scans the projects array to determine if there is an empty slot available.
 * Used before allowing a new project to be created, since the system only allows up to three projects.
 *
 * @return true if at least one project slot is null (i.e. available), false otherwise.
 */
private boolean hasAvailableProjectSlot() {
    for (Project p : projects) {
        if (p == null) {
            return true;
        }
    }
    return false;
}
    
// -------------------------------------------------------------------------
// HELPER METHOD 3: Prompt for Valid and Unique Project ID
// -------------------------------------------------------------------------

/**
 * Prompts the user to enter a valid project ID between 1 and 999.
 * Ensures the input is an integer within the valid range and that the ID
 * is not already in use by an existing project.
 * 
 * Re-prompts the user for input until a valid and unique ID is provided.
 *
 * @param scannerInput The Scanner object used to read user input.
 * @return A validated and unique project ID.
 */
private int promptValidProjectId(Scanner scannerInput, boolean allowCancel) {
    int id = 0;
    boolean validProjectId = false;
    
    do {
        System.out.print("\nEnter Project ID (1-999) or -1 to cancel: ");
        String input = scannerInput.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("Input cannot be empty");
            continue;
        }
        
        try {
            id = Integer.parseInt(input);

            if (allowCancel && id == -1) {
                return -1;
            }

            if (id >= 1 && id <= 999) {
                validProjectId = true;
            } else {
                System.out.println("Project ID must be a number between 1 and 999.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 999.");
        }
        
    } while (!validProjectId);
    
    return id;
}

// -------------------------------------------------------------------------
// HELPER METHOD 4: Prompt for Existing Project ID 
// -------------------------------------------------------------------------

/**
 * Prompts the user to enter a project ID for an existing project.
 * Accepts only numeric input and allows the user to enter -1 to cancel.
 * Ensures the input is not empty and is a valid integer.
 * 
 * Loops until a valid integer is entered.
 * 
 * @param scannerInput The Scanner object used for reading user input.
 * @param promptLabel The custom prompt message to display to the user.
 * @return A valid integer project ID, or -1 if the user chooses to cancel. 
 */
private int promptExistingProjectId(Scanner scannerInput, String promptLabel) {
    int projectId = -1;
    boolean validInput = false;

    do {
        System.out.print("\n" + promptLabel);

        if (scannerInput.hasNextLine()) {
            String input = scannerInput.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }

            try {
                projectId = Integer.parseInt(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number (between 1-999).");
            }
        }
    } while (!validInput);

    return projectId;
}
// -------------------------------------------------------------------------
// HELPER METHOD 5: Display Existing Projects
// -------------------------------------------------------------------------

/**
 * Displays a list of all currently saved projects.
 * Useful for user reference before selecting a project by ID.
 * Skips over any null project slots.
 */
private void displayExistingProjects() {
    System.out.println("\nExisting Projects: ");
    for (Project p : projects) {
        if (p != null) {
            System.out.println("- ID: " + p.getProjectId() + " | Name: " + p.getProjectName());
        }
    }
}

// -------------------------------------------------------------------------
// HELPER METHOD 6: Display Task Limit for Project Type
// -------------------------------------------------------------------------

/**
 * Displays how many tasks are allowed based on the given project type.
 * Useful for informing the user before adding a new task.
 *
 * @param type The project type ("Small", "Medium", or "Large").
 */
private void displayTaskLimitByProjectType(String type) {
    switch (type) {
        case "Small":
            System.out.println("This project allows only 1 task.");
            break;
        case "Medium":
            System.out.println("This project allows up to 2 tasks.");
            break;
        case "Large":
            System.out.println("This project allows up to 3 tasks.");
            break;
        default:
            System.out.println("Unknown project type.");
    }
}

// -------------------------------------------------------------------------
// HELPER METHOD 7: Check if Project Has Room for Another Task
// -------------------------------------------------------------------------

/**
 * Checks if the given project can accept more tasks based on its type.
 * Small projects can have 1 task, Medium 2, and Large 3.
 *
 * @param project The project to check.
 * @return true if the project has space for another task, false otherwise.
 */
private boolean projectHasRoomForTask(Project project) {
    int taskLimit = 0;
    switch (project.getProjectType()) {
        case "Small":
            taskLimit = 1;
            break;
        case "Medium":
            taskLimit = 2;
            break;
        case "Large":
            taskLimit = 3;
            break;
    }

    int currentTasks = 0;
    for (Task t : project.getTasks()) {
        if (t != null) currentTasks++;
    }

    return currentTasks < taskLimit;
}

// -------------------------------------------------------------------------
// HELPER METHOD 8: Prompt for Valid Task ID
// -------------------------------------------------------------------------

/**
 * Prompts the user to enter a valid task ID between 1 and 9.
 * Ensures the input is an integer within range.
 * 
 * Loops until a valid ID is provided. Does not check for uniqueness.
 *
 * @param scannerInput The Scanner object used to read user input.
 * @return A validated integer task ID between 1 and 9.
 */
private int promptValidTaskId(Scanner scannerInput) {
    int taskId = 0;
    boolean validInput = false;

    do {
        System.out.print("\nEnter Task ID (1-99): ");
        if (scannerInput.hasNextLine()) {
            String input = scannerInput.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }

            try {
                taskId = Integer.parseInt(input);
                if (taskId >= 1 && taskId <= 99) {
                    validInput = true;
                } else {
                    System.out.println("Task ID must be between 1 and 99.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 99.");
            }
        }
    } while (!validInput);

    return taskId;
}


// -------------------------------------------------------------------------
// HELPER METHOD 9: Prompt and Return Valid Project Object by ID (Custom Prompt)
// -------------------------------------------------------------------------

/**
 * Prompts the user to select an existing project by entering its ID.
 * Loops until a valid project is selected or the user cancels by entering -1.
 *
 * @param scannerInput Scanner object to read user input
 * @param promptLabel Custom message to display for the prompt
 * @return The selected Project object, or null if cancelled
 */
private Project selectProjectById(Scanner scannerInput, String promptLabel) {
    while (true) {
        int selectedId = promptExistingProjectId(scannerInput, promptLabel);

        if (selectedId == -1) {
            return null;  // user cancelled
        }

        for (Project p : projects) {
            if (p != null && p.getProjectId() == selectedId) {
                return p;
            }
        }

        System.out.println("No project found with ID: " + selectedId + ". Please try again or enter -1 to cancel.");
    }
}

// -------------------------------------------------------------------------
// HELPER METHOD 10: Select Task by ID from a Project
// -------------------------------------------------------------------------

/**
 * Prompts the user to enter a Task ID to select a task from the given project.
 * Validates that the task exists. Entering -1 cancels the operation.
 *
 * @param project The project containing the tasks.
 * @param scannerInput The Scanner object for user input.
 * @return The selected Task object, or null if cancelled or not found.
 */
private Task selectTaskById(Project project, Scanner scannerInput) {
    while (true) {
        System.out.print("\nEnter the Task ID to select (or -1 to cancel): ");
        String input = scannerInput.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("Input cannot be empty.");
            continue;
        }

        try {
            int taskId = Integer.parseInt(input);

            if (taskId == -1) {
                return null; // User cancelled
            }

            for (Task t : project.getTasks()) {
                if (t != null && t.getTaskId() == taskId) {
                    return t;
                }
            }

            System.out.println("No task found with ID: " + taskId + ". Please try again or enter -1 to cancel.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
}

// -------------------------------------------------------------------------
// HELPER METHOD 11: Display Tasks for a Given Project
// -------------------------------------------------------------------------

/**
 * Displays all tasks within a given project.
 * Skips over any null task slots.
 *
 * @param project The project whose tasks should be displayed.
 */
private void displayTasksForProject(Project project) {
    System.out.println("\nTasks in project: " + project.getProjectName());
    boolean hasTasks = false;

    for (Task t : project.getTasks()) {
        if (t != null) {
            hasTasks = true;
            String status = t.isCompleted() ? "Completed" : "Incomplete";
            System.out.println("- Task ID: " + t.getTaskId() +
                               " | Desc: " + t.getDescription() +
                               " | Type: " + t.getTaskType() +
                               " | Duration: " + t.getTaskDuration() + "h" +
                               " | Status: " + status);
        }
    }

    if (!hasTasks) {
        System.out.println("This project has no tasks.");
    }
}

// -------------------------------------------------------------------------
// HELPER METHOD 12: Display Average Durations Across All Projects
// -------------------------------------------------------------------------

/**
 * Calculates and displays the average duration for each task type (Admin, Support, Logistics)
 * across all saved projects.
 *
 * @param projects An array of all saved projects
 */
private void displayAverageDurationsAcrossAllProjects(Project[] projects) {
    int totalAdmin = 0, countAdmin = 0;
    int totalSupport = 0, countSupport = 0;
    int totalLogistics = 0, countLogistics = 0;

    // Loop through all projects and their tasks
    for (Project p : projects) {
        if (p != null) {
            for (Task t : p.getTasks()) {
                if (t != null) {
                    switch (t.getTaskType()) {
                        case 'A':
                            totalAdmin += t.getTaskDuration();
                            countAdmin++;
                            break;
                        case 'S':
                            totalSupport += t.getTaskDuration();
                            countSupport++;
                            break;
                        case 'L':
                            totalLogistics += t.getTaskDuration();
                            countLogistics++;
                            break;
                    }
                }
            }
        }
    }

    System.out.println("\n------------------------ Average Task Duration ------------------\n");

    // Display average for Admin tasks
    if (countAdmin > 0) {
        System.out.println("* Average task duration of administrative tasks is " + (totalAdmin / countAdmin) + " hours\n");
    } else {
        System.out.println("* No administrative tasks found.\n");
    }

    // Display average for Support tasks
    if (countSupport > 0) {
        System.out.println("* Average task duration of support tasks is " + (totalSupport / countSupport) + " hours\n");
    } else {
        System.out.println("* No support tasks found.\n");
    }

    // Display average for Logistics tasks
    if (countLogistics > 0) {
        System.out.println("* Average task duration of logistics tasks is " + (totalLogistics / countLogistics) + " hours\n");
    } else {
        System.out.println("* No logistics tasks found.\n");
    }
}

// -------------------------------------------------------------------------
// HELPER METHOD 13: Display Task Duration Breakdown for a Single Project
// -------------------------------------------------------------------------

/**
 * Calculates and displays the average duration for each task type (Admin, Support, Logistics)
 * within a single project.
 *
 * @param p The project to analyze
 */
private void displayTaskDurationBreakdown(Project p) {
    int admin = 0, countA = 0;
    int support = 0, countS = 0;
    int logistics = 0, countL = 0;

    // Loop through each task in the project and tally durations by type
    for (Task t : p.getTasks()) {
        if (t != null) {
            switch (t.getTaskType()) {
                case 'A':
                    admin += t.getTaskDuration();
                    countA++;
                    break;
                case 'S':
                    support += t.getTaskDuration();
                    countS++;
                    break;
                case 'L':
                    logistics += t.getTaskDuration();
                    countL++;
                    break;
            }
        }
    }

    System.out.println("\n---------------- Project " + p.getProjectId() + " ----------------\n");

    // Display Admin average
    if (countA > 0) {
        System.out.println("* Admin average: " + (admin / countA) + " hours\n");
    } else {
        System.out.println("* No admin tasks found.\n");
    }

    // Display Support average
    if (countS > 0) {
        System.out.println("* Support average: " + (support / countS) + " hours\n");
    } else {
        System.out.println("* No support tasks found.\n");
    }

    // Display Logistics average
    if (countL > 0) {
        System.out.println("* Logistics average: " + (logistics / countL) + " hours\n");
    } else {
        System.out.println("* No logistics tasks found.\n");
    }
}

// -------------------------------------------------------------------------
// HELPER METHOD 14: Generate Unique Project ID
// -------------------------------------------------------------------------

/**
 * Generates a random unique project ID between 1 and 999 that is not already
 * used by any existing project in the system.
 *
 * @return A unique project ID not currently used.
 */
private int generateUniqueProjectId() {
    Random rand = new Random();
    int id;

    do {
        id = rand.nextInt(999) + 1; // generates number between 1 and 999
    } while (isProjectIdTaken(id));

    return id;
}

/**
 * Checks if the given project ID is already used by an existing project.
 *
 * @param id The project ID to check.
 * @return true if the ID is taken, false otherwise.
 */
private boolean isProjectIdTaken(int id) {
    for (Project p : projects) {
        if (p != null && p.getProjectId() == id) {
            return true;
        }
    }
    return false;
}

// -------------------------------------------------------------------------
// HELPER METHOD 15: Generate Unique Task ID Within a Project
// -------------------------------------------------------------------------

/**
 * Generates a random unique task ID between 1 and 99 that is not already
 * used by any existing task in the given project.
 *
 * @param project The project to generate a unique task ID for.
 * @return A unique task ID not currently used in that project.
 */
private int generateUniqueTaskId(Project project) {
    Random rand = new Random();
    int id;
    
    do {
        id = rand.nextInt(99) + 1; // generates number between 1 and 99
    } while (isTaskIdTakenInProject(project, id));
    
    return id;
}

/**
 * Checks if the given task ID already exists in the given project.
 *
 * @param project The project to check.
 * @param id The task ID to check.
 * @return true if the ID is taken, false otherwise.
 */
private boolean isTaskIdTakenInProject(Project project, int id) {
    for (Task t : project.getTasks()) {
        if (t != null && t.getTaskId() == id) {
            return true;
        }
    }
    return false;
}


} // end of UserInterface