package org.example.Utils;

import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserCash {
    private static final String CACHE_FILE_NAME = "target/user_cache.txt";
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void saveUser(String username, String password) {
        File file = new File(System.getProperty("user.dir") + File.separator + CACHE_FILE_NAME);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // Ensure the target directory exists
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Cannot create file: " + CACHE_FILE_NAME, e);
            }
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
        File cacheFile = new File(System.getProperty("user.dir") + File.separator + CACHE_FILE_NAME);
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

    public static void shutdownScheduler() {
        scheduler.shutdown();
    }
}