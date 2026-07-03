package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        String[] options = {"add", "remove", "list", "exit"};
        Scanner scanner = new Scanner(System.in);
        String[][] tasks = openTasks();

        while (true) {
            viewOptions(options);
            String userInput = scanner.nextLine();

            switch (userInput) {
                case "exit":
                    exitProgram();
                    return;
                case "list":
                    listTasks(tasks);
                    break;
                // other options
                default:
                    System.out.println("Please select a correct option.");
            }
        }






    }

    public static void listTasks(String[][] tasks) {
        System.out.println("list");
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < tasks.length; i++) {
            buffer.append(i);
            buffer.append(" : ");
            for (String column : tasks[i]) {
                buffer.append(column);
                buffer.append(" ");
            }
            buffer.append("\n");
        }
        System.out.println(buffer);
    }

    public static void exitProgram() {
        //Save file method below TO ADD
        System.out.println("exit");
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

    public static String[][] openTasks() {
        Path path = Paths.get("tasks.csv");
        String[][] data;

        // Opening the tasks.csv file
        try {
            List<String> rows = Files.readAllLines(path);

            data = new String[rows.size()][];
            for (int i = 0; i < rows.size(); i++) {
                data[i] = rows.get(i).split(",");
            }
            return data;
        } catch (IOException e) {
            System.out.println("Error while reading: " + path.getFileName());
            return new String[0][0];
        }
    }
}
