package edu.school21.classes.car;

import java.util.StringJoiner;

public class Car {
    private String name;
    private String brand;
    private int kilometerage;

    public Car() {
        this.name = "Default name";
        this.brand = "Default brand name";
        this.kilometerage = 0;
    }

    public Car(String name, String brand, int kilometerage) {
        this.name = name;
        this.brand = brand;
        this.kilometerage = kilometerage;
    }

    public int ride(int kilometers) {
        this.kilometerage += kilometers;
        return kilometerage;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("brand='" + brand + "'")
                .add("kilometerage=" + kilometerage)
                .toString();
    }
}
