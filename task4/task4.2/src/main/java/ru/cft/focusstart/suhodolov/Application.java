package ru.cft.focusstart.suhodolov;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    private static final int WAREHOUSE_SIZE = 10;
    private static final int PRODUCER_COUNT = 5;
    private static final int PRODUCER_DELAY_TIME = 2000;
    private static final int CONSUMER_COUNT = 5;
    private static final int CONSUMER_DELAY_TIME = 1000;

    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse(WAREHOUSE_SIZE);

        ExecutorService producers = Executors.newFixedThreadPool(PRODUCER_COUNT);
        for (int i = 0; i < PRODUCER_COUNT; i++) {
            producers.submit(new Producer(warehouse, i, PRODUCER_DELAY_TIME));
        }

        ExecutorService consumers = Executors.newFixedThreadPool(CONSUMER_COUNT);
        for (int i = 0; i < CONSUMER_COUNT; i++) {
            consumers.submit(new Consumer(warehouse, i, CONSUMER_DELAY_TIME));
        }

        producers.shutdown();
        consumers.shutdown();
    }
}
