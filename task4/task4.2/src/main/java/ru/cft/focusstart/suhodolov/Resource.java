package ru.cft.focusstart.suhodolov;

public class Resource {

    private static int count = 0;
    private static final Object lock = new Object();

    private final int id;

    public Resource() {
        synchronized (lock) {
            this.id = count++;
        }
    }

    public int getId() {
        return id;
    }
}
