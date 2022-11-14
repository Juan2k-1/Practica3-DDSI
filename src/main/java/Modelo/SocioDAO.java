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

    private Conexion conexion;
    private PreparedStatement ps;

    public SocioDAO() {
        this.conexion = null;
        this.ps = null;
    }

    public SocioDAO(Conexion conexion) {
        this.conexion = conexion;
    }

    public ArrayList<Socio> listarSocios() throws SQLException {

        ArrayList listarSocios = new ArrayList<>();

        String consulta = "SELECT * FROM SOCIO";
        this.ps = this.conexion.getConnect().prepareStatement(consulta);

        ResultSet rs = this.ps.executeQuery();
        while (rs.next()) {
            Socio socio = new Socio(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
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
            Socio socio = new Socio(rs.getString(1), rs.getString(2), rs.getString(3),
                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                    rs.getString(8));
            listarSocios.add(socio);
        }
        return listarSocios;
    }

    public void insertarSocio(Socio socio) throws SQLException {

        String insercion = "INSERT INTO SOCIO VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        this.ps = this.conexion.getConnect().prepareStatement(insercion);

        ps.setString(1, socio.getnumeroSocio());
        ps.setString(2, socio.getNombre());
        ps.setString(3, socio.getDni());
        ps.setString(4, socio.getFechaNacimiento());
        ps.setString(5, socio.getTelefono());
        ps.setString(6, socio.getCorreo());
        ps.setString(7, socio.getFechaEntrada());
        ps.setString(8, socio.getCategoria());

        ps.executeUpdate();
    }

    public String calcularCodigoSocio() throws SQLException {

        String mayor = "";

        int mayorNumeroSocio = 0;
        ArrayList<Socio> socios = this.listarSocios();

        for (int i = 0; i < socios.size(); i++) {
            int numSocio = Integer.parseInt(socios.get(i).getnumeroSocio().split("S")[1]);
            if (mayorNumeroSocio < numSocio) {
                mayorNumeroSocio = numSocio;
            }
        }

        if (mayorNumeroSocio <= 99) {
            mayor = "S0" + (mayorNumeroSocio + 1);
        } else if (mayorNumeroSocio <= 9) {
            mayor = "S00" + (mayorNumeroSocio + 1);
        } else {
            mayor = "S" + (mayorNumeroSocio + 1);
        }
        return mayor;
    }

    public void eliminarSocio(Socio socio) throws SQLException {
        String eliminar = "DELETE FROM SOCIO WHERE NUMEROSOCIO=?";

        ps = conexion.getConnect().prepareStatement(eliminar);
        ps.setString(1, socio.getnumeroSocio());
        ps.executeUpdate();
    }

    public void actualizarSocio(Socio update) throws SQLException {
        String actualizar = "UPDATE SOCIO SET NOMBRE = ?, DNI = ?, FECHANACIMIENTO = ?, TELEFONO = ?, CORREO = ?, FECHAENTRADA = ?, CATEGORIA = ? WHERE NUMEROSOCIO = ?";

        ps = conexion.getConnect().prepareStatement(actualizar);
        ps.setString(1, update.getNombre());
        ps.setString(2, update.getDni());    
        ps.setString(3, update.getFechaNacimiento());
        ps.setString(4, update.getTelefono());
        ps.setString(5, update.getCorreo());
        ps.setString(6, update.getFechaEntrada());
        ps.setString(7, update.getCategoria());
        ps.setString(8, update.getnumeroSocio());
        ps.executeUpdate();
    }
}
