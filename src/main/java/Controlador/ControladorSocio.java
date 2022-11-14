/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Conexion;
import Modelo.Socio;
import Modelo.SocioDAO;
import Modelo.SocioTablas;
import Vista.VistaActualizacionSocio;
import Vista.VistaMensaje;
import Vista.VistaNuevoSocio;
import Vista.VistaSocio;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;

/**
 *
 * @author juald
 */
public class ControladorSocio implements ActionListener {

    private VistaMensaje vMensaje = null;
    private VistaSocio vSocio = null;
    private VistaNuevoSocio vNuevoSocio = null;
    private VistaActualizacionSocio vActualizar = null;
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
    private Conexion conexion;
    private ArrayList<Socio> ListaSocios;
    private SocioTablas sTablas;

    /**
     * Este metodo es el constructor de la clase Controlador principal Se
     * encarga de inicializar los atributos y de crear las vistas de los
     * mensajes relacionados con la ventana principal y la propia ventana
     * principal
     *
     * @param vSocio
     * @param conexion
     * @param sTablas
     */
    public ControladorSocio(VistaSocio vSocio, Conexion conexion, SocioTablas sTablas) {

        this.vMensaje = new VistaMensaje();
        this.vNuevoSocio = new VistaNuevoSocio();
        this.vActualizar = new VistaActualizacionSocio();
        this.vSocio = vSocio;
        this.conexion = conexion;
        this.sTablas = sTablas;

        SocioDAO miSocio = new SocioDAO(conexion);

        String NumeroSocio = null;
        try {
            NumeroSocio = miSocio.calcularCodigoSocio();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorSocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.vNuevoSocio.jTextFieldCodigo.setText(NumeroSocio);
        addListeners();
    }

    /**
     * Este método esta a la escucha de los posibles que se pueden en nuestra
     * ventana En este caso simplemente está a la escucha de que pulsemos el
     * botón de salir de la ventana
     */
    private void addListeners() {
        vSocio.jButtonAddSocio.addActionListener(this);
        vSocio.jButtonDeleteSocio.addActionListener(this);
        vSocio.jButtonUpdateSocio.addActionListener(this);
        vNuevoSocio.jButtonInsertarNuevoSocio.addActionListener(this);
        vNuevoSocio.jButtonCancelar.addActionListener(this);
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
            case "NuevoSocio": {
                this.vNuevoSocio.setLocationRelativeTo(null);
                this.vNuevoSocio.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                this.vNuevoSocio.setVisible(true);
                //this.vNuevoSocio.jTextFieldCodigo.setEnabled(false);
                break;
            }

            case "BajaSocio": {
                this.eliminarSocio();
                break;
            }
            case "ActualizacionSocio": {
                this.vActualizar.setLocationRelativeTo(null);
                this.vActualizar.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
                this.vActualizar.setVisible(true);
                //this.vActualizar.dispose();
                break;
            }
            case "Insertar": {
                this.insertarNuevoSocio();
                vNuevoSocio.dispose();
                break;
            }
            case "Cancelar": {
                vNuevoSocio.dispose();
                break;
            }
            case "Cancelar2": {
                vActualizar.dispose();
                break;
            }
            case "Actualizar": {
                this.updateSocio();
                vNuevoSocio.dispose();
                break;
            }
        }
    }

