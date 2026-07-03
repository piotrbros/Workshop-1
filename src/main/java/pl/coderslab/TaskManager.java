package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        final String[] OPTIONS = {"add", "remove", "list", "exit"};
        final Path PATH = Paths.get("tasks.csv");
        Scanner scanner = new Scanner(System.in);
        String[][] tasks;
        try {
            tasks = readTasks(PATH);
        } catch (IOException e) {
            System.out.println("Cannot open " + PATH.getFileName());
            return;
        }

        while (true) {
            viewOptions(OPTIONS);
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "exit":
                    exitProgram(PATH, tasks);
                    return;
                case "list":
                    listTasks(tasks);
                    break;
                case "add":
                    tasks = addTask(tasks);
                    break;
                case "remove":
                    tasks = removeTask(tasks);

                default:
                    System.out.println("Please select a correct option.");
            }
        }


    }

    public static void writeToFile(Path path, List<String> data) {
        try {
            Files.write(path, data);
        } catch (IOException e) {
            System.out.println("Cannot write to file. Error: " + e);
        }
    }


    public static List<String> createListToWrite(String[][] tasks) {
        List<String> rows = new ArrayList<>();

        for (String[] row : tasks) {
            rows.add(String.join(",", row));
        }
        return rows;
    }

    public static String[][] removeTask(String[][] tasks) {
        System.out.println("remove");
        int indexToRemove = getIndexToRemove(tasks.length);
        // Removing task with an index provided by user
        return ArrayUtils.remove(tasks, indexToRemove);


    }

    public static int getIndexToRemove(int tasksLength) {
        // Validate and get user input
        Scanner scanner = new Scanner(System.in);
        int maxIndex = tasksLength - 1;
        int index;
        String inputError = "Incorrect argument passed. Please give a number between 0 and " + (maxIndex);

        System.out.println("Please select a number to remove.");
        while (true) {
            try {
                index = Integer.parseInt(scanner.nextLine());
                if (index >= 0 && index <= maxIndex) {
                    return index;
                }
            } catch (NumberFormatException e) {
                System.out.println(inputError);
                continue;
            }
            System.out.println(inputError);
        }
    }


    // This method adds new task to table and returns a new table with updated task list
    public static String[][] addTask(String[][] tasks) {
        System.out.println("add");

        // Get info about new task from the user.
        String[] newTask = getNewTask();

        // Preparing a copy array with + 1 length to accommodate user input
        String[][] newTasks = Arrays.copyOf(tasks, tasks.length + 1);

        // Saving userInput into the copied array.
        newTasks[tasks.length] = newTask;

        //Returning final newTasks
        return newTasks;
    }

    // This method gets new task info from the user, ensures proper data format and returns String[].
    public static String[] getNewTask() {
        String[] userInputDescription = {
                "Please add task description",
                "Please add task due date",
                "Is your task important: true/false"
        };
        String[] userInput = new String[userInputDescription.length];
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < userInput.length; i++) {
            // Prompting the user for data
            System.out.println(userInputDescription[i]);

            // Collecting data from user input
            while (true) {
                String input = scanner.nextLine();

                if (input.contains(",")) {
                    System.out.println("Please dont use \",\" in the input.");
                    continue;
                }

                userInput[i] = input;
                break;
            }
        }
        return userInput;
    }

    public static void listTasks(String[][] tasks) {
        System.out.println("list");

        if (tasks.length == 0) {
            System.out.println("Your task list is empty!");
        }

        StringBuilder buffer = new StringBuilder();

        // For loop that creates every row of the output
        for (int i = 0; i < tasks.length; i++) {
            buffer.append(i);
            buffer.append(" : ");
            // Inner loop that iterates over all columns in the row
            for (String column : tasks[i]) {
                buffer.append(column);
                buffer.append(" ");
            }
            // Finishing row creation
            buffer.append("\n");
        }
        // Printing the whole buffer
        System.out.println(buffer);
    }

    public static void exitProgram(Path path, String[][] tasks) {
        //Save file method below TO ADD
        System.out.println("exit");
        writeToFile(path, createListToWrite(tasks));
        System.out.println(ConsoleColors.RED + "Bye, bye.");
        return;
    }

    public static void viewOptions(String[] optionsList) {
        System.out.println();
        System.out.println(ConsoleColors.BLUE + "Please select an option:");

        for (String option : optionsList) {
            System.out.println(ConsoleColors.RESET + option);
        }
    }

    public static String[][] readTasks(Path path) throws IOException {
        String[][] data;

        // Opening the tasks.csv file
        List<String> rows = Files.readAllLines(path);

        data = new String[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i).split(",");
        }
        return data;
    }
}
