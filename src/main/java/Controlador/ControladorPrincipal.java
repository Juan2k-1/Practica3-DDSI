/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Vista.VistaMensaje;
import Vista.VistaMonitor;
import Vista.VistaPorDefecto;
import Vista.VistaPrincipal;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author juald
 */
public class ControladorPrincipal implements ActionListener {

    private Conexion conexion_bd = null;
    private VistaMensaje vMensaje = null;
    private VistaPrincipal vPrincipal = null;
    private VistaMonitor vMonitor = null;
    private VistaPorDefecto vPorDefecto;
    private ControladorMonitor controladorMonitor;

    /**
     * Este metodo es el constructor de la clase Controlador principal Se
     * encarga de inicializar los atributos y de crear las vistas de los
     * mensajes relacionados con la ventana principal y la propia ventana
     * principal
     *
     * @param conexion
     */
    public ControladorPrincipal(Conexion conexion) {
        this.conexion_bd = conexion;
        vMensaje = new VistaMensaje();
        vPrincipal = new VistaPrincipal();
        vMonitor = new VistaMonitor();
        vPorDefecto = new VistaPorDefecto();

        addListeners();

        vPrincipal.setLayout(new CardLayout());

        vPrincipal.add(vPorDefecto);
        vPrincipal.add(vMonitor);

        vPorDefecto.setVisible(true);
        vMonitor.setVisible(false);

        vPrincipal.setLocationRelativeTo(null); //Para que la ventana se muestre en el centro de la pantalla
        vPrincipal.setVisible(true); // Para hacer la ventana visible
        vPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Para que la ventana se cierra cuando le doy a cerrar

    }

    /**
     * Este método es el encargado de llevar a cabo acciones diferentes según el
     * evento que trate En este caso solo trata el evento de pulsar el botón de
     * cerrar la ventana principal recogido por el addListeners()
     *
     * @param e El parametro e representa el evento que queremos tratar en el
     * método
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "CerrarVentanaPrincipal": {
                try {
                    conexion_bd.desconexion();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
                vMensaje.MensajeInformacion("Cerrando la aplicación");
                vPrincipal.dispose();
                System.exit(0);
                break;
            }
            case "GestionDeMonitores": {
                controladorMonitor = new ControladorMonitor(vMonitor);
                vMonitor.setVisible(true);
                vPorDefecto.setVisible(false);
                break;
            }
            case "GestionDeSocios": {
                break;
            }
            case "GestionDeActividades": {
                break;
            }
        }
    }

    /**
     * Este método esta a la escucha de los posibles que se pueden en nuestra
     * ventana En este caso simplemente está a la escucha de que pulsemos el
     * botón de salir de la ventana
     */
    private void addListeners() {
        vPrincipal.jMenuItemMonitores.addActionListener(this);
        vPrincipal.jMenuItemSalir.addActionListener(this);
    }
}
