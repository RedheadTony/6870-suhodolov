package ru.cft.focusstart.suhodolov;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Application {

    public static void main(String[] args) {
        int warehouseSize = 10;
        Warehouse warehouse = new Warehouse(warehouseSize);

        int producerCount = 5;
        int producerDelayTime = 2000;

        ExecutorService producers = Executors.newFixedThreadPool(producerCount);
        for (int i = 0; i < producerCount; i++) {
            producers.submit(new Producer(warehouse, i, producerDelayTime));
        }

        int consumerCount = 5;
        int consumerDelayTime = 1000;

        ExecutorService consumers = Executors.newFixedThreadPool(consumerCount);
        for (int i = 0; i < consumerCount; i++) {
            consumers.submit(new Consumer(warehouse, i, consumerDelayTime));
        }

        producers.shutdown();
        consumers.shutdown();
    }
}
