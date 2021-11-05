package com.example.test.car;

/**
 * @author Ellison Pei
 * @date 2021/11/5 08:34
 * @since 1.0
 **/
public class Person {

    private String name;

    private String address;

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }
}

interface Add1 {
    /**
     * Employee 员工涨工资 10%
     * Manager 经理涨工资 20%
     * @param wage 员工当前工资
     * @return 涨薪之后的工资
     */
    double add(double wage);
}

class Employee extends Person implements Add1 {
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

class Manager extends Employee implements Add1 {
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


