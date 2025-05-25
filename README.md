# SENG1110 – Assignment 2 Refactor Plan & Progress

## Core Structure
- [ ] Created fresh repo and folder structure
- [ ] All `.java` files inside `src/`
- [ ] `.gitignore` blocks `.class` files and build artifacts

## Clean-up from Assignment 1
- [ ] Moved all task-related logic out of `UserInterface` into `Project`
- [ ] Removed hardcoded seed data; now loaded from file
- [ ] Replaced copy-pasted logic with reusable helper methods
- [ ] Improved clarity and structure of menu system

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
