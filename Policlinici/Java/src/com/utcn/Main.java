package com.utcn;

import com.utcn.CommonInterfaces.Login;

import java.sql.*;

public class Main {

    private final static String USERNAME = "root";
    private final static String PASSWORD = "Rootpass107";
    private final static String DB_NAME = "lant_policlinici";
    private final static String CONNECTION_LINK = "jdbc:mysql://localhost:3306/";

    public static void main(String[] args) {
        try {
             Connection connection = DriverManager.getConnection(CONNECTION_LINK + DB_NAME, USERNAME, PASSWORD);
             new Login(connection);
             /*Angajat angajat = new Angajat();
             new RepartizareMedici(connection, angajat);*/
        }
        catch (SQLException e) {
            System.out.println("not connected to DB");
        }
    }
}
