/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Vista.VistaConsola;
import Vista.VistaLogin;
import Vista.VistaMensaje;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author juald
 */
public class ControladorLogin implements ActionListener {

    private Conexion conexion_bd;
    private VistaConsola vista = new VistaConsola();
    private VistaLogin vLogin;
    private VistaMensaje vMensaje;
    private boolean desconexionOK = false;

    /**
     * Este método es el constructor de la clase ControladorLogin
     * Se encarga de inicializar los atributos privados
     * Pone los valores correspondientes a cada campo del componente jComboBox y se los pasa a un objeto tipo Conexion para iniciar la conexion con el servidor de la base de datos en cuestión
     */
    public ControladorLogin() {
        /*this.conexion_bd = conectar();
        try {

            this.vista.vistaMetadatos(this.recuperar_metadatos());

        } catch (SQLException e) {
        }
        this.desconexionOK = desconectar();*/
        vLogin = new VistaLogin();
        //Valores de los campos de conexion por defecto para Oracle
        vLogin.jTextFieldIP.setText("172.17.20.39:1521");
        vLogin.jTextFieldServicie.setText("etsi");
        vLogin.jTextFieldUser.setText("DDSI_057");
        vLogin.jPasswordFieldPasswordBD.setText("DDSI_057");

        vMensaje = new VistaMensaje();

        addListeners();

        vLogin.setLocationRelativeTo(null); // Para que la ventana se muestre en el centro de la pantalla
        vLogin.setVisible(true); // Para que la ventana sea visible al usuario

    }

    /**
     * 
     * @return Este método nos devuelve un objeto de tipo conexión
     * Para eso recogemos los valores seleccionados en el jComboBox y se lo pasamos a un obejto de tipo Conexion, que será en que finalmente devolvamos
     * @throws SQLException 
     */
    private Conexion conectar() throws SQLException {
        
        /*
        try {
            this.conexion_bd = new Conexion("oracle", "172.17.20.39:1521", "etsi", "DDSI_057", "DDSI_057");
            //this.conexion_bd = new Conexion("mariadb", "172.18.1.241:3306", "DDSI_057", "DDSI_057", "DDSI_057");
            this.vista.mensajeConsola("Conexion realizada con exito!!!");

        } catch (SQLException ex) {
            this.vista.mensajeConsola("Error en la conexion", ex.getMessage());
        }*/
        
        String server = (String) (vLogin.jComboBoxServer.getSelectedItem());
        String ip = vLogin.jTextFieldIP.getText();
        String service_bd = vLogin.jTextFieldServicie.getText();
        String user = vLogin.jTextFieldUser.getText();
        String password = new String (vLogin.jPasswordFieldPasswordBD.getPassword());
        this.conexion_bd = new Conexion(server, ip, service_bd, user, password);
        return this.conexion_bd;
    }

    /**
     * 
     * @return Este método nos devuelve un true o false dependiendo de sia la desconexión de la base de datos e ha podido hacer correctamente o no
     */
    private boolean desconectar() {

        boolean resultado = false;

        try {
            this.conexion_bd.desconexion();
            this.vista.mensajeConsola("Desconexion de la BD efectuada con exito!");
            resultado = true;
        } catch (SQLException e) {
            this.vista.mensajeConsola("Error al desconectarse de la BD", e.getMessage());
        }
        return resultado;
    }

    /**
     * 
     * @return Este método nos devuelve  la información de la base de datos que queremos mostrar
     * @throws SQLException 
     */
    public DatabaseMetaData recuperar_metadatos() throws SQLException {

        return this.conexion_bd.informacionBD();
    }

    /**
     * Este método esta a la escucha de los posibles que se pueden en nuestra ventana
     * En este caso está a la escucha de que pulsemos el botón de salir de la ventana, el botón conectar o pulsemos en el jComboBox para selecionar un servidor u otro
     */
    private void addListeners() {
        vLogin.jButtonExit.addActionListener(this);
        vLogin.jButtonConnect.addActionListener(this);
        vLogin.jComboBoxServer.addActionListener(this);
    }

    /**
     * Este método es el encargado de llevar a cabo acciones diferentes según el evento que trate
     * En este caso trata el evento de pulsar el botón de cerrar la ventana login recogido por el addListeners(),
     *  el evento de seleccionar el jComboBox para cambiar entre servidores de base datos o el evento de conectar a la base de datos seleccionada
     * @param e El parametro e representa el evento que queremos tratar en el método
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Conectar": {
                try {
                    this.conexion_bd = conectar();
                    this.vMensaje.MensajeInformacion("Conexión Correcta!");
                    ControladorPrincipal c = new ControladorPrincipal(conexion_bd);
                    this.vLogin.dispose();
                    
                } catch (HeadlessException | SQLException ex) {
                    /*JOptionPane.showMessageDialog(vLogin, "Error en la conexión!, Revise los parámetros introducidos"
                            + " " + ex.getCause(),"Access denied!", JOptionPane.ERROR_MESSAGE);  */
                    this.vMensaje.MensajeDeError("Error en la conexion, revise los parametros introducidos" + " " + "\n" + ex.getCause());
                }
                break;
            }
            case "SalirDialogoConexion": {
                this.vLogin.dispose();
                System.exit(0);
                break;
            }
            case "comboBoxChange": {
                comboBoxChangeEvent();
                break;
            }
        }
    }

    /**
     * Este método ha sido creado para inicializar los campos de conexión a cada base de datos al iniciar la app
     */
    public void comboBoxChangeEvent() {
        String tipoBD = (String) vLogin.jComboBoxServer.getSelectedItem();
        if (tipoBD.equals("Oracle")) {
            vLogin.jTextFieldIP.setText("172.17.20.39:1521");
            vLogin.jTextFieldServicie.setText("etsi");
            vLogin.jTextFieldUser.setText("DDSI_057");
            vLogin.jPasswordFieldPasswordBD.setText("DDSI_057");
        }
        else {
            vLogin.jTextFieldIP.setText("172.18.1.241:3306");
            vLogin.jTextFieldServicie.setText("DDSI_057");
            vLogin.jTextFieldUser.setText("DDSI_057");
            vLogin.jPasswordFieldPasswordBD.setText("DDSI_057");
        }
    }
}
