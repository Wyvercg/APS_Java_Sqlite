/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aps;

import java.sql.*;


public class ConnectionApsDB {
    Connection con = null;
        
        public static Connection ConnectionDB(){
        
        try{
        
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:APSDB.db");
            System.out.println("Conexao com o banco de dados de login bem sucedida");
            return con;
        }
        
        catch(Exception e){
            System.out.println("Connection Failed " + e);
            return null;
        }
        
        }
       
                
    
}
