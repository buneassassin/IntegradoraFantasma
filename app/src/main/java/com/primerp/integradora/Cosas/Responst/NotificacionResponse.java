package com.primerp.integradora.Cosas.Responst;

import com.primerp.integradora.Cosas.Modelos.Notificaciones;
import com.primerp.integradora.Cosas.Modelos.Tinacos;

import java.util.List;

public class NotificacionResponse {
    private String status;
    private List<Notificaciones> data;

    // Getters y Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Notificaciones> getData() {
        return data;
    }

    public void setData(List<Notificaciones> data) {
        this.data = data;
    }
}
