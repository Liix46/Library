package com.example.library.utils;



import org.json.simple.JSONObject;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static JSONObject config;
    private static Connection connection;
    private static final String _PREFIX = "CS181_46_" ;

    private static BookDb bookDb;

    public static BookDb getBookDb() {
        if (bookDb == null){
            bookDb = new BookDb(connection, _PREFIX, config);
        }
        return bookDb;
    }

    public static Connection getConnection() {return connection;}

    public static boolean setConnection(JSONObject connectionData) {
        try{

            //String dbms = connectionData.getString("dbms");
            String dbms = connectionData.toJSONString();
            if (dbms.toLowerCase().indexOf("Oracle".toLowerCase()) !=-1){
                config = connectionData;
                return setConnectionOracle(connectionData);
            }else if(dbms.toLowerCase().indexOf("MySQL".toLowerCase()) != -1){
                config = connectionData;
                return true;
            }else {
                System.err.println("Db: Unsupported DBMS");
            }
        }catch (Exception ex){
            System.err.println("Db:" + ex.getMessage());
        }
        connection = null;
        config = null;
        return false;
    }

    private static boolean setConnectionOracle(JSONObject connectionData){
        if (connectionData == null){
            connection = null;
            return false;
        }
        try{
            String connectionString = String.format(
                    "jdbc:oracle:thin:%s/%s@%s:%d:XE",
                    connectionData.get("user"),
                    connectionData.get("pass"),
                    connectionData.get("host"),
                    connectionData.get("port")
            );
            DriverManager.registerDriver(
                    new oracle.jdbc.driver.OracleDriver()
            );
            connection = DriverManager.getConnection(connectionString);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception ignored) {}
        }
    }
}
