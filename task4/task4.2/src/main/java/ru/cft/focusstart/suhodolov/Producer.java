package ru.cft.focusstart.suhodolov;

public class Producer implements Runnable {

    private final Warehouse warehouse;

    private final int id;
    private final int delay;

    public Producer(final Warehouse warehouse, final int id, final int delay) {
        this.warehouse = warehouse;
        this.id = id;
        this.delay = delay;
    }

    @Override
    public void run() {
        while (true) {
            Resource resource = new Resource();
            warehouse.addResource(resource, id);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println("Producer thread" + id + "has been interrupted");
                return;
            }
        }
    }
}
