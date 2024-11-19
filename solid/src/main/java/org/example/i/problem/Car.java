package org.example.i.problem;

public class Car implements Vehicule{
    @Override
    public void drive() {
        System.out.println("Car driving");
    }

    @Override
    public void fly() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sail() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
