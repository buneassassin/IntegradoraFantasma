package com.primerp.integradora.Cosas.Responst;

public class PassaworRequest {
    private String password;
    private String password_confirmation;
    public PassaworRequest(String password, String password_confirmation) {
        this.password = password;
        this.password_confirmation = password_confirmation;
    }

}
