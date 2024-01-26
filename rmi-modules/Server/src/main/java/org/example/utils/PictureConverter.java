//package org.example.utils;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//public class PictureConverter {
//
//    public byte[] getDefaultPictureData() {
//        try {
//            Path path = Paths.get(PictureConverter.class.getResource("/default.jpg").toURI());
//            return Files.readAllBytes(path);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
//
//    public byte[] getPictureData(String imagePath) {
//        try {
//            Path path = Paths.get(imagePath);
//            return Files.readAllBytes(path);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public URI convertBytesToURI(byte[] pictureBytes, String fileName) {
//        Path path = Paths.get(System.getProperty("user.dir"), fileName);
//        try (FileOutputStream fos = new FileOutputStream(path.toFile())) {
//            fos.write(pictureBytes);
//            return path.toUri();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
