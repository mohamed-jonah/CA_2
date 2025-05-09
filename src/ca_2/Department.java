/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca_2;

/**
 *
 * @author Gerant
 */
public class Department {

    public enum DeptName {
        CUSTOMER_SERVICE("Customer Service"),
        TECHNICAL_SUPPORT("Technical Support"),
        HR("HR");

        private final String displayName;

        DeptName(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
}
