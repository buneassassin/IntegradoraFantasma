package com.primerp.integradora.Cosas.Responst;

public class NotificacionRequest {
    private int id;
    private String message;

    public NotificacionRequest(int id) {
        this.id = id;
    }

    public NotificacionRequest(int id, String message) {
        this.id = id;
        this.message = message;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
