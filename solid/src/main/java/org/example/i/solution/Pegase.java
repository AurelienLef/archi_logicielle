package org.example.i.solution;

public class Pegase implements Drivable, Flyable{
    @Override
    public void drive() {
        System.out.println("Pegase driving");
    }

    @Override
    public void fly() {
        System.out.println("Pegase flying");
    }
}
