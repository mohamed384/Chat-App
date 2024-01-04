package com.example.DataAccessLayer;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private static final String propPath="../resources/db.properties";
    public static void createProp() {
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = Files.newOutputStream(Paths.get(propPath));

            prop.setProperty("URL", "jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11674278");
            prop.setProperty("User", "sql11674278");
            prop.setProperty("Password", "TzbJUuFkHB");

            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static DataSource createDataSource() {
        Properties prop = new Properties();
        MysqlDataSource mysqlDS = new MysqlDataSource();

        try (InputStream iStream = Files.newInputStream(Paths.get(propPath))) {
            // Load properties from the file
            prop.load(iStream);
            mysqlDS.setURL(prop.getProperty("URL"));
            mysqlDS.setUser(prop.getProperty("User"));
            mysqlDS.setPassword(prop.getProperty("Password"));

        } catch (IOException e) {
            System.out.println("IOEXCEPTION: " + e.getMessage());
        }

        return mysqlDS;
    }
    public static Connection getMyConnection() {
        DataSource ds = createDataSource();

        Connection con = null;

        try {
            con = ds.getConnection();
            return con;
        } catch (SQLException e) {
            System.out.println("Can not create connection... ");
            e.printStackTrace();
            return null;
        }
    }
}
