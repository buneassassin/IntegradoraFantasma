package com.primerp.integradora.Cosas.Responst;

public class RegisterRequest {
    private String usuario_nom;
    private String email;
    private String password;

    public RegisterRequest(String usuario_nom, String email, String password) {
        this.usuario_nom = usuario_nom;
        this.email = email;
        this.password = password;
    }
}
