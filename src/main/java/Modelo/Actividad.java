/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author juald
 */
public class Actividad {
    private String idActividad;
    private String nombre;
    private String descripcion;
    private int precioBaseMes;
    
    public Actividad() {
        this.idActividad = null;
        this.nombre = null;
        this.descripcion = null;
        this.precioBaseMes = 0;
    }
    
    public Actividad(String idActividad, String nombre, String descripcion, int precioBaseMes) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioBaseMes = precioBaseMes;
    }

    //Getters
    public String getidActividad() {
        return this.idActividad;
    }
    
    public String getNombre() {
        return this.nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public int getPrecioBaseMes() {
        return this.precioBaseMes;
    }
    
    //Setters
    public void setidActividad(String idActividad) {
        this.idActividad = idActividad;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecioBaseMes(int precioBaseMes) {
        this.precioBaseMes = precioBaseMes;
    } 
}
