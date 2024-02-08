package org.example.Utils;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserCash {
    private static final String CACHE_FILE_NAME = "user_cache.txt";
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void saveUser(String username, String password) {
        URL resourceUrl = UserCash.class.getClassLoader().getResource(CACHE_FILE_NAME);
        File file;
        if (resourceUrl == null) {
            // If the file doesn't exist, create it
            file = new File(UserCash.class.getClassLoader().getResource("").getPath() + CACHE_FILE_NAME);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Cannot create file: " + CACHE_FILE_NAME, e);
            }
        } else {
            file = new File(resourceUrl.getFile());
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.println(username);
            out.println(password);
        } catch (IOException e) {
            e.printStackTrace();
        }


        scheduler.schedule(() -> {
            try {
                Files.deleteIfExists(file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 3, TimeUnit.HOURS);
    }

    public static String[] loadUser() {
        URL resourceUrl = UserCash.class.getClassLoader().getResource(CACHE_FILE_NAME);
        if (resourceUrl == null) {
            return null;
        }

        File cacheFile = new File(resourceUrl.getFile());
        if (!cacheFile.exists()) {
            return null;
        }

        try (BufferedReader in = new BufferedReader(new FileReader(cacheFile))) {
            String username = in.readLine();
            String password = in.readLine();
            return new String[]{username, password};
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}