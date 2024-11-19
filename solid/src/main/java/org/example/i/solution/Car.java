package org.example.i.solution;

import org.example.i.problem.Vehicule;

public class Car implements Drivable {
    @Override
    public void drive() {
        System.out.println("Car driving");
    }
}
