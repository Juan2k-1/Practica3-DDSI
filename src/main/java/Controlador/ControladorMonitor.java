/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.VistaMensaje;
import Vista.VistaMonitor;
import Vista.VistaNuevoMonitor;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author juald
 */
public class ControladorMonitor implements ActionListener {

    private VistaMensaje vMensaje = null;
    private VistaMonitor vMonitor = null;
    private VistaNuevoMonitor vNuevoMonitor = null;

    /**
     * Este metodo es el constructor de la clase Controlador principal Se
     * encarga de inicializar los atributos y de crear las vistas de los
     * mensajes relacionados con la ventana principal y la propia ventana
     * principal
     *
     * @param vMonitor
     */
    public ControladorMonitor(VistaMonitor vMonitor) {

        this.vMensaje = new VistaMensaje();
        this.vNuevoMonitor = new VistaNuevoMonitor();
        this.vMonitor = vMonitor;

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
                break;
            }
            case "BajaMonitor": {
                break;
            }
            case "ActualizacionMonitor": {
                break;
            }
        }
    }
}
