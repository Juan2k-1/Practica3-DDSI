/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author juald
 */
public class Monitor {
    private String codMonitor;
    private String nombre;
    private String dni;
    private String telefono;
    private String correo;
    private String fechaEntrada;
    private String nick;
    
    public Monitor() {
        this.codMonitor = null;
        this.nombre = null;
        this.dni = null;
        this.telefono = null;
        this.correo = null;
        this.fechaEntrada = null;
        this.nick = null;
    }
    
    public Monitor(String codMonitor, String nombre, String dni, String telefono, String correo, String fechaEntrada, String nick) {
        this.codMonitor = codMonitor;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
        this.fechaEntrada = fechaEntrada;
        this.nick = nick;
    }

    //Getters
    public String getCodMonitor() {
        return this.codMonitor;
    }

    public String getNombre() {
        return this.nombre;
    }

    public String getDni() {
        return this.dni;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public String getCorreo() {
        return this.correo;
    }

    public String getFechaEntrada() {
        return this.fechaEntrada;
    }

    public String getNick() {
        return this.nick;
    }

    //Setters
    public void setCodMonitor(String codMonitor) {
        this.codMonitor = codMonitor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
