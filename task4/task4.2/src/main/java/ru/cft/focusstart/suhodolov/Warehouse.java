package ru.cft.focusstart.suhodolov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private static final Logger logger = LoggerFactory.getLogger(Warehouse.class);

    private static final String THREAD_WAIT_ACTION = "перешел в режим ожидания";
    private static final String THREAD_NOTIFY_ACTION = "возобновляет работу";

    private final int size;
    private volatile List<Resource> resources = new ArrayList<>();

    public Warehouse(final int size) {
        this.size = size;
    }

    public synchronized void addResource(Resource resource, final int id) {
        String producerLog = String.format("Номер: %d Тип: производитель ", id);

        String producerAddResourceAction = String.format("Id ресурса: %d произведен", resource.getId());

        while (resources.size() >= size) {
            try {
                logger.info(producerLog + THREAD_WAIT_ACTION);
                wait();
                logger.info(producerLog + THREAD_NOTIFY_ACTION);
            } catch (InterruptedException e) {
                System.out.println("Producer thread" + id + "has been interrupted");
            }
        }
        resources.add(resource);

        logger.info(producerLog + producerAddResourceAction);

        notify();
    }

    public synchronized Resource getResource(final int id) {
        String consumerLog = String.format("Номер: %d Тип: потребитель ", id);

        while (resources.size() <= 0) {
            try {
                logger.info(consumerLog + THREAD_WAIT_ACTION);
                wait();
                logger.info(consumerLog + THREAD_NOTIFY_ACTION);
            } catch (InterruptedException e) {
                System.out.println("Consumer thread" + id + "has been interrupted");
            }
        }
        Resource resource = resources.get(0);
        resources.remove(resource);

        String consumerGetResourceAction = String.format("Id ресурса: %d потреблен", resource.getId());

        logger.info(consumerLog + consumerGetResourceAction);

        notify();

        return resource;
    }
}
