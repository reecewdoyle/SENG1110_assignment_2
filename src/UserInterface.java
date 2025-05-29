/**
 * UserInterface class handles all user interactions for the Project Management System
 * Provides a menu-driven interface for managing projects and tasks
 */
import java.util.Scanner;

public class UserInterface {
    
    // -------------------------------------------------------------------------
    // Instance Variables
    // -------------------------------------------------------------------------
    
    private Project[] projects = new Project[3];
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
    //   project1 = null;
    //   project2 = null;
    //   project3 = null;
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
    int id = promptValidProjectId(scannerInput);
    p.setProjectId(id);
    System.out.println("\nProject ID " + p.getProjectId());
    
    // ----------------- Project Name Input -----------------
    System.out.print("\nEnter Project Name: ");
    String projectName = scannerInput.nextLine().trim();
    while (projectName.isEmpty()) {
        System.out.println("Project name cannot be empty. Please enter a valid name:");
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
        
        displayExistingProjects();

        while (true) {
            // Prompt for the ID of the project to remove
            int projectIdToRemove = promptExistingProjectId(scannerInput);

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
        Project workProject = selectProjectById(scannerInput);
        if (workProject == null) {
            System.out.println("Task creation cancelled.");
            return;
        }

        // Display the Selected Project
        System.out.println("Selected Project: " + workProject.getProjectName());

        // Display how many tasks this project can have
        displayTaskLimitByProjectType(workProject.getProjectType());
        System.out.println("Project Type: " + workProject.getProjectType());

        // Check to see if there's room to add a task
        if (!projectHasRoomForTask(workProject)) {
            System.out.println("This project already has the maximum number of tasks allowed.");
            return;
        }

        // Task ID input and validation
        int taskId = promptValidTaskId(scannerInput);

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
            System.out.print("\nEnter task duration in hours (positive whole number): ");
            if (scannerInput.hasNextInt()) {
                duration = scannerInput.nextInt();
                scannerInput.nextInt();

                if (duration > 0) {
                    validDuration = true;
                } else {
                    System.out.println("Duration must be greater than 0.");
                }
            } else {
                System.out.println("Invalid input. Please enter the number of hours.");
                scannerInput.next();
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
        System.out.println("markTaskAsCompleted() not implemented yet.");
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
        System.out.println("removeTask() not implemented yet.");
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
        System.out.println("displayProjectDetails() not implemented yet.");
    }
// -------------------------------------------------------------------------
// DISPLAY COMPLETED TASKS
// -------------------------------------------------------------------------

/**
 * Displays only completed tasks for a specific project by projectId.
 * Prompts the user to enter a valid project ID, validates it, and checks the selected project
 * for any tasks marked as completed. If completed tasks exist, they are displayed in a formatted
 * list. If none are found, an appropriate message is shown.
 * Entering -1 returns to the main menu without displaying anything.
 */

    private void displayCompletedTasks() {
        System.out.println("displayCompletedTasks() not implemented yet.");
    }

// -------------------------------------------------------------------------
// FILTER TASKS BY TYPE
// -------------------------------------------------------------------------

/**
 * Prompts the user to enter a task type (A, S, or L),
 * then loops through all saved projects and displays tasks
 * that match the selected type.
 * If no matching tasks are found, a message is displayed.
 */
    private void filterTasksByType() {
        System.out.println("filterTasksByType() not implemented yet.");
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
 *
 * Task types:
 * - A = Admin
 * - S = Support
 * - L = Logistics
 */

    private void displayProjectSummary() {
        System.out.println("displayProjectSummary() not implemented yet.");
    }
// -------------------------------------------------------------------------
// LOAD PROJECTS FROM FILE
// -------------------------------------------------------------------------

/**
 * Loads projects and tasks from an external text file (e.g., ProjectData.txt).
 *
 * Expected file format:
 * - Project line: projectId,projectName,projectType
 * - Task lines (follow immediately): taskId,description,taskType,duration,completed
 * - Repeats for each project
 *
 * This method:
 * - Parses and reconstructs Project and Task objects from the file
 * - Populates the internal project array
 * - Skips invalid entries and handles file-related exceptions
 */
    private void loadFromFile() {
        System.out.println("loadFromFile() not implemented yet.");
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
        System.out.println("saveToFile() not implemented yet.");
    }

// -------------------------------------------------------------------------
// HELPER METHOD: Check if No Projects Exist
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
// HELPER METHOD: Check for Available Project Slot
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
// HELPER METHOD: Prompt for Valid and Unique Project ID
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

private int promptValidProjectId(Scanner scannerInput) {
    int id = 0;
    boolean validProjectId = false;
    
    do {
        System.out.print("\nEnter Project ID (1-999): ");
        
        if (scannerInput.hasNextInt()) {
            id = scannerInput.nextInt();
            scannerInput.nextLine(); // clear newline
            
            if (id >= 1 && id <= 999) {
                boolean duplicate = false;
                for (Project p : projects) {
                    if (p != null && p.getProjectId() == id) {
                        duplicate = true;
                        break;
                    }
                }
                if (duplicate) {
                    System.out.println("Project ID already exists.");
                } else {
                    validProjectId = true;
                }
            } else {
                System.out.println("Project ID must be between 1 and 999.");
            }
        } else {
            System.out.println("Invalid input. Please enter a number between 1 and 999.");
            scannerInput.next(); // clear invalid token
        }
        
    } while (!validProjectId);
    
    return id;
}

// -------------------------------------------------------------------------
// HELPER METHOD: Prompt for Existing Project ID
// -------------------------------------------------------------------------

/**
 * Prompts the user to enter a project ID for an existing project.
 * Accepts only numeric input and allows the user to enter -1 to cancel.
 * Ensures the input is not empty and is a valid integer.
 * 
 * Loops until a valid integer is entered.
 * 
 * @param scannerInput The Scanner object used for reading user input.
 * @return A valid integar project ID, or -1 if the user chooses to cancel. 
*/
private int promptExistingProjectId(Scanner scannerInput) {
    int projectId = -1;
    boolean validInput = false;

    do {
        System.out.print("\nEnter the Project ID to remove (or -1 to cancel): ");

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
// HELPER METHOD: Display Existing Projects
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
// HELPER METHOD: Display Task Limit for Project Type
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
// HELPER METHOD: Check if Project Has Room for Another Task
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
// HELPER METHOD: Prompt for Valid Task ID
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
        System.out.print("\nEnter Task ID (1-9): ");
        if (scannerInput.hasNextLine()) {
            String input = scannerInput.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Input cannot be empty.");
                continue;
            }

            try {
                taskId = Integer.parseInt(input);
                if (taskId >= 1 && taskId <= 9) {
                    validInput = true;
                } else {
                    System.out.println("Task ID must be between 1 and 9.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
            }
        }
    } while (!validInput);

    return taskId;
}


// -------------------------------------------------------------------------
// HELPER METHOD: Prompt and Return Valid Project Object by ID
// -------------------------------------------------------------------------

/**
 * Prompts the user to select an existing project by entering its ID.
 * Loops until a valid project is selected or the user cancels by entering -1.
 *
 * @param scannerInput Scanner object to read user input
 * @return The selected Project object, or null if cancelled
 */
private Project selectProjectById(Scanner scannerInput) {
    while (true) {
        int selectedId = promptExistingProjectId(scannerInput);

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

} // end of UserInterface