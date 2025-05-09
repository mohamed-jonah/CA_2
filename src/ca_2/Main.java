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