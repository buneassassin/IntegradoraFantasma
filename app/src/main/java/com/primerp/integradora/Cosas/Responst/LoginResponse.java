package com.primerp.integradora.Cosas.Responst;

public class LoginResponse {
    private boolean success;
    private String message;
    private String token;

    public boolean isSuccess() {
        return success;
    }

    public void setMessage(String loginSuccessful){
        this.message = message;
    };
    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
