package com.primerp.integradora.Cosas.Responst;

import com.google.gson.annotations.SerializedName;
import com.primerp.integradora.Cosas.Modelos.Tinacos;

import java.util.List;

public class TinacoResponse {
    private List<Tinacos> tinacosList;
    private boolean success;
    private String message;

    @SerializedName("mensaje")
    private String mensaje;

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setMessage(String loginSuccessful){
        this.message = message;
    };
    public String getMessage() {
        return message;
    }


    public TinacoResponse(String s, int i, String tinacoName) {

    }

    public List<Tinacos> getTinacosList() {
        return tinacosList;
    }
    private Tinacos tinaco;

    public void setTinacosList(List<Tinacos> tinacosList) {
        this.tinacosList = tinacosList;
    }


    public Tinacos getTinaco() {
        return tinaco;
    }

    public void setTinaco(Tinacos tinaco) {
        this.tinaco = tinaco;
    }
}
