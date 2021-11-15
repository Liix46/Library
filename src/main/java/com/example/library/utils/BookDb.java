package com.example.library.utils;

import org.json.simple.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookDb {
    private Connection _connection ;
    private final String _PREFIX ;
    private final JSONObject _config ;

    public BookDb(Connection connection, String PREFIX, JSONObject config) {
        this._connection = connection;
        this._PREFIX = PREFIX;
        this._config = config;
    }

    public boolean isTableExists(){
        String query;
        String dbms = _config.toJSONString();
        if (dbms.toLowerCase().indexOf("Oracle".toLowerCase()) !=-1){
            query = "SELECT COUNT(*) " +
                    "FROM USER_TABLES T " +
                    "WHERE T.TABLE_NAME = " +
                    "'" + _PREFIX + "BOOKS'" ;
        }else{
            return false;
        }
        try(ResultSet res = _connection
                .createStatement()
                .executeQuery(query)){
            if( res.next() ) {
                return res.getInt( 1 ) == 1 ;
            }
        }
        catch (SQLException ex){
            System.err.println(
                    "BookDb.isTableExists:"
                    + ex.getMessage()
            );
        }
        return false;
    }

    public boolean createTable(){
        String query;
        String dbms = _config.toJSONString();
        if (dbms.toLowerCase().indexOf("Oracle".toLowerCase()) !=-1){
            query = "CREATE TABLE " + _PREFIX + "BOOKS (" +
                    "id       RAW(16) DEFAULT SYS_GUID() PRIMARY KEY," +
                    "author   NVARCHAR2(128) NOT NULL," +
                    "title    NVARCHAR2(128) NOT NULL," +
                    "cover    NVARCHAR2(128) )" ;
        }else {
            return false;
        }
        try(Statement statement = _connection.createStatement()){
            statement.executeUpdate(query);
            return true;
        }catch(SQLException ex){
            System.err.println(
                    "BookDb.createTable: "
                    +ex.getMessage()
                    +"\n"
                    +query
            );
        }
        return false;
    }
}
