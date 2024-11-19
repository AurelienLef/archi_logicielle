package org.example.i.solution;

public class Amphicar implements Drivable, Sailable{
    @Override
    public void drive() {
        System.out.println("Amphicar driving");
    }

    @Override
    public void sail() {
        System.out.println("Amphicar sailing");
    }
}
