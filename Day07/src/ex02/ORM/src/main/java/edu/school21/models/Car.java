package edu.school21.models;


import edu.school21.orm.annotation.OrmColumn;
import edu.school21.orm.annotation.OrmColumnId;
import edu.school21.orm.annotation.OrmEntity;

import java.util.Objects;


@OrmEntity(table = "cars")
public class Car {
    @OrmColumnId(name = "id")
    private Long id;
    @OrmColumn(name = "name", length = 20)
    private String name;
    @OrmColumn(name = "brand", length = 20)
    private String brand;
    @OrmColumn(name = "kilometerage")
    private Integer kilometerage;

    public Car() {
    }

    public Car(String name, String brand, Integer kilometerage) {
        this.name = name;
        this.brand = brand;
        this.kilometerage = kilometerage;
    }

    public Car(Long id, String name, String brand, Integer kilometerage) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.kilometerage = kilometerage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getKilometerage() {
        return kilometerage;
    }

    public void setKilometerage(Integer kilometerage) {
        this.kilometerage = kilometerage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) && Objects.equals(name, car.name) && Objects.equals(brand, car.brand) && Objects.equals(kilometerage, car.kilometerage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, kilometerage);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", kilometerage=" + kilometerage +
                '}';
    }
}