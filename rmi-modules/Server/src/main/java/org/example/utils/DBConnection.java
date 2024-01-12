package org.example.utils;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.Server;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static DBConnection instance;
    private static MysqlConnectionPoolDataSource dataSource;


    private DBConnection() {
        dataSource = new MysqlConnectionPoolDataSource();
        Properties props = new Properties();

        try (InputStream fis = DBConnection.class.getResourceAsStream("/db.properties")) {
            props.load(fis);
            dataSource.setURL(props.getProperty("URL"));
            dataSource.setUser(props.getProperty("User"));
            dataSource.setPassword(props.getProperty("Password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
    }

    public static Connection getConnection() throws SQLException {
        getInstance();
        return dataSource.getConnection();
    }


    public static void stopConnection(){
        if(instance == null){
            System.out.println("No DB connection");
            return;
        }
        try {
            dataSource.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
