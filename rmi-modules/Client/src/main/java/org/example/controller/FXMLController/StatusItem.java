package org.example.controller.FXMLController;

import javafx.scene.image.Image;

public class StatusItem {
    private String statusText;
    private Image statusIcon;

    public StatusItem(String statusText, String iconPath) {
        this.statusText = statusText;
        this.statusIcon = new Image(iconPath);
    }

    public String getStatusText() {
        return statusText;
    }

    public Image getStatusIcon() {
        return statusIcon;
    }

    @Override
    public String toString() {
        return statusText;
    }
}