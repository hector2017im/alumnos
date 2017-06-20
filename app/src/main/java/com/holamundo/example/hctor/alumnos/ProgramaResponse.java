package com.holamundo.example.hctor.alumnos;

/**
 * Created by Hector on 18/06/2017.
 */

public class ProgramaResponse {
    private int id;
    private String nombre;
    private String apellidoP;
    private String getApellidoM;
    private String curso;

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getGetApellidoM() {
        return getApellidoM;
    }

    public void setGetApellidoM(String getApellidoM) {
        this.getApellidoM = getApellidoM;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


