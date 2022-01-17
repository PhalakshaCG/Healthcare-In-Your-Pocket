/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.frosoft.mavenproject1;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Phalaksha C G
 */
public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName="logincred";
        String databaseUser ="root";
        String databasePassword = "Phallu*30101";
        String url = "jdbc:mysql://localhost:3306/logincred";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);
            
        }
        catch(Exception e){}
        return databaseLink;
    }
}
