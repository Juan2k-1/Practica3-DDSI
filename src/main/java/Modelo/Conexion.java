/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author juald
 */
public class Conexion 
{       
    private Connection connect = null;
    
    public Conexion() throws SQLException
    {
        //Si queremos conectaros a Oracle
        //String cadenaConexion = "jdbc:oracle:thin:@172.17.20.39:1521:etsi";
        
        //Si queremos conectarnos a mariaDB
        String cadenaConexion = "jdbc:mariadb://172.18.1.241:3306/DDSI_057";
        
        String usuario = "DDSI_057";
        String password = "DDSI_057";
        
        this.connect = DriverManager.getConnection(cadenaConexion, usuario, password);
    }
    public Conexion(String sgdb, String ip, String servicio_bd, String usuario, String password) throws SQLException
    {
        String cadenaConexion;
        if ("oracle".equals(sgdb.toLowerCase()))
        {
            cadenaConexion = "jdbc:oracle:thin:@" + ip + ":" + servicio_bd;
            this.connect = DriverManager.getConnection(cadenaConexion, usuario, password);
        }
        else
        {
            cadenaConexion = "jdbc:mariadb://" + ip + "/" + servicio_bd;
            this.connect = DriverManager.getConnection(cadenaConexion, usuario, password);
        }     
    }
    public void desconexion() throws SQLException
    {
        this.connect.close();
    }
    public Connection getConnect() 
    { 
        return this.connect;
    }
    public DatabaseMetaData informacionBD() throws SQLException {
        return this.connect.getMetaData();
    }
}