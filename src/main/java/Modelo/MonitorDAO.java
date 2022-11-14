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

    private Conexion conexion;
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
                    rs.getString(3), rs.getString(4), rs.getString(5),
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
                    rs.getString(3), rs.getString(4), rs.getString(5),
                    rs.getString(6), rs.getString(7));
            listaMonitores.add(monitor);
        }
        return listaMonitores;
    }

    public void insertarMonitor(Monitor monitor) throws SQLException {

        String insercion = "INSERT INTO MONITOR VALUES (?, ?, ?, ?, ?, ?, ?)";
        this.ps = this.conexion.getConnect().prepareStatement(insercion);

        ps.setString(1, monitor.getCodMonitor());
        ps.setString(2, monitor.getNombre());
        ps.setString(3, monitor.getDni());
        ps.setString(4, monitor.getTelefono());
        ps.setString(5, monitor.getCorreo());
        ps.setString(6, monitor.getFechaEntrada());
        ps.setString(7, monitor.getNick());

        ps.executeUpdate();
    }
    
    public String calcularCodigoMonitor() throws SQLException {

        String mayor = "";

        int mayorNumeroMonitor = 0;
        ArrayList<Monitor> monitores = this.listaMonitores();

        for (int i = 0; i < monitores.size(); i++) {
            int numMonitor = Integer.parseInt(monitores.get(i).getCodMonitor().split("M")[1]);
            if (mayorNumeroMonitor < numMonitor) {
                mayorNumeroMonitor = numMonitor;
            }
        }

        if (mayorNumeroMonitor <= 99) {
            mayor = "M0" + (mayorNumeroMonitor + 1);
        } else if (mayorNumeroMonitor <= 9) {
            mayor = "M00" + (mayorNumeroMonitor + 1);
        } else {
            mayor = "M" + (mayorNumeroMonitor + 1);
        }
        return mayor;
    }

    public void eliminarMonitor(Monitor monitor) throws SQLException {
        String eliminar = "DELETE FROM MONITOR WHERE codMonitor=?";

        ps = conexion.getConnect().prepareStatement(eliminar);
        ps.setString(1, monitor.getCodMonitor());
        ps.executeUpdate();
    }

    public void actualizarMonitor(Monitor update) throws SQLException {
        String actualizar = "UPDATE MONITOR SET NOMBRE = ?, DNI = ?, TELEFONO = ?, CORREO = ?, FECHAENTRADA = ?, NICK = ? WHERE codMonitor = ?";
        
        ps = conexion.getConnect().prepareStatement(actualizar);
        ps.setString(1, update.getNombre());
        ps.setString(2, update.getDni());
        ps.setString(3, update.getTelefono());
        ps.setString(4, update.getCorreo());
        ps.setString(5, update.getFechaEntrada());
        ps.setString(6, update.getNick());
        ps.setString(7, update.getCodMonitor());
        ps.executeUpdate();
    }
}
