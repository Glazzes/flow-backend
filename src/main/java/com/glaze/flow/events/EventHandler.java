package com.glaze.flow.events;

public interface EventHandler <T> {
    void handle(T event);
}
