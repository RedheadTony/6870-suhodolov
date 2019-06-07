package ru.cft.focusstart.suhodolov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

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
        String producerLog = String.format("Номер: %d Тип: производитель ", id);
        try {
            while (!Thread.interrupted()) {
                Thread.sleep(delay);
                Resource resource = new Resource();
                synchronized (warehouse) {
                    if (warehouse.isFull()) {
                        logger.info(producerLog + "перешел в режим ожидания");

                        while (warehouse.isFull()) {
                            Producer.class.wait();
                        }

                        logger.info(producerLog + "возобновляет работу");
                    }
                    logger.info(producerLog + "Id ресурса: {} произведен", resource.getId());
                    warehouse.addResource(resource);
                    warehouse.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Producer thread" + id + "has been interrupted");
        }
    }
}
