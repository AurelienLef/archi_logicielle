package org.example.i.solution;

import org.example.i.problem.Vehicule;

public class Plane implements Flyable, Drivable {
    @Override
    public void fly() {
        System.out.println("I'm flying");
    }

    @Override
    public void drive() {
        System.out.println("I'm driving");
    }
}
