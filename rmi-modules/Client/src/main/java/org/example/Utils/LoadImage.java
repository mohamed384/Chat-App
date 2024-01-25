package org.example.Utils;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;

public class LoadImage {
    public static Image loadImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.bmp"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            return new Image(selectedFile.toURI().toString());

        }
        return null;
    }
}