    /**
     *
     * @param monitor
     */
    public void insertarNuevoSocio() {
        try {
            SocioDAO miSocio = new SocioDAO(conexion);

            String NumeroSocio = miSocio.calcularCodigoSocio();
            this.vNuevoSocio.jTextFieldCodigo.setText(NumeroSocio);
            String nombre = vNuevoSocio.jTextFieldNombre.getText();
            String dni = vNuevoSocio.jTextFieldDNI.getText();

            Date fechaChooser = vNuevoSocio.jDateChooserFechaNacimiento.getDate();
            String fechaNacimiento = "";
            if (fechaChooser != null) {
                fechaNacimiento = formatoFecha.format(fechaChooser);
            }

            String telefono = vNuevoSocio.jTextFieldTelefono.getText();
            String correo = vNuevoSocio.jTextFieldCorreo.getText();
            String categoria = vNuevoSocio.jTextFieldCategoria.getText();

            Date fechaChooser2 = vNuevoSocio.jDateChooserFechaEntrada.getDate();
            String fechaEntrada = "";
            if (fechaChooser2 != null) {
                fechaEntrada = formatoFecha.format(fechaChooser2);
            }

            Socio socio = new Socio(NumeroSocio, nombre, dni, fechaNacimiento, telefono, correo, fechaEntrada, categoria);

            if (!miSocio.listarSocios().contains(socio)) {
                try {
                    miSocio.insertarSocio(socio);
                    this.sTablas.vaciarTablaSocios();
                    this.sTablas.rellenarTablaSocios(miSocio.listarSocios());

                } catch (SQLException ex) {
                    Logger.getLogger(ControladorMonitor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarSocio() {
        Socio eliminar;

        String numeroSocio = vNuevoSocio.jTextFieldCodigo.getText();
        String nombre = vNuevoSocio.jTextFieldNombre.getText();
        String dni = vNuevoSocio.jTextFieldDNI.getText();

        Date fechaChooser = vNuevoSocio.jDateChooserFechaNacimiento.getDate();
        String fechaNacimiento = "";
        if (fechaChooser != null) {
            fechaNacimiento = formatoFecha.format(fechaChooser);
        }

        String telefono = vNuevoSocio.jTextFieldTelefono.getText();
        String correo = vNuevoSocio.jTextFieldCorreo.getText();

        Date fechaChooser2 = vNuevoSocio.jDateChooserFechaEntrada.getDate();
        String fechaEntrada = "";
        if (fechaChooser2 != null) {
            fechaEntrada = formatoFecha.format(fechaChooser2);
        }
        String categoria = vNuevoSocio.jTextFieldCategoria.getText();

        int opcion = JOptionPane.showConfirmDialog(null, "¿Quiere eliminarlo?");
        System.out.println("Opcion: " + opcion);

        if (opcion == 0) {
            try {
                eliminar = new Socio(numeroSocio, nombre, dni, fechaNacimiento, telefono, correo, fechaEntrada, categoria);
                SocioDAO miSocio = new SocioDAO(conexion);
                miSocio.eliminarSocio(eliminar);

                ListaSocios = miSocio.listarSocios();
                sTablas.vaciarTablaSocios();
                sTablas.rellenarTablaSocios(ListaSocios);
            } catch (SQLException ex) {
                Logger.getLogger(ControladorMonitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateSocio() {
        SocioDAO miSocio = new SocioDAO(conexion);
        try {
            int fila = vSocio.TablaSocio.getSelectedRow();
            this.ListaSocios = miSocio.listarSocios();
            Socio seleccionado = this.ListaSocios.get(fila);

            String numeroSocio = seleccionado.getnumeroSocio();
            String nombre = vActualizar.jTextFieldNombre.getText();
            String dni = vActualizar.jTextFieldDNI.getText();

            Date fechaChooser = vNuevoSocio.jDateChooserFechaNacimiento.getDate();
            String fechaNacimiento = "";
            if (fechaChooser != null) {
                fechaNacimiento = formatoFecha.format(fechaChooser);
            }

            String telefono = vActualizar.jTextFieldTelefono.getText();
            String correo = vActualizar.jTextFieldCorreo.getText();
            String categoria = vActualizar.jTextFieldCategoria.getText();

            Date fechaChooser2 = vActualizar.jDateChooserFechaEntrada.getDate();
            String fechaEntrada = "";
            if (fechaChooser2 != null) {
                fechaEntrada = formatoFecha.format(fechaChooser2);
            }

            seleccionado.setnumeroSocio(numeroSocio);
            seleccionado.setNombre(nombre);
            seleccionado.setDni(dni);
            seleccionado.setFechaNacimiento(fechaNacimiento);
            seleccionado.setTelefono(telefono);
            seleccionado.setCorreo(correo);
            seleccionado.setFechaEntrada(fechaEntrada);       
            seleccionado.setCategoria(categoria);
            
            miSocio.actualizarSocio(seleccionado);
            this.sTablas.vaciarTablaSocios();
            ListaSocios = miSocio.listarSocios();
            this.sTablas.rellenarTablaSocios(ListaSocios);
        } catch (SQLException ex) {
            Logger.getLogger(ControladorSocio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
