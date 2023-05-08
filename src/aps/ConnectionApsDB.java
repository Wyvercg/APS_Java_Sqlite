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
            System.out.println("Conexao com o banco de dados da APS bem sucedida");
            return con;
        }
        
        catch(Exception e){
            System.out.println("Conexao falhou" + e);
            return null;
        }
        
        }
       
                
    
}
