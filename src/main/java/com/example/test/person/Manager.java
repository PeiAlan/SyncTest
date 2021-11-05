package com.example.test.person;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/11/5 08:41
 * @since 1.0
 **/
public class Manager extends Employee implements Add{
    private String level;

    public Manager(String name, String address) {
        super(name, address);
    }

    public Manager(String name, String address, String id, double wage, int workAge) {
        super(name, address, id, wage, workAge);
    }

    public Manager(String name, String address, String level) {
        super(name, address);
        this.level = level;
    }

    public Manager(String name, String address, String id, double wage, int workAge, String level) {
        super(name, address, id, wage, workAge);
        this.level = level;
    }

    @Override
    public double add(double wage) {
        double newWage = wage * 1.2;
        return newWage;
    }
}
