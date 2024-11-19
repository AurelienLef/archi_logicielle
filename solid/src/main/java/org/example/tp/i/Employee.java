package org.example.tp.i;

public class Employee implements Worker, Eater, Sleeper{
    @Override
    public void eat() {
        System.out.println("Eating...");
    }

    @Override
    public void sleep() {
        System.out.println("Sleeping...");
    }

    @Override
    public void work() {
        System.out.println("Working...");
    }
}
