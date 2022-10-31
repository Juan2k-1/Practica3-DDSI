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
public class SocioDAO {

    private final Conexion conexion;
    private PreparedStatement ps;

    public SocioDAO() {
        this.conexion = null;
        this.ps = null;
    }

    public SocioDAO(Conexion conexion) {
        this.conexion = conexion;
    }

    public ArrayList<Socio> listarSocios() throws SQLException {

        ArrayList listarSocios = new ArrayList<Socio>();

        String consulta = "SELECT * FROM SOCIO";
        this.ps = this.conexion.getConnect().prepareStatement(consulta);

        ResultSet rs = this.ps.executeQuery();
        while (rs.next()) {
            Socio socio = new Socio(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7),
                    rs.getString(8));
            listarSocios.add(socio);
        }
        return listarSocios;
    }

    public ArrayList<Socio> listaSocioPorLetra(String letra) throws SQLException {

        ArrayList<Socio> listarSocios = new ArrayList<Socio>();

        String consulta = "SELECT * FROM SOCIO WHERE nombre LIKE ?";
        ps = conexion.getConnect().prepareStatement(consulta);

        letra = letra + "%";
        ps.setString(1, letra);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Socio socio = new Socio(rs.getInt(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getInt(5), rs.getString(6), rs.getString(7),
                    rs.getString(8));
            listarSocios.add(socio);
        }
        return listarSocios;
    }
}
