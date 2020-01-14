package com.training.project;

public class MyCollection {
    private static Employee array[] = new Employee[300];
    protected static int writeCounter = 0;
    protected static int readCounter = 0;

    public static synchronized void add(Employee e) {
        array[writeCounter++] = e;
    }

    public static synchronized Employee get() {
        return array[readCounter++];
    }
}
