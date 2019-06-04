package ru.cft.focusstart.suhodolov;

import java.util.ArrayList;
import java.util.List;

public class Warehouse {

    private final int size;
    private final List<Resource> resources = new ArrayList<>();

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
    }

    public Resource getResource() {
        Resource resource = resources.get(0);
        resources.remove(resource);

        return resource;
    }
}
