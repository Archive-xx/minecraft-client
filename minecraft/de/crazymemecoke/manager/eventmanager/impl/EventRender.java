package de.crazymemecoke.manager.eventmanager.impl;

import de.crazymemecoke.manager.eventmanager.Event;

public class EventRender extends Event {

    Type type;

    public EventRender(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        twoD, threeD;
    }
}
