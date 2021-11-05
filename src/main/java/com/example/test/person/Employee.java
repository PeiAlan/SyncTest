package com.example.test.person;

/**
 * <p>TODO</p>
 *
 * @author Ellison_Pei
 * @date 2021/11/5 08:40
 * @since 1.0
 **/
public class Employee extends Person implements Add{
    private String id;

    private double wage;

    private int workAge;

    public Employee(String name, String address) {
        super(name, address);
    }

    public Employee(String name, String address, String id, double wage, int workAge) {
        super(name, address);
        this.id = id;
        this.wage = wage;
        this.workAge = workAge;
    }

    @Override
    public double add(double wage) {
        double newWage = wage * 1.1;
        return newWage;
    }
}
