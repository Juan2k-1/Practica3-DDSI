/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Modelo.Monitor;
import Modelo.MonitorDAO;
import Modelo.MonitorTablas;
import Vista.VistaActualizacionMonitor;
import Vista.VistaMensaje;
import Vista.VistaMonitor;
import Vista.VistaNuevoMonitor;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author juald
 */
public class ControladorMonitor implements ActionListener {

    private VistaMensaje vMensaje = null;
    private VistaMonitor vMonitor = null;
    private VistaNuevoMonitor vNuevoMonitor = null;
    private VistaActualizacionMonitor vActualizar = null;
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private Conexion conexion;
    private ArrayList<Monitor> ListaMonitores;
    private MonitorTablas mTablas;

    /**
     * Este metodo es el constructor de la clase Controlador principal Se
     * encarga de inicializar los atributos y de crear las vistas de los
     * mensajes relacionados con la ventana principal y la propia ventana
     * principal
     *
     * @param vMonitor
     */
    public ControladorMonitor(VistaMonitor vMonitor, Conexion conexion, MonitorTablas mTablas) {

        this.vMensaje = new VistaMensaje();
        this.vNuevoMonitor = new VistaNuevoMonitor();
        this.vActualizar = new VistaActualizacionMonitor();
        this.vMonitor = vMonitor;
        this.conexion = conexion;
        this.mTablas = mTablas;

        MonitorDAO miMonitor = new MonitorDAO(conexion);
        String codMonitor = null;
        try {
            codMonitor = miMonitor.calcularCodigoMonitor();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorSocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.vNuevoMonitor.jTextFieldCodigo.setText(codMonitor);

        addListeners();
    }

    /**
     * Este método esta a la escucha de los posibles que se pueden en nuestra
     * ventana En este caso simplemente está a la escucha de que pulsemos el
     * botón de salir de la ventana
     */
    private void addListeners() {
        vMonitor.jButtonAddMonitor.addActionListener(this);
        vMonitor.jButtonDeleteMonitor.addActionListener(this);
        vMonitor.jButtonUpdateMonitor.addActionListener(this);
        vNuevoMonitor.jButtonInsertarNuevoMonitor.addActionListener(this);
        vNuevoMonitor.jButtonCancelar.addActionListener(this);
        vActualizar.jButtonActualizar.addActionListener(this);
        vActualizar.jButtonCancelar.addActionListener(this);
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
            case "NuevoMonitor": {
                this.vNuevoMonitor.setLocationRelativeTo(null);
                this.vNuevoMonitor.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                this.vNuevoMonitor.setVisible(true);
                this.vNuevoMonitor.jTextFieldCodigo.setEditable(false); // A veces no detecta el comando y hay que hacerlo en el asistente de interfaces
                break;
            }
            case "BajaMonitor": {
                this.eliminarMonitor();
                break;
            }
            case "ActualizacionMonitor": {
                this.vActualizar.setLocationRelativeTo(null);
                this.vActualizar.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                this.vActualizar.setVisible(true);
                this.vActualizar.dispose();
                break;
            }
            case "Insertar": {
                this.insertarNuevoMonitor();
                vNuevoMonitor.dispose();
                break;
            }
            case "Cancelar": {
                vNuevoMonitor.dispose();
                break;
            }
            case "Cancelar2": {
                vActualizar.dispose();
                break;
            }
            case "Actualizar": {
                this.updateMonitor();
                vNuevoMonitor.dispose();
                break;
            }
        }
    }

