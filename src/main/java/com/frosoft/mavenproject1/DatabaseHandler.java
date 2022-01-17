//
//
//package com.frosoft.mavenproject1;
////import org.apache.derby.client;
//import java.sql.Statement;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.DatabaseMetaData;
//import java.sql.SQLException;
//import java.sql.ResultSet;
////import javax.swing.JOptionPane;
//
//public class DatabaseHandler {
//    private static DatabaseHandler handler;
//    private static final String DB_URL= "jdbc:derby://localhost:1527/credential";
//    private static Connection conn = null;
//    private static Statement stmt = null;
//   public DatabaseHandler(){
//    createconnection();
//    setuphospital();
//}
//    
//    void createconnection(){
//        try{
//            //Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
//            conn = DriverManager.getConnection(DB_URL,);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    void setuphospital(){
//        String TABLE_NAME = "CREDENTIALS";
//        try{
//            stmt = conn.createStatement();
//            DatabaseMetaData dbm = conn.getMetaData();
//            ResultSet tables = dbm.getTables(null,null,TABLE_NAME.toUpperCase(),null);
//            if(tables.next()){
//                System.out.println("Table"+ TABLE_NAME + " already exists. ready to go");
//            }
//            else{
//                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
//                +"      Username varchar(200) primary key,\n"
//                +"      Password varchar(200),\n"                
//                +")");
//            }
//        }
//        catch(SQLException e){
//            System.err.println(e.getMessage()+ " ... setup database");
//        }
//    }
//    public ResultSet execQuery(String query){
//        ResultSet result;
//        try{
//            stmt= conn.createStatement();
//            result = stmt.executeQuery(query);
//        }catch(SQLException ex){
//            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
//            return null;
//        }
//        return result;
//    }
//    public boolean execAction(String qu){
//        try{
//            stmt = conn.createStatement();
//            stmt.execute(qu);
//            return true;
//        }catch(SQLException ex){
//          //  JOptionPane.showMessageDialog(null,"Error:"+ex.getMessage(),"Error Occured",JOptionPane.ERROR_MESSAGE);
//            System.out.println(" Exception at execQuery : Datahandler"+ex.getLocalizedMessage());
//            return false;
//        }
//    }
//    
//}
///*else{
//                stmt.execute("CREATE TABLE " + TABLE_NAME + "("
//                +"      Username varchar(200) primary key,\n"
//                +"      Password varchar(200),\n"
//                +"      category varchar(200),\n"
//                +"      Stock int ,\n"
//                +"      isAvail boolean default true"
//                +")");
//            }*/