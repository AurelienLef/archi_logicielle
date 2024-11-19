package org.example.i.problem;

public class Boat implements Vehicule{
    @Override
    public void drive() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void fly() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sail() {
        System.out.println("I'm sailing");
    }
}
