package com.primerp.integradora.Cosas.Responst;

public class RegisterRequest {
    private String usuario_nom;
    private String email;
    private String password;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;

    public RegisterRequest(String usuario_nom, String email, String password, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono) {
        this.usuario_nom = usuario_nom;
        this.email = email;
        this.password = password;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
    }


    public RegisterRequest(String user, String name, String email, String phone) {
        this.usuario_nom = user;
        this.nombres = name;
        this.email = email;
        this.telefono = phone;
    }

    public RegisterRequest(String email) {
        this.email = email;
    }
}
