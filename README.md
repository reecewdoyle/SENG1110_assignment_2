
For Assignment 2, I've created a new repo and I'm keeping the `Project` and `Task` classes from Assignment 1, since they still do the job with just a few tweaks. But I've decided to completely rebuild `UserInterface.java` from scratch. Refactoring the old one would've taken more time than just writing it again in a cleaner, more structured way - using arrays, helper methods, and the new stuff we've covered since the break like file I/O and exception handling.

## UserInterface Rebuild

The `UserInterface` class in Assignment 1 got the job done, but it ended up pretty bulky and hard to manage, with most of the logic crammed into one place. For Assignment 2, I've decided to start fresh so I can build things more cleanly - using arrays, helper methods, and better separation of responsibilities across classes.

Progress below is tracked by menu option:

- [x] `1. Create a new project` - Prompt for project details, store in first available slot
- [x] `2. Remove a project` - Find by ID, confirm, null the slot
- [x] `3. Add a task to a project` - Prompt for task details, validate and add via `addTask()`
- [x] `4. Mark a task as completed` - Find task by ID and update completed status
- [x] `5. Remove a task from a project` - Locate and null out the matching task slot
- [x] `6. Display all project details` - Loop through projects, print info + all tasks
- [x] `7. Display completed tasks` - Filter and show only completed tasks across all projects
- [x] `8. Filter tasks by type` - Prompt for A/L/S, display matching tasks only
- [x] `9. Display project summary` - Show ID, name, type, task count, total duration, etc.
- [ ] `10. Load from file` - Parse ProjectData.txt into structured objects
- [ ] `11. Save to file` - Write all data in assignment format to ProjectData.txt
- [x] `-1. Exit` - Terminate program cleanly with goodbye message

## Core Structure
- [x] Created fresh repo and folder structure
- [x] All `.java` files inside `src/`
- [x] `.gitignore` blocks `.class` files and build artifacts

## Project & Task Management
- [x] `Project[]` stores up to 10 projects
- [x] Each `Project` stores tasks in a `Task[]`, size depends on type (Small, Medium, Large)
- [x] Prevent duplicate IDs for projects and tasks
- [x] Auto-generate random unique ID if duplicate is entered

## Helper Methods & Refactoring
- [x] Refactored long blocks of code into clear helper methods
- [x] Examples: `getProjectById()`, `addTaskToProject()`, `displayTasks()`, etc.
- [x] Avoided deeply nested code, used early returns and clean loops

## File I/O
- [x] Implemented `loadFromFile(String filename)` using `Scanner`
- [x] Implemented `saveToFile(String filename)` using `PrintWriter`
- [x] Follows exact format given in `ProjectData.txt`
- [x] Fixed the `ProjectData.txt` error where it doesn't get description
- [x] All file reading/writing wrapped in exception handling

## Exception Handling
- [x] Wrapped `Scanner.nextInt()` and similar calls in `try-catch`
- [x] Handled `InputMismatchException`, `NumberFormatException`, `FileNotFoundException`
- [x] All errors produce meaningful output without crashing the program

## Display & Filtering
- [x]  Menu options display task summaries, completed tasks, and project info
- [x] Filter tasks by type (Admin, Lab, Study)
- [x] Gracefully handle empty lists, null slots, or invalid filters

## Report
- [ ] Compared Assignment 1 vs Assignment 2 code structure
- [ ] Included screenshots of each working feature
- [ ] Explained how inheritance and polymorphism *could* apply to Task hierarchy
- [ ] Report written without AI assistance (as per assignment rules)

## Testing
- [x] Validated task limits per project type (1, 2, or 3)
- [x] Verified duplicate ID logic with fallback random generation
- [x] Tested file load/save with provided `ProjectData.txt`
- [x] Checked for all expected error messages and recovery flows


## Assignment 2 Specifications
### Task Class
### Attributes
- [x] taskId (int)
- [x] description (String)
- [x] completed (boolean)
- [x] (char) (A,S,L)
- [x] taskDuration (int)

### Project Class
### Attributes
- [x] projectId (int)
- [x] projectName (String)
- [x] task (Task[]) An array of Task objects. Size determined by project type
- [x] projectType (String) (Small, Medium, Large)

### User Interface Class
### Attributes
- [x] Projects[] - Array with a max of 10 projects
- [x] scannerInput (Scanner) - to handle euser input

### Functionality Requirements
### 

## Assignment 2 Feature Checklist

### Project Management
- [x] Supports **up to 10 projects** in the `projects` array.
- [x] Displays an **error if trying to create a project when full**.
- [x] Ensures **unique `projectId`** across all projects.
- [x] If a **duplicate `projectId`** is entered, a **random unique ID** is assigned automatically.
- [x] Accepts and normalises **case-insensitive `projectType`** input (Small, Medium, Large).

### Task Management
- [x] Adds tasks to a specific project's **`tasks` array**.
- [x] Ensures **unique `taskId` within the project**.
- [x] If a **duplicate `taskId`** is entered, a **random unique ID** is assigned automatically.
- [x] Accepts and normalises **case-insensitive `taskType`** input (A, S, L).
- [x] Prevents adding tasks **if the project is at max capacity** (based on type).

### Deletion
- [x] Allows **removing a project by `projectId`**, with error handling for invalid IDs.
- [x] Allows **removing tasks from a project’s `tasks` array** by ID.

### Display Features
- [x] **Displays all project details** (ID, name, type, tasks).
- [x] **Displays completed tasks** for a selected project.
- [x] **Filters tasks by type** (A, S, L) across all projects.

### Task Duration Summary
- [x] Displays **average duration for each task type** across all projects.
- [x] Displays **task duration breakdown per individual project**.
- [x] Shows appropriate **messages if no projects or tasks exist**.