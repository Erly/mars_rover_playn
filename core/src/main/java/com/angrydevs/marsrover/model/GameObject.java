package com.angrydevs.marsrover.model;

public abstract class GameObject {
    private Size size;

    public GameObject(Size size) {
        this.size = size;
    }

    public Size getSize() {
        return size;
    }
}
