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