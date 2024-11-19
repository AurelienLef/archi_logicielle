package org.example.i.solution;

import org.example.i.problem.Vehicule;

public class Boat implements Sailable {
    @Override
    public void sail() {
        System.out.println("I'm sailing");
    }
}
