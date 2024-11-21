package com.primerp.integradora.Cosas.Modelos;

import com.google.gson.annotations.SerializedName;

public class Tinacos {
    private int id;

    @SerializedName("name") // Mapea "name" del JSON al atributo "nombre"
    private String nombre;

    @SerializedName("nivel_del_agua") // Mapea "nivel_del_agua" del JSON al atributo correspondiente
    private int nivelDelAgua;

    @SerializedName("id_usuario") // Mapea "id_usuario" del JSON al atributo idUsuario (opcional)
    private int idUsuario;


    public Tinacos(String nombre, int nivelDelAgua) {
        this.nombre = nombre;
        this.nivelDelAgua = nivelDelAgua;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivelDelAgua() {
        return nivelDelAgua;
    }

    public void setNivelDelAgua(int nivelDelAgua) {
        this.nivelDelAgua = nivelDelAgua;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }


}