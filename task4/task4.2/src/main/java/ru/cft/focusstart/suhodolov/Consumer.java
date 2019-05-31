package ru.cft.focusstart.suhodolov;

public class Consumer implements Runnable {

    private final Warehouse warehouse;

    private final int id;
    private final int delay;

    public Consumer(final Warehouse warehouse, final int id, final int delay) {
        this.warehouse = warehouse;
        this.id = id;
        this.delay = delay;
    }

    @Override
    public void run() {
        while (true) {
            warehouse.getResource(id);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println("Consumer thread" + id + "has been interrupted");
                return;
            }
        }
    }
}
