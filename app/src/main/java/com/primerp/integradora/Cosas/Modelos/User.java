package com.primerp.integradora.Cosas.Modelos;

public class User {
    private int id;
    private String email;
    private String usuario_nom;
    private String foto_perfil;
    private Persona persona;
    private int numero_tinacos;
    private String tiempo_registrado;
    private int is_Inactive;
    private String rol;

    // Getters y Setters
    public int getId() { return id; }
    public String getEmail() { return email; }
    public String getUsuarioNom() { return usuario_nom; }
    public String getFotoPerfil() { return foto_perfil; }
    public Persona getPersona() { return persona; }
    public int getNumeroTinacos() { return numero_tinacos; }
    public String getTiempoRegistrado() { return tiempo_registrado; }
    public int getIsInactive() { return is_Inactive; }
    public String getRol() {
        return rol;
    }
}
