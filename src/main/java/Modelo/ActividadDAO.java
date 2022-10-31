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
public class ActividadDAO {
    private final Conexion conexion;
    private PreparedStatement ps;
    
    public ActividadDAO() {
        this.conexion = null;
        this.ps = null;
    }
    
    public ActividadDAO(Conexion conexion) {
        this.conexion = conexion;
    }
}