    /**
     *
     * @param monitor
     */
    public void insertarNuevoMonitor() {
        try {
            MonitorDAO miMonitor = new MonitorDAO(conexion);

            String codMonitor = miMonitor.calcularCodigoMonitor();
            this.vNuevoMonitor.jTextFieldCodigo.setText(codMonitor);
            String nombre = vNuevoMonitor.jTextFieldNombre.getText();
            String dni = vNuevoMonitor.jTextFieldDNI.getText();
            String telefono = vNuevoMonitor.jTextFieldTelefono.getText();
            String correo = vNuevoMonitor.jTextFieldCorreo.getText();
            String nick = vNuevoMonitor.jTextFieldNick.getText();

            Date fechaChooser = vNuevoMonitor.jDateChooserFechaEntrada.getDate();
            String fechaEntrada = "";
            if (fechaChooser != null) {
                fechaEntrada = formatoFecha.format(fechaChooser);
            }

            Monitor monitor = new Monitor(codMonitor, nombre, dni, telefono, correo, fechaEntrada, nick);

            if (!miMonitor.listaMonitores().contains(monitor)) {
                try {
                    miMonitor.insertarMonitor(monitor);
                    mTablas.vaciarTablaMonitores();
                    mTablas.rellenarTablaMonitores(miMonitor.listaMonitores());

                } catch (SQLException ex) {
                    Logger.getLogger(ControladorMonitor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarMonitor() {
        Monitor eliminar;

        String codMonitor = vNuevoMonitor.jTextFieldCodigo.getText();
        String nombre = vNuevoMonitor.jTextFieldNombre.getText();
        String dni = vNuevoMonitor.jTextFieldDNI.getText();
        String telefono = vNuevoMonitor.jTextFieldTelefono.getText();
        String correo = vNuevoMonitor.jTextFieldCorreo.getText();

        Date fechaChooser = vNuevoMonitor.jDateChooserFechaEntrada.getDate();
        String fechaEntrada = "";
        if (fechaChooser != null) {
            fechaEntrada = formatoFecha.format(fechaChooser);
        }
        String nick = vNuevoMonitor.jTextFieldNick.getText();

        int opcion = JOptionPane.showConfirmDialog(null, "¿Quiere eliminarlo?");
        System.out.println("Opcion: " + opcion);

        if (opcion == 0) {
            try {
                eliminar = new Monitor(codMonitor, nombre, dni, telefono, correo, fechaEntrada, nick);
                MonitorDAO miMonitor = new MonitorDAO(conexion);
                miMonitor.eliminarMonitor(eliminar);

                ListaMonitores = miMonitor.listaMonitores();
                mTablas.vaciarTablaMonitores();
                mTablas.rellenarTablaMonitores(ListaMonitores);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateMonitor() {
        MonitorDAO monitorDAO = new MonitorDAO(conexion);
        try {           
            int fila = vMonitor.TablaMonitor.getSelectedRow();
            this.ListaMonitores = monitorDAO.listaMonitores();
            Monitor seleccionado = this.ListaMonitores.get(fila);

            String codMonitor = seleccionado.getCodMonitor();
            String nombre = vActualizar.jTextFieldNombre.getText();
            String dni = vActualizar.jTextFieldDNI.getText();
            String telefono = vActualizar.jTextFieldTelefono.getText();
            String correo = vActualizar.jTextFieldCorreo.getText();
            String nick = vActualizar.jTextFieldNick.getText();

            Date fechaChooser = vActualizar.jDateChooserFechaEntrada.getDate();
            String fechaEntrada = "";
            if (fechaChooser != null) {
                fechaEntrada = formatoFecha.format(fechaChooser);
            }
            seleccionado.setCodMonitor(codMonitor);
            seleccionado.setNombre(nombre);
            seleccionado.setDni(dni);
            seleccionado.setTelefono(telefono);
            seleccionado.setCorreo(correo);    
            seleccionado.setFechaEntrada(fechaEntrada);
            seleccionado.setNick(nick);
            
            monitorDAO.actualizarMonitor(seleccionado);
            this.mTablas.vaciarTablaMonitores();
            ListaMonitores = monitorDAO.listaMonitores();
            this.mTablas.rellenarTablaMonitores(ListaMonitores);

        } catch (SQLException ex) {
            Logger.getLogger(ControladorMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
