package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionFactory {

    private static Connection connection = null;

    private ConnectionFactory(){

    }

    public static Connection getConnection(){
        if(connection == null){
            boolean isTesting = false;
            String driver = isTesting ? "org.h2.Driver" : "org.postgresql.Driver";
            String configFile = isTesting ? "dbConfigTest" : "dbConfig";

            try{
                Class.forName("org.postgresql.Driver");
            } catch(ClassNotFoundException e){
                e.printStackTrace();
            }

            try{
                ResourceBundle resourceBundle = ResourceBundle.getBundle(configFile);
                String url = resourceBundle.getString("url");
                String username = resourceBundle.getString("username");
                String password = resourceBundle.getString("password");
                connection = DriverManager.getConnection(url, username, password);
            }
            catch(SQLException e){
                System.out.println("Something went wrong when creating the connection");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
