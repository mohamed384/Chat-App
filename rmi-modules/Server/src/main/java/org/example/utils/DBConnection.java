package org.example.utils;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
            try (FileInputStream fis = new FileInputStream("E:\\project\\ok\\rmi-modules\\Server\\src\\main\\java\\org\\example\\utils\\db.properties")) {
                props.load(fis);
                mysqlDS = new MysqlDataSource();
                mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
                mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
                mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mysqlDS;
        }














//    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/iti";
//    private static final String USER = "root";
//    private static final String PASSWORD = "rootadmin";
//
//    public static Connection getConnection() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
//        } catch (ClassNotFoundException | SQLException e) {
//            throw new RuntimeException("Failed to establish a database connection.", e);
//        }
//    }
//
//    public static void main(String[] args) {
//        try (Connection connection = DBConnection.getConnection()) {
//            if (connection != null) {
//                System.out.println("Connected to the database!");
//            } else {
//                System.out.println("Failed to connect to the database.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
