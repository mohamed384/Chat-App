package org.example.utils;

public class MessageDAOSaveHelper {
    private final boolean success;
    private final int generatedKey;

    public MessageDAOSaveHelper(boolean success, int generatedKey) {
        this.success = success;
        this.generatedKey = generatedKey;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getGeneratedKey() {
        return generatedKey;
    }
}

