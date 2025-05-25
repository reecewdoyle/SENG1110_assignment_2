/**
 * UserInterface class handles all user interactions for the Project Management System
 * Provides a menu-driven interface for managing projects and tasks
 */
import java.util.Scanner;

public class UserInterface {
    // Attributes
    private Project project1;
    private Project project2;
    private Project project3;
    private Scanner scannerInput;
    

    public static void main(String[] args) {
        new UserInterface().run();
       
    }
    
    public void run() {
      project1 = null;
      project2 = null;
      project3 = null;

      scannerInput = new Scanner(System.in);
      Project p = new Project();
      System.out.print("Enter Project ID: ");
      int id = scannerInput.nextInt();
      p.setProjectId(id);
      System.out.println("Project ID " + p.getProjectId());
      
      System.out.print("Enter Project Name: ");
      String projectName = scannerInput.nextLine();
      p.setProjectName(projectName);
      System.out.println("Project Name " + p.getProjectName());

      System.out.print("Enter Project Type: ");
      String projectType = scannerInput.nextLine();
      p.setProjectType(projectType);
      System.out.println("Project Type " + p.getProjectType());
    }

		// Menu logic here

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
      return scannerInput.nextInt();

      int choice;
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
          case -1: System.out.println("Thank you for using Project Managment System. Goodbye!"); break;
          default: System.out.println("Invalid choice. Please try again.");
        }
      } while (choice != -1);
    }

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
}