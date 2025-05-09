/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca_2;

/**
 *
 * @author Gerant
 */
public class Manager extends Employee {

    public enum ManagerType {
        HEAD, ASSISTANT, TEAM_LEAD
    }
    private final ManagerType type;

    public Manager(String name, String department, ManagerType type) {
        super(name, department);
        this.type = type;
    }

    public ManagerType getType() {
        return type;
    }

    @Override
    public String toString() {
        return super.toString() + ", Manager Type: " + type;
    }
}
