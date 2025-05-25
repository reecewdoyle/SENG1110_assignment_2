/**
 * UserInterface class handles all user interactions for the Project Management System
 * Provides a menu-driven interface for managing projects and tasks
 */
import java.util.Scanner;

public class UserInterface {
    
    // -------------------------------------------------------------------------
    // Instance Variables
    // -------------------------------------------------------------------------
    
    private Project project1;
    private Project project2;
    private Project project3;
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
      project1 = null;
      project2 = null;
      project3 = null;

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
          System.out.println("Enter your choice: ");
            
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
        if (project1 == null || project2 == null || project3 == null) {
        Project p = new Project();
        // Collect projectId, name, type from user
        // Normalise projectType (e.g., "basic" -> "Basic")
        if (project1 == null) project1 = p;
        else if (project2 == null) project2 = p;
        else if (project3 == null) project3 = p;
        } else {
        System.out.println("Maximum projects reached!");
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
        System.out.println("removeProject() not implemented yet.");
    }

// -------------------------------------------------------------------------
// ADD TASK TO PROJECT
// -------------------------------------------------------------------------

/**
 * Adds a new task to a selected project.
 * Ensures the project exists, has space for tasks, and all inputs are valid.
 */
    private void addTask() {
        System.out.println("addTask() not implemented yet.");
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
}

