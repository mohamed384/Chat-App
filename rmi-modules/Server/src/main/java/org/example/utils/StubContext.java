package org.example.utils;

import java.util.HashMap;
import java.util.Map;

public class StubContext {
    private final Map<String, Object> stubs;

    public StubContext() {
        this.stubs = new HashMap<>();
    }

    public void addStub(String name, Object stub) {
        stubs.put(name, stub);
    }

    public Object getStub(String name) {
        return stubs.get(name);
    }
}