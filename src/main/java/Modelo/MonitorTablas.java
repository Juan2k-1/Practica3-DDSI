/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VistaMonitor;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juald
 */
public class MonitorTablas extends DefaultTableModel {

    private VistaMonitor vMonitor;

    public MonitorTablas(VistaMonitor vMonitor) {
        this.vMonitor = vMonitor;
        dibujarTablaMonitores(vMonitor);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void dibujarTablaMonitores(VistaMonitor vMonitor) {
        String[] columnasTabla = {"Código", "Nombre", "DNI", "Teléfono", "Correo", "Fecha Incorporación", "Nick"};
        setColumnIdentifiers(columnasTabla);
    }

    public void rellenarTablaMonitores(ArrayList<Monitor> monitores) {
        Object[] fila = new Object[7];
        int numRegistros = monitores.size();
        for (int i = 0; i < numRegistros; i++) {
            fila[0] = monitores.get(i).getCodMonitor();
            fila[1] = monitores.get(i).getNombre();
            fila[2] = monitores.get(i).getDni();
            fila[3] = monitores.get(i).getTelefono();
            fila[4] = monitores.get(i).getCorreo();
            fila[5] = monitores.get(i).getFechaEntrada();
            fila[6] = monitores.get(i).getNick();
            this.addRow(fila);
        }
    }

    public void vaciarTablaMonitores() {
        while (this.getRowCount() > 0) {
            this.removeRow(0);
        }
    }
}
