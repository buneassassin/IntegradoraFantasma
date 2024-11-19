package com.primerp.integradora.Cosas.Responst;

import com.primerp.integradora.Cosas.Modelos.Tinacos;

import java.util.List;

public class TinacoResponse {
    private List<Tinacos> tinacosList;
    public List<Tinacos> getTinacosList() {
        return tinacosList;
    }

    public void setTinacosList(List<Tinacos> tinacosList) {
        this.tinacosList = tinacosList;
    }
}
