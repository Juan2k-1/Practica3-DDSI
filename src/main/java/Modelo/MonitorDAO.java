/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author juald
 */
public class MonitorDAO {
    private final Conexion conexion;
    private PreparedStatement ps;
    
    public MonitorDAO() {
        this.conexion = null;
        this.ps = null;
    }
    
    public MonitorDAO(Conexion conexion) {
        this.conexion = conexion;
    }
    
    public ArrayList<Monitor> listaMonitores() throws SQLException {
        
        ArrayList listaMonitores = new ArrayList();
        String consulta = "SELECT * FROM MONITOR";
        this.ps = this.conexion.getConnect().prepareStatement(consulta);
        ResultSet rs = this.ps.executeQuery();
        while (rs.next()) {
            Monitor monitor = new Monitor(rs.getString(1), rs.getString(2), 
                    rs.getString(3), rs.getInt(4), rs.getString(5), 
                    rs.getString(6), rs.getString(7));
            listaMonitores.add(monitor);
        }
         return listaMonitores;
    } 
    
    public ArrayList<Monitor> listaMonitoresPorLetra(String letra) throws SQLException {
        ArrayList listaMonitores = new ArrayList<Monitor>();
        
        String consulta = "SELECT * FROM MONITOR WHERE nombre LIKE ?";
        this.ps = this.conexion.getConnect().prepareStatement(consulta);
        
        letra = letra + "%";
        this.ps.setString(1, letra);
        ResultSet rs = this.ps.executeQuery();
        
        while (rs.next()) {
            Monitor monitor = new Monitor(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getInt(4), rs.getString(5), 
                    rs.getString(6), rs.getString(7));
            listaMonitores.add(monitor);
        }
        return listaMonitores;
    }
}
