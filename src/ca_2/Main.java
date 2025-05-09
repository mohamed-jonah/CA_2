/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca_2;
import java.io.*;
import java.util.*;
/**
 *
 * @author Gerant
 */
public class Main {
    private static List<Employee> employees = new ArrayList<>();
    private static final String FILE_PATH = "Applicants_Form.txt";
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

public static void main(String[] args) {
        System.out.println("Employee Management System");
        loadEmployees();

        while (true) {
            displayMenu();
            int choice = getIntInput(1, 5);
            
            switch (choice) {
                case 1: sortEmployees(); break;
                case 2: searchEmployee(); break;
                case 3: addEmployee(); break;
                case 4: generateEmployee(); break;
                case 5: System.exit(0);
            }
        }
    }
 private static void displayMenu() {
        System.out.println("\nMenu Options:");
        for (MenuOptions option : MenuOptions.values()) {
            System.out.println((option.ordinal() + 1) + ". " + option);
        }
        System.out.print("Select option: ");
    }
 private static void loadEmployees() {
        try {
            List<String> lines = readFileLines();
            if (lines.size() <= 1) return;
            
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    String name = parts[0].trim() + " " + parts[1].trim();
                    String department = parts[5].trim();
                    String position = parts[7].trim();
                    
                    try {
                        Manager.ManagerType type = Manager.ManagerType.valueOf(position);
                        employees.add(new Manager(name, department, type));
                    } catch (IllegalArgumentException e) {
                        employees.add(new Employee(name, department));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Warning: Could not load employee data");
        }
    }
 private static void sortEmployees() {
        try {
            List<String> lines = readFileLines();
            if (lines.size() <= 1) {
                System.out.println("No data found in file");
                return;
            }
            
            List<String> dataLines = lines.subList(1, lines.size());
            mergeSort(dataLines);
            
            System.out.println("\nSorted Employees (First 20):");
            System.out.println(lines.get(0));
            for (int i = 0; i < Math.min(20, dataLines.size()); i++) {
                System.out.println(dataLines.get(i));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
        }
    }

    private static void mergeSort(List<String> list) {
        if (list.size() <= 1) return;
        
        int mid = list.size() / 2;
        List<String> left = new ArrayList<>(list.subList(0, mid));
        List<String> right = new ArrayList<>(list.subList(mid, list.size()));
        
        mergeSort(left);
        mergeSort(right);
        
        merge(list, left, right);
    }

    private static void merge(List<String> result, List<String> left, List<String> right) {
        int i = 0, j = 0, k = 0;
        
        while (i < left.size() && j < right.size()) {
            if (left.get(i).compareToIgnoreCase(right.get(j)) <= 0) {
                result.set(k++, left.get(i++));
            } else {
                result.set(k++, right.get(j++));
            }
        }
        
        while (i < left.size()) result.set(k++, left.get(i++));
        while (j < right.size()) result.set(k++, right.get(j++));
    }
    private static void searchEmployee() {
    System.out.print("Enter name to search: ");
    String searchName = scanner.nextLine().trim().toLowerCase();
    boolean found = false;
    
    try {
        List<String> lines = readFileLines();
        if (lines.size() > 1) {
            List<String> dataLines = lines.subList(1, lines.size());
            mergeSort(dataLines);
            
            int index = binarySearch(dataLines, searchName);
            if (index != -1) {
                System.out.println("\nSearch results:");
                System.out.println(lines.get(0));
                System.out.println(dataLines.get(index));
                found = true;
                
                
                for (int i = index - 1; i >= 0; i--) {
                    String currentName = (dataLines.get(i).split(",")[0] + " " + 
                                        dataLines.get(i).split(",")[1]).toLowerCase();
                    if (currentName.contains(searchName)) {
                        System.out.println(dataLines.get(i));
                    } else {
                        break;
                    }
                }
                
                
                for (int i = index + 1; i < dataLines.size(); i++) {
                    String currentName = (dataLines.get(i).split(",")[0] + " " + 
                                        dataLines.get(i).split(",")[1]).toLowerCase();
                    if (currentName.contains(searchName)) {
                        System.out.println(dataLines.get(i));
                    } else {
                        break;
                    }
                }
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
    
    
    if (!employees.isEmpty()) {
        for (Employee e : employees) {
            if (e.getName().toLowerCase().contains(searchName)) {
                if (!found) {
                    System.out.println("\nIn-Memory Matches:");
                    found = true;
                }
                System.out.print(e.getName() + " | " + e.getDepartment());
                if (e instanceof Manager) {
                    System.out.print(" | " + ((Manager)e).getType());
                }
                System.out.println();
            }
        }
    }
    
    if (!found) {
        System.out.println("No matches found for: " + searchName);
    }
}

private static int binarySearch(List<String> list, String searchName) {
    int low = 0;
    int high = list.size() - 1;
    
    while (low <= high) {
        int mid = (low + high) / 2;
        String[] parts = list.get(mid).split(",");
        if (parts.length < 2) continue;
        
        String currentName = (parts[0] + " " + parts[1]).toLowerCase();
        
        if (currentName.contains(searchName)) {
            return mid;
        } else if (currentName.compareTo(searchName) < 0) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }
    return -1;
}
      private static void addEmployee() {
        try {
            System.out.print("Enter first name: ");
            String firstName = getValidNameInput();
            
            System.out.print("Enter last name: ");
            String lastName = getValidNameInput();

            Department.DeptName department = selectDepartment();
            Manager.ManagerType type = selectManagerType();

            String csvLine = String.join(",",
                firstName, lastName, "", "", "", department.toString(), "", 
                type.toString(), ""
            );

            try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH, true))) {
                out.println(csvLine);
            }

            employees.add(new Manager(
                firstName + " " + lastName,
                department.toString(),
                type
            ));

            System.out.printf("\n%s %s has been added as %s to %s successfully!\n",
                firstName, lastName, type, department);

        } catch (IOException e) {
            System.out.println("Error saving employee: " + e.getMessage());
        }
    }

    private static String getValidNameInput() {
        while (true) {
            String input = scanner.nextLine().trim();
            if (!input.isEmpty() && input.matches("[a-zA-Z]+")) {
                return input;
            }
            System.out.print("Invalid input. Please enter letters only: ");
        }
    }

    private static Department.DeptName selectDepartment() {
        System.out.println("Select department:");
        Department.DeptName[] departments = Department.DeptName.values();
        for (int i = 0; i < departments.length; i++) {
            System.out.println((i+1) + ". " + departments[i]);
        }
        int deptChoice = getIntInput(1, departments.length);
        return departments[deptChoice-1];
    }

    private static Manager.ManagerType selectManagerType() {
        System.out.println("Select manager type:");
        Manager.ManagerType[] types = Manager.ManagerType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i+1) + ". " + types[i]);
        }
        int typeChoice = getIntInput(1, types.length);
        return types[typeChoice-1];
    }
    private static void generateEmployee() {
        String[] firstNames = {"Ken", "James", "Mike", "Sonya", "David", "Jessica"};
        String[] lastNames = {"Winter", "Anderson", "Williams", "Taylor", "Jones", "Lee"};
        
        String name = firstNames[random.nextInt(firstNames.length)] + " " + 
                     lastNames[random.nextInt(lastNames.length)];
        
        Manager.ManagerType type = Manager.ManagerType.values()[random.nextInt(3)];
        Department.DeptName dept = Department.DeptName.values()[random.nextInt(3)];
        
        Manager newEmp = new Manager(name, dept.toString(), type);
        employees.add(newEmp);
        System.out.printf("\nGenerated: %s as %s in %s department\n", name, type, dept);
    }

    private static List<String> readFileLines() throws FileNotFoundException {
        List<String> lines = new ArrayList<>();
        try (Scanner fileScanner = new Scanner(new File(FILE_PATH))) {
            while (fileScanner.hasNextLine()) {
                lines.add(fileScanner.nextLine());
            }
        }
        return lines;
    }
