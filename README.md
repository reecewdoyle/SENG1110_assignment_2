
For Assignment 2, I've created a new repo and I'm keeping the `Project` and `Task` classes from Assignment 1, since they still do the job with just a few tweaks. But I've decided to completely rebuild `UserInterface.java` from scratch. Refactoring the old one would've taken more time than just writing it again in a cleaner, more structured way - using arrays, helper methods, and the new stuff we've covered since the break like file I/O and exception handling.

## UserInterface Rebuild

The `UserInterface` class in Assignment 1 got the job done, but it ended up pretty bulky and hard to manage, with most of the logic crammed into one place. For Assignment 2, I've decided to start fresh so I can build things more cleanly - using arrays, helper methods, and better separation of responsibilities across classes.

Progress below is tracked by menu option:

- [ ] `1. Create a new project` - Prompt for project details, store in first available slot
- [ ] `2. Remove a project` - Find by ID, confirm, null the slot
- [ ] `3. Add a task to a project` - Prompt for task details, validate and add via `addTask()`
- [ ] `4. Mark a task as completed` - Find task by ID and update completed status
- [ ] `5. Remove a task from a project` - Locate and null out the matching task slot
- [ ] `6. Display all project details` - Loop through projects, print info + all tasks
- [ ] `7. Display completed tasks` - Filter and show only completed tasks across all projects
- [ ] `8. Filter tasks by type` - Prompt for A/L/S, display matching tasks only
- [ ] `9. Display project summary` - Show ID, name, type, task count, total duration, etc.
- [ ] `10. Load from file` - Parse ProjectData.txt into structured objects
- [ ] `11. Save to file` - Write all data in assignment format to ProjectData.txt
- [ ] `-1. Exit` - Terminate program cleanly with goodbye message

## Core Structure
- [ ] Created fresh repo and folder structure
- [ ] All `.java` files inside `src/`
- [ ] `.gitignore` blocks `.class` files and build artifacts

## Project & Task Management
- [ ] `Project[]` stores up to 10 projects
- [ ] Each `Project` stores tasks in a `Task[]`, size depends on type (Small, Medium, Large)
- [ ] Prevent duplicate IDs for projects and tasks
- [ ] Auto-generate random unique ID if duplicate is entered

## Helper Methods & Refactoring
- [ ] Refactored long blocks of code into clear helper methods
- [ ] Examples: `getProjectById()`, `addTaskToProject()`, `displayTasks()`, etc.
- [ ] Avoided deeply nested code, used early returns and clean loops

## File I/O
- [ ] Implemented `loadFromFile(String filename)` using `Scanner`
- [ ] Implemented `saveToFile(String filename)` using `PrintWriter`
- [ ] Follows exact format given in `ProjectData.txt`
- [ ] All file reading/writing wrapped in exception handling

## Exception Handling
- [ ] Wrapped `Scanner.nextInt()` and similar calls in `try-catch`
- [ ] Handled `InputMismatchException`, `NumberFormatException`, `FileNotFoundException`
- [ ] All errors produce meaningful output without crashing the program

## Display & Filtering
- [ ] Menu options display task summaries, completed tasks, and project info
- [ ] Filter tasks by type (Admin, Lab, Study)
- [ ] Gracefully handle empty lists, null slots, or invalid filters

## Report
- [ ] Compared Assignment 1 vs Assignment 2 code structure
- [ ] Included screenshots of each working feature
- [ ] Explained how inheritance and polymorphism *could* apply to Task hierarchy
- [ ] Report written without AI assistance (as per assignment rules)

## Testing
- [ ] Validated task limits per project type (1, 2, or 3)
- [ ] Verified duplicate ID logic with fallback random generation
- [ ] Tested file load/save with provided `ProjectData.txt`
- [ ] Checked for all expected error messages and recovery flows