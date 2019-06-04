package ru.cft.focusstart.suhodolov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

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
        String consumerLog = String.format("Номер: %d Тип: потребитель ", id);

        try {
            while (!Thread.interrupted()) {
                synchronized (warehouse) {
                    if (warehouse.isEmpty()) {
                        logger.info(consumerLog + "перешел в режим ожидания");

                        while (warehouse.isEmpty()) {
                            warehouse.wait();
                        }

                        logger.info(consumerLog + "возобновляет работу");
                    }
                    Resource resource = warehouse.getResource();

                    logger.info(consumerLog + String.format("Id ресурса: %d потреблен", resource.getId()));

                    warehouse.notifyAll();
                }
                Thread.sleep(delay);
            }
        } catch (InterruptedException e) {
            System.out.println("Consumer thread" + id + "has been interrupted");
        }
    }
}
