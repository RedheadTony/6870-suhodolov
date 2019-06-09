package ru.cft.focusstart.suhodolov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private final int size;
    private final List<Resource> resources = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(Warehouse.class);

    public Warehouse(final int size) {
        this.size = size;
    }

    public boolean isFull() {
        return resources.size() == size;
    }

    public boolean isEmpty() {
        return resources.size() == 0;
    }

    public void addResource(final Resource resource) {
        resources.add(resource);
        logger.info("Resource with id {} produced, current amount of resources in warehouse: {}", resource.getId(), resources.size());
    }

    public Resource getResource() {
        Resource resource = resources.get(0);
        resources.remove(resource);
        logger.info("Resource with id {} consumed, current amount of resources in warehouse: {}", resource.getId(), resources.size());


        return resource;
    }
}
