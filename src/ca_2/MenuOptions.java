/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca_2;

/**
 *
 * @author Gerant
 */
public enum MenuOptions {
    SORT("Sort Employees"),
    SEARCH("Search Employee"),
    ADD("Add Employee"),
    GENERATE("Generate Random"),
    EXIT("Exit");

    private final String displayText;

    MenuOptions(String displayText) {
        this.displayText = displayText;
    }

    @Override
    public String toString() {
        return displayText;
    }
}
