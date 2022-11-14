/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Modelo.Monitor;
import Modelo.MonitorDAO;
import Modelo.MonitorTablas;
import Modelo.Socio;
import Modelo.SocioDAO;
import Modelo.SocioTablas;
import Vista.VistaMensaje;
import Vista.VistaMonitor;
import Vista.VistaPorDefecto;
import Vista.VistaPrincipal;
import Vista.VistaSocio;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JTable;

/**
 *
 * @author juald
 */
public class ControladorPrincipal implements ActionListener {

    private Conexion conexion_bd = null;
    private VistaMensaje vMensaje = null;
    private VistaPrincipal vPrincipal = null;
    private VistaMonitor vMonitor = null;
    private VistaSocio vSocio;
    private VistaPorDefecto vPorDefecto;
    private ControladorMonitor controladorMonitor;
    private ControladorSocio controladorSocio;
    private MonitorTablas mTablas;
    private SocioTablas socioTablas;
    private MonitorDAO mDAO;
    private SocioDAO sDAO;

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
        this.vMensaje = new VistaMensaje();
        this.vPrincipal = new VistaPrincipal();
        this.vMonitor = new VistaMonitor();
        this.vSocio = new VistaSocio();
        this.vPorDefecto = new VistaPorDefecto();
        this.mDAO = new MonitorDAO(conexion);
        this.sDAO = new SocioDAO(conexion);
        this.mTablas = new MonitorTablas(vMonitor);
        this.socioTablas = new SocioTablas(vSocio);

        addListeners();

        vPrincipal.setLayout(new CardLayout());

        vPrincipal.add(vPorDefecto);
        vPrincipal.add(vMonitor);
        vPrincipal.add(vSocio);

        vPorDefecto.setVisible(true);
        vMonitor.setVisible(false);
        vSocio.setVisible(false);

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
                controladorMonitor = new ControladorMonitor(vMonitor, this.conexion_bd, this.mTablas);
                this.vPorDefecto.setVisible(false);
                this.vSocio.setVisible(false);
                this.vMonitor.setVisible(true);
                //this.mTablas.dibujarTablaMonitores(vMonitor);
                this.vMonitor.TablaMonitor.setModel(mTablas);
                DiseñoTablaMonitor();

                try {
                    pideMonitores();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;
            }
            case "GestionDeSocios": {
                controladorSocio = new ControladorSocio(vSocio, this.conexion_bd, this.socioTablas);
                this.vPorDefecto.setVisible(false);
                this.vMonitor.setVisible(false);
                this.vSocio.setVisible(true);
                this.vSocio.TablaSocio.setModel(socioTablas);
                DiseñoTablaSocio();

                try {
                    pideSocios();
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }

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
        vPrincipal.jMenuItemSocios.addActionListener(this);
        vPrincipal.jMenuItemSalir.addActionListener(this);
    }

    private void pideMonitores() throws SQLException {
        ArrayList<Monitor> monitores = this.mDAO.listaMonitores();
        this.mTablas.vaciarTablaMonitores();
        this.mTablas.rellenarTablaMonitores(monitores);
    }

    private void DiseñoTablaMonitor() {
        //Para no permitir el redimensionamiento de las columnas con el ratón
        vMonitor.TablaMonitor.getTableHeader().setResizingAllowed(false);
        vMonitor.TablaMonitor.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        vMonitor.TablaMonitor.getColumnModel().getColumn(0).setPreferredWidth(60);
        vMonitor.TablaMonitor.getColumnModel().getColumn(1).setPreferredWidth(240);
        vMonitor.TablaMonitor.getColumnModel().getColumn(2).setPreferredWidth(70);
        vMonitor.TablaMonitor.getColumnModel().getColumn(3).setPreferredWidth(70);
        vMonitor.TablaMonitor.getColumnModel().getColumn(4).setPreferredWidth(200);
        vMonitor.TablaMonitor.getColumnModel().getColumn(5).setPreferredWidth(150);
        vMonitor.TablaMonitor.getColumnModel().getColumn(6).setPreferredWidth(60);
    }

    private void pideSocios() throws SQLException {
        ArrayList<Socio> socios = this.sDAO.listarSocios();
        this.socioTablas.vaciarTablaSocios();
        this.socioTablas.rellenarTablaSocios(socios);
    }

    private void DiseñoTablaSocio() {
        //Para no permitir el redimensionamiento de las columnas con el ratón
        vSocio.TablaSocio.getTableHeader().setResizingAllowed(false);
        vSocio.TablaSocio.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        vSocio.TablaSocio.getColumnModel().getColumn(0).setPreferredWidth(60);
        vSocio.TablaSocio.getColumnModel().getColumn(1).setPreferredWidth(240);
        vSocio.TablaSocio.getColumnModel().getColumn(2).setPreferredWidth(70);
        vSocio.TablaSocio.getColumnModel().getColumn(3).setPreferredWidth(70);
        vSocio.TablaSocio.getColumnModel().getColumn(4).setPreferredWidth(200);
        vSocio.TablaSocio.getColumnModel().getColumn(5).setPreferredWidth(150);
        vSocio.TablaSocio.getColumnModel().getColumn(6).setPreferredWidth(60);
        vSocio.TablaSocio.getColumnModel().getColumn(7).setPreferredWidth(60);
    }
}
