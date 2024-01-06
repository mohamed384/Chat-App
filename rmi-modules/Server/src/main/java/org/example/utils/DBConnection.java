package org.example.utils;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.example.Server;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection;

        private DBConnection(){

        }
        public static Connection getConnection(){
            if(connection == null) {
                DataSource ds = getMySQLDataSource();
                try {
                    connection = ds.getConnection();
                    System.out.println("Connected to DB");
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            return connection;
        }

        public static void stopConnection(){
            if(connection == null){
                System.out.println("No DB connection");
                return;
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        private static DataSource getMySQLDataSource(){
            Properties props = new Properties();
            MysqlDataSource mysqlDS = null;
            try (InputStream fis = DBConnection.class.getResourceAsStream("/db.properties")) {
                props.load(fis);
                mysqlDS = new MysqlDataSource();
                mysqlDS.setURL(props.getProperty("URL"));
                mysqlDS.setUser(props.getProperty("User"));
                mysqlDS.setPassword(props.getProperty("Password"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mysqlDS;
        }
}
