/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vista.VistaSocio;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author juald
 */
public class SocioTablas extends DefaultTableModel {
    private VistaSocio vSocio;

    public SocioTablas(VistaSocio vSocior) {
        this.vSocio = vSocio;
        dibujarTablaSocios(vSocio);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void dibujarTablaSocios(VistaSocio vSocio) {
        String[] columnasTabla = {"NumeroSocio", "Nombre", "DNI", "Fecha Nacimiento", "Teléfono", "Correo", "Fecha Entrada", "Categoría"};
        setColumnIdentifiers(columnasTabla);
    }

    public void rellenarTablaSocios(ArrayList<Socio> socios) {
        Object[] fila = new Object[8];
        int numRegistros = socios.size();
        for (int i = 0; i < numRegistros; i++) {
            fila[0] = socios.get(i).getnumeroSocio();
            fila[1] = socios.get(i).getNombre();
            fila[2] = socios.get(i).getDni();
            fila[3] = socios.get(i).getFechaNacimiento();
            fila[4] = socios.get(i).getTelefono();
            fila[5] = socios.get(i).getCorreo();
            fila[6] = socios.get(i).getFechaEntrada();
            fila[7] = socios.get(i).getCategoria();
            this.addRow(fila);
        }
    }

    public void vaciarTablaSocios() {
        while (this.getRowCount() > 0) {
            this.removeRow(0);
        }
    }
}